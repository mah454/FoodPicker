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

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Singleton
public class TokenProvider {

    private Signer signer;
    private HMACVerifier hmacVerifier;

    @Inject
    @ConfigProperty(name = "foodpicker.jwt.issuer")
    private String jwtIssuer;

    @Inject
    @ConfigProperty(name = "foodpicker.jwt.secretKey")
    private String jwtSecretKey;

    @PostConstruct
    public void init() {
        this.signer = HMACSigner.newSHA256Signer(jwtSecretKey);
        this.hmacVerifier = HMACVerifier.newVerifier(jwtSecretKey);
    }

    public String createToken(String username, Set<String> roles) {
        JWT jwt = new JWT().setIssuer(jwtIssuer)
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setSubject(username)
                .addClaim("roles", roles)
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(60));

        return JWT.getEncoder().encode(jwt, signer);
    }

    @SuppressWarnings("unchecked")
    public JWTCredential getCredential(String token) {
        try {
            JWT decode = JWT.getDecoder().decode(token, hmacVerifier);
            List<String> list = (List<String>) decode.getAllClaims().get("roles");
            return new JWTCredential(decode.subject, new HashSet<>(list), null,token);
        } catch (Exception e) {
            return null;
        }
    }


    public boolean verify(String token) {
        if (token == null) return false;
        try {
            JWT decode = JWT.getDecoder().decode(token, hmacVerifier);
            if (decode.isExpired()) {
                return false;
            }
            if (decode.otherClaims.isEmpty()) {
                return false;
            }
            return decode.issuer.equals(jwtIssuer);
        } catch (Exception e) {
            return false;
        }
    }
}
