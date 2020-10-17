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

package ir.moke.foodpicker.http;

import ir.moke.foodpicker.entity.Profile;
import ir.moke.foodpicker.utils.JsonUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.time.Duration;

@Singleton
public class FanapResourceProvider {

    @Inject
    @ConfigProperty(name = "foodpicker.api.baseUrl")
    private String foodpickerBaseUrl;

    @Inject
    @ConfigProperty(name = "fanap.sso.baseUrl")
    private String ssoBaseUrl;

    @Inject
    @ConfigProperty(name = "fanap.sso.clientId")
    private String clientId;

    @Inject
    @ConfigProperty(name = "fanap.sso.clientSecret")
    private String clientSecret;

    @Inject
    @ConfigProperty(name = "foodpicker.api.callBack")
    private String callBackUri;

    @Inject
    @ConfigProperty(name = "fanap.sso.responseType")
    private String responseType;

    @Inject
    @ConfigProperty(name = "fanap.sso.scope")
    private String scope;

    @Inject
    @ConfigProperty(name = "fanap.sso.api.authorize")
    private String authorizePath;

    @Inject
    @ConfigProperty(name = "fanap.sso.api.token")
    private String tokenPath;

    @Inject
    @ConfigProperty(name = "fanap.api.baseUrl")
    private String fanapBaseUrl;

    @Inject
    @ConfigProperty(name = "fanap.api.path.getUserProfile")
    private String userProfilePath;

    private HttpClient httpClient;

    @PostConstruct
    public void init() {
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .sslContext(SSLTrustManager.instance.getSslContext())
                .build();
    }

    public URI getLoginUri(String state) {
        String uri = ssoBaseUrl + authorizePath
                + "?client_id=" + clientId
                + "&response_type=" + responseType
                + "&redirect_uri=" + foodpickerBaseUrl + callBackUri
                + "&scope=" + scope
                + "&state=" + state;
        return URI.create(uri);
    }

    public String getAccessToken(String authorizeCode) {
        String uri = ssoBaseUrl + tokenPath;
        String formData = "grant_type=authorization_code"
                + "&code=" + authorizeCode
                + "&redirect_uri=" + URLEncoder.encode(foodpickerBaseUrl + callBackUri, Charset.defaultCharset())
                + "&client_id=" + clientId
                + "&client_secret=" + clientSecret;
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(formData))
                .uri(URI.create(uri))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == Response.Status.OK.getStatusCode()) {
                JsonObject jsonObject = JsonUtils.fromJson(response.body(), JsonObject.class);
                return jsonObject.getString("access_token");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Profile getUserProfile(String accessToken) {
        String uri = fanapBaseUrl + userProfilePath;
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("_token_", accessToken)
                .setHeader("_token_issuer_", "1")
                .GET()
                .uri(URI.create(uri))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonUtils.fromJson(response.body(), JsonObject.class);
            JsonObject resultObject = jsonObject.getJsonObject("result");
            return JsonUtils.fromJson(resultObject, Profile.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
