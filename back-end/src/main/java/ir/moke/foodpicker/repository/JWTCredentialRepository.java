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

package ir.moke.foodpicker.repository;

import ir.moke.foodpicker.auth.JWTCredential;

import javax.ejb.Singleton;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class JWTCredentialRepository {

    private static final Set<JWTCredential> JWT_CREDENTIALS = new HashSet<>();

    public void save(JWTCredential credential) {
        JWT_CREDENTIALS.add(credential);
    }

    public JWTCredential findByUsername(String username) {
        return JWT_CREDENTIALS.stream().filter(e -> e.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public JWTCredential findByToken(String token) {
        return JWT_CREDENTIALS.stream().filter(e -> e.getToken().equals(token))
                .findFirst()
                .orElse(null);
    }

    public Set<JWTCredential> findAll() {
        return JWT_CREDENTIALS;
    }

    public void remove(String username) {
        JWT_CREDENTIALS.remove(findByUsername(username));
    }
}
