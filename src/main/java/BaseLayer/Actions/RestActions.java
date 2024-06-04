package BaseLayer.Actions;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class RestActions {

    public enum requestType{
        POST, GET, DELETE, PUT, PATCH
    }

    private static RequestSpecification prepareRequst(String targetURL, String endPoint, List<List<Object>> queryParam,
                                                      List<List<Object>> formParam, Map<String, String> headers, ContentType contentType){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(String.valueOf(targetURL));
        builder.setBasePath(endPoint);
        builder.setContentType(contentType.toString());

        if(queryParam != null){
            queryParam.forEach(param -> {
                builder.addQueryParam(param.get(0).toString(), param.get(1));
            });
        }

        if(formParam != null){
            formParam.forEach(param ->{
                builder.addFormParam(param.get(0).toString(), param.get(1));
            });
        }

        if(headers != null && !headers.isEmpty()){
            headers.forEach((headerName, headerValue)->{
                builder.addHeader(headerName, headerValue);
            });
        }

        builder.setRelaxedHTTPSValidation();
        return builder.build();
    }

    public static Response sendRequest(String targetURL, String endPoint, requestType requestType, String JSONObject, List<List<Object>> queryParam,
                                       List<List<Object>> formParam, Map<String, String> headers, ContentType contentType){
        Response response;
        RequestSpecification request;
        if(JSONObject != null){
            request = given().spec(prepareRequst(targetURL, endPoint, formParam, queryParam, headers, contentType)).body(JSONObject);
        }
        else{
            request = given().spec(prepareRequst(targetURL, endPoint, formParam, queryParam, headers, contentType));
        }

        switch (requestType){
            case POST:
                response = request.when().post().then().extract().response();
                System.out.println("Response: "+ response.asString());
                return response;

            case PUT:
                response = request.when().put().then().extract().response();
                System.out.println("Response: "+ response.asString());
                return response;

            case GET:
                response = request.when().get().then().extract().response();
                System.out.println("Response: "+ response.asString());
                return response;

            case DELETE:
                response = request.when().delete().then().extract().response();
                System.out.println("Response: "+ response.asString());
                return response;

            case PATCH:
                response = request.when().patch().then().extract().response();
                System.out.println("Response: "+ response.asString());
                return response;

            default:
                break;
        }

        System.out.println("Response is NULL");
        return null;
    }
}