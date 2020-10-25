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

import ir.moke.foodpicker.repository.JWTCredentialRepository;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class AuthorizationIdentityStore implements IdentityStore {

    private Set<JWTCredential> JWT_CREDENTIALS;

    @EJB
    private JWTCredentialRepository jwtCredentialRepository;

    @PostConstruct
    public void init() {
        this.JWT_CREDENTIALS = jwtCredentialRepository.findAll();
    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        String username = validationResult.getCallerPrincipal().getName();
        Optional<JWTCredential> jwtCredential = JWT_CREDENTIALS.stream().filter(jwt -> jwt.getUsername().equals(username)).findFirst();
        if (jwtCredential.isPresent()) {
            return jwtCredential.get().getRoles();
        } else {
            return Collections.emptySet();
        }
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return Collections.singleton(ValidationType.PROVIDE_GROUPS);
    }
}
