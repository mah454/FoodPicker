/*
 * Copyright (c) 2020.  FanapSoft Software Inc
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ir.moke.foodpicker.api;

import ir.moke.foodpicker.auth.JWTCredential;
import ir.moke.foodpicker.auth.TokenProvider;
import ir.moke.foodpicker.http.FanapResourceProvider;
import ir.moke.foodpicker.repository.JWTCredentialRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class AuthenticationResources {
    private String state;

    @Inject
    private Logger logger;

    @EJB
    private FanapResourceProvider fanapResourceProvider;

    @EJB
    private JWTCredentialRepository jwtCredentialRepository;

    @Inject
    private SecurityContext securityContext;

    @Inject
    @ConfigProperty(name = "foodpicker.frontEnd.baseUrl")
    private String frontEndTarget;

    @Inject
    @ConfigProperty(name = "foodpicker.frontEnd.loginPath")
    private String frontEndLoginPath;

    @EJB
    private TokenProvider tokenProvider;

    @Context
    private HttpServletResponse response;

    @PostConstruct
    public void init() {
        this.state = RandomStringUtils.random(14, true, true);
    }

    @GET
    @Path("login")
    public Response login() {
        String loginUri = fanapResourceProvider.getLoginUri(state);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("url", loginUri);
        return Response.ok(responseData).build();
    }

    @GET
    @Path("callback")
    public Response ssoCallBack(@QueryParam("code") String authorizeCode,
                                @QueryParam("state") String state) {
        if (csrfProtected(state))
            return Response.seeOther(URI.create(frontEndTarget)).build();

        Principal callerPrincipal = securityContext.getCallerPrincipal();
        if (callerPrincipal != null) {
            String token = response.getHeader("token").split(" ")[1].trim();
            return Response.temporaryRedirect(URI.create(frontEndTarget + frontEndLoginPath + "?token=" + token)).build();
        } else {
            return Response.temporaryRedirect(URI.create(frontEndTarget)).build();
        }
    }

    @GET
    @Path("verify")
    public Response verifyToken() {
        if (securityContext.getCallerPrincipal() != null) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Path("logout")
    public Response logout(@HeaderParam("token") String token) {
        try {
            String username = securityContext.getCallerPrincipal().getName();
            logger.info("Logout user " + username);
            JWTCredential jwtCredential = jwtCredentialRepository.findByToken(token.substring("bearer ".length()).trim());
            fanapResourceProvider.logout(jwtCredential.getAccessToken());
            jwtCredentialRepository.remove(username);
        } catch (Exception ignore) {
        }
        return Response.ok().build();
    }

    private boolean csrfProtected(String state) {
        return !state.equals(this.state);
    }
}
