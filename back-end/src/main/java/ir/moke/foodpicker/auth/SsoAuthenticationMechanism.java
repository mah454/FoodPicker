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


package ir.moke.foodpicker.auth;

import ir.moke.foodpicker.entity.Profile;
import ir.moke.foodpicker.entity.Role;
import ir.moke.foodpicker.entity.RoleType;
import ir.moke.foodpicker.http.FanapResourceProvider;
import ir.moke.foodpicker.repository.RoleRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@ApplicationScoped
public class SsoAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    @ConfigProperty(name = "foodpicker.api.baseUrl")
    private String foodpickerBaseUrl;

    @Inject
    @ConfigProperty(name = "foodpicker.superuser.username")
    private String superUserName;

    @Inject
    @ConfigProperty(name = "foodpicker.api.callBack")
    private String callBackPath;

    @Inject
    @ConfigProperty(name = "foodpicker.api.login")
    private String loginPath;

    @EJB
    private FanapResourceProvider fanapResourceProvider;

    @EJB
    private TokenProvider tokenProvider;

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @EJB
    private RoleRepository roleRepository;

    @EJB
    private ir.moke.foodpicker.repository.JWTCredentialRepository JWTCredentialRepository;

    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) throws AuthenticationException {
        String reqUri = request.getRequestURI();
        String token = extractTokenFromHeader(context);
        if (reqUri.equals(callBackPath)) {
            String authorizeCode = request.getParameter("code");
            String state = request.getParameter("state");
            if (authorizeCode != null && !authorizeCode.isEmpty() && state != null && !state.isEmpty()) {
                String accessToken = fanapResourceProvider.getAccessToken(authorizeCode);
                if (accessToken != null) {
                    Set<String> roles;
                    Profile userProfile = fanapResourceProvider.getUserProfile(accessToken);
                    if (userProfile == null) {
                        return context.responseUnauthorized();
                    }
                    if (userProfile.getUsername().equals(superUserName)) {
                        roles = roleRepository.find()
                                .stream()
                                .map(Role::getRoleType)
                                .map(RoleType::name)
                                .collect(toSet());
                        JWTCredential credential = new JWTCredential(userProfile.getUsername(), roles);
                        JWTCredentialRepository.save(credential);
                    } else {
                        Role statisticRole = roleRepository.find(RoleType.STATISTIC);
                        Role foodPickerRole = roleRepository.find(RoleType.FOOD_PICKER);
                        roles = new HashSet<>();
                        roles.add(statisticRole.getRoleType().name());
                        roles.add(foodPickerRole.getRoleType().name());
                        JWTCredential credential = new JWTCredential(userProfile.getUsername(), roles);
                        JWTCredentialRepository.save(credential);
                    }
                    token = tokenProvider.createToken(userProfile.getUsername(), roles);
                    context.getResponse().setHeader("token", "Bearer " + token);
                    return context.notifyContainerAboutLogin(userProfile.getUsername(), roles);
                } else {
                    return context.responseUnauthorized();
                }
            } else {
                return context.responseUnauthorized();
            }
        } else if (reqUri.equals(loginPath)) {
            return context.doNothing();
        } else if (token != null) {
            boolean isValid = tokenProvider.verify(token);
            if (isValid) {
                JWTCredential credential = tokenProvider.getCredential(token);
                if (credential == null) return context.responseUnauthorized();
                return context.notifyContainerAboutLogin(credential.getUsername(), credential.getRoles());
            }
            return context.responseUnauthorized();
        } else if (context.isProtected()) {
            return context.responseUnauthorized();
        } else {
            return context.doNothing();
        }
    }

    private String extractTokenFromHeader(HttpMessageContext context) {
        String jwt = context.getRequest().getHeader("token");
        if (jwt != null && jwt.startsWith("Bearer")) {
            return jwt.substring("Bearer".length()).trim();
        }
        return null;
    }
}
