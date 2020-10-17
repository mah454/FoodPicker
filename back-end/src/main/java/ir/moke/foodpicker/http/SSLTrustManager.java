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

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SSLTrustManager implements X509TrustManager {

    public static SSLTrustManager instance = new SSLTrustManager();

    private SSLTrustManager() {
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    private TrustManager[] getTrustManagers() {
        return new TrustManager[]{
                new SSLTrustManager()
        };
    }

    public SSLContext getSslContext() {
        SSLContext context = null;
        try {
            context = SSLContext.getInstance("ssl");
            context.init(null, getTrustManagers(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }
}
