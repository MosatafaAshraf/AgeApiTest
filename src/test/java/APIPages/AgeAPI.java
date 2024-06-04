package APIPages;

import BaseLayer.Actions.RestActions;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.*;


public class AgeAPI {
    private static final String endpoint = "agify.io";
    private static final String BaseUri = "https://api.";

    public static String getAge(){
        HashMap<String, String> headers = new HashMap<>();
        Response response = RestActions.sendRequest(BaseUri, endpoint, RestActions.requestType.GET, null, null, null, headers, ContentType.JSON);
        return response.asString();
    }

}
