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

import javax.security.enterprise.credential.Credential;
import java.util.Set;

public class JWTCredential implements Credential {
    private String username;
    private Set<String> roles;
    private String token;
    private String accessToken;

    public JWTCredential(String username, Set<String> roles, String accessToken) {
        this.username = username;
        this.roles = roles;
        this.accessToken = accessToken;
    }

    public JWTCredential(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public String getToken() {
        return token;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
