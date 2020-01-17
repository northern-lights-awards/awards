package project.aurora.utils;

import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


public class HTTPClient {

    private static HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(60))
            .followRedirects(HttpClient.Redirect.NEVER)
            .proxy(ProxySelector.getDefault())
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    protected String apiKey;

    private HttpResponse doGetRaw(URI httpURI) throws Exception {
        var request = HttpRequest.newBuilder(httpURI)
                .timeout(Duration.ofSeconds(60))
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public String doGet(URI httpURI) throws Exception {
        var response = doGetRaw(httpURI);
        var respString = (String) response.body();

        if (response.statusCode() > 202) {
            throw new Exception(respString);
        }

        return respString;
    }

}
