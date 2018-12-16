package Client.dropbox;

import apis.DropBoxApi2;
import com.github.scribejava.apis.DropBoxApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuthService;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class DropBoxAPI {
    private static final String API_APP_KEY = "da1ugxx1zwxo9p7";
    private static final String API_APP_SECRET = "q0j8ctaffsbnoec";
    private static final String HOST = "https://api.dropboxapi.com/2/";
    private OAuthService service;
    private String accessToken;

    public DropBoxAPI(String accessToken) {
        this.accessToken = accessToken;
        this.service = getService();
    }

    private JSONObject makeRequest(String endpoint, Verb verb, String payload) {
        OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.dropboxapi.com/2/" + endpoint, service);
        request.addHeader("authorization", "Bearer " + accessToken);
        request.addHeader("Content-Type", "application/json");
        request.addPayload(payload);

        Response response = request.send();
        return (JSONObject) JSONValue.parse(response.getBody());
    }

    public void listFiles() {
        JSONObject object = makeRequest("files/list_folder",Verb.POST,"{\n" +
                "    \"path\": \"\",\n" +
                "    \"recursive\": false,\n" +
                "    \"include_media_info\": false,\n" +
                "    \"include_deleted\": false,\n" +
                "    \"include_has_explicit_shared_members\": false,\n" +
                "    \"include_mounted_folders\": true\n" +
                "}");
        System.out.println(object.toJSONString());
    }

    private static OAuthService getService() {
        return new ServiceBuilder()
                .provider(DropBoxApi2.class)
                .apiKey(API_APP_KEY)
                .apiSecret(API_APP_SECRET)
                .callback("http://localhost:8080/war/dropboxtoken")
                .build();
    }

    public static String getAuthorizeUrl(String username) {
        OAuthService service = getService();
        return service.getAuthorizationUrl(null) + "&state=" + username;
    }

    public static String getAccessToken(String code) {
        OAuthService service = getService();
        Verifier verifier = new Verifier(code);
        return service.getAccessToken(null, verifier).getToken();
    }
}
