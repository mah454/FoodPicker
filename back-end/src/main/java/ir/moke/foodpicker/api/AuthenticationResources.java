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
import ir.moke.foodpicker.http.FanapResourceProvider;
import ir.moke.foodpicker.repository.JWTCredentialRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;

@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class AuthenticationResources {

    private String state;

    @EJB
    private FanapResourceProvider fanapResourceProvider;

    @Inject
    private SecurityContext securityContext;

    @EJB
    private JWTCredentialRepository jwtCredentialRepository;

    @Inject
    @ConfigProperty(name = "foodpicker.frontEnd.baseUrl")
    private String frontEndUrl;

    @PostConstruct
    public void init() {
        this.state = RandomStringUtils.random(14, true, true);
    }

    @GET
    @Path("login")
    public Response login() throws IOException {
        URI loginUri = fanapResourceProvider.getLoginUri(state);
        return Response.temporaryRedirect(loginUri).build();
    }

    @GET
    @Path("callback")
    public Response ssoCallBack(@QueryParam("code") String authorizeCode, @QueryParam("state") String state) throws Exception {
        if (csrfProtected(state)) return Response.status(Response.Status.UNAUTHORIZED).build();

        Principal callerPrincipal = securityContext.getCallerPrincipal();
        if (callerPrincipal != null) {
            return Response.temporaryRedirect(URI.create(frontEndUrl)).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Path("roles")
    public Response getRoles() {
        JWTCredential credential = jwtCredentialRepository.find(securityContext.getCallerPrincipal().getName());
        return Response.ok(credential).build();
    }

    private boolean csrfProtected(String state) {
        return !state.equals(this.state);
    }
}
