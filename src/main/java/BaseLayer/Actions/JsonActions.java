package BaseLayer.Actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;


public class JsonActions {
    public static String getJsonAsValue(Response response, String responseJsonPath){
        String result = "";
        try{
            String responseBody = response.getBody().asString();
            JsonPath jsonPath = new JsonPath(responseBody);
            result = jsonPath.getString(responseJsonPath);
        } catch (ClassCastException | JsonPathException e) {
            System.out.println("Incorrect JSON Path: [" + responseJsonPath + "].");
        }
        return result;
    }

    public static boolean validateJsonSchema(Response response, String filePathToJsonFileValidation) throws JsonProcessingException, ProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode schemaNode = objectMapper.readTree(filePathToJsonFileValidation);
        JsonNode jsonNode = objectMapper.readTree(response.asString());

        JsonSchemaFactory SchemaFactory = JsonSchemaFactory.byDefault();
        JsonSchema schema = SchemaFactory.getJsonSchema(schemaNode);

        try {
            schema.validate(jsonNode);
            return true;
        }
        catch (ProcessingException e){
        return false;
        }
    }
}
