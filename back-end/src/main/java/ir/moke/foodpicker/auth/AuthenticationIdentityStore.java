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
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class AuthenticationIdentityStore implements IdentityStore {

    private Set<JWTCredential> JWT_CREDENTIALS;

    @EJB
    private JWTCredentialRepository jwtCredentialRepository;

    @EJB
    private TokenProvider tokenProvider;

    @PostConstruct
    public void init() {
        JWT_CREDENTIALS = jwtCredentialRepository.findAll();
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult result;

        if (credential instanceof JWTCredential) {
            JWTCredential jwtCredential = (JWTCredential) credential;
            Optional<JWTCredential> optionalJWTCredential = JWT_CREDENTIALS.stream().filter(jwt -> jwt.getToken().equals(jwtCredential.getToken())).findFirst();
            if (optionalJWTCredential.isPresent()) {
                boolean verify = tokenProvider.verify(((JWTCredential) credential).getToken());
                if (verify) {
                    result = new CredentialValidationResult(optionalJWTCredential.get().getUsername());
                } else {
                    return CredentialValidationResult.INVALID_RESULT;
                }
            } else {
                return CredentialValidationResult.INVALID_RESULT;
            }
        } else {
            return CredentialValidationResult.NOT_VALIDATED_RESULT;
        }
        return result;
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return Collections.singleton(ValidationType.VALIDATE);
    }
}
