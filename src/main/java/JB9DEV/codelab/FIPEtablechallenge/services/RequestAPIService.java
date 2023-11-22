package JB9DEV.codelab.FIPEtablechallenge.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestAPIService {
    private final String BASE_URL;

    public RequestAPIService(String baseURL) {
        this.BASE_URL = baseURL;
    }

    public String get(String path) {
        try(HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(getEndpoint(path))).build();

            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException exception) {
            return "Couldn't fetch data from " + BASE_URL + " due to: " + exception.getMessage();
        }
    }

    private String getEndpoint(String path) {
        String endpoint = BASE_URL;

        if (!BASE_URL.endsWith("/") && !path.startsWith("/")) {
            endpoint = BASE_URL + "/";
        }

        return endpoint + path;
    }
}
