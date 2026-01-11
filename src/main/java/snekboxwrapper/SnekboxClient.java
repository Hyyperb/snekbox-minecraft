package snekboxwrapper;

import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;



public class SnekboxClient {
    private final String snekboxURI;

    public SnekboxClient(String snekboxURI){
        this.snekboxURI = snekboxURI;
    }
    public String eval( String code ){
        String escapedCode = code
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
        String requestJson = "{\"input\": \""+ escapedCode +"\"}";
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(snekboxURI + "/eval"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestJson))
                    .build();
        } catch (URISyntaxException e) {
            return "URISyntaxException: " + e.toString();
        }


        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            return "IOException: " + snekboxURI + " - " + requestJson +  " - "+ e.toString();
        } catch (InterruptedException e) {
            return "InterruptedException: " + e.toString();
        }

        JSONObject json = new JSONObject(response.body());

        return json.getString("stdout");
    }
}
