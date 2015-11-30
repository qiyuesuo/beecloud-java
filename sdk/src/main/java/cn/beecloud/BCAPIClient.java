package cn.beecloud;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * BeeCloud REST API请求客户端
 * 
 * @author Ray
 * @Date: 15/7/11
 */
class BCAPIClient {

    public static Client client;

    public static void initClient() {
        ClientConfig configuration = new ClientConfig();
        configuration = configuration.property(
                ClientProperties.CONNECT_TIMEOUT, BCCache.getNetworkTimeout());
        configuration = configuration.property(ClientProperties.READ_TIMEOUT,
                BCCache.getNetworkTimeout());

        try {
            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String urlHostName, SSLSession session) {
                    return urlHostName.equals(session.getPeerHost());
                }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { tm }, null);
            client = ClientBuilder.newBuilder().withConfig(configuration)
                    .sslContext(sslContext).hostnameVerifier(hv).build();
        } catch (Exception e) {
            client = ClientBuilder.newClient(configuration);
            e.printStackTrace();
        }
        client.register(JacksonFeature.class);
    }
}
