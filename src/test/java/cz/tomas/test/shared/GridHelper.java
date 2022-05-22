package cz.tomas.test.shared;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jakarta.inject.Singleton;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Helper class that contains methods that deal with various fact-finding of the running selenium grid instance (if it is running)
 */
@Singleton
public class GridHelper {

    /**
     * Determines whether the Selenium grid instance is up & running.
     * @param gridUrl is the URL of the grid
     * @return boolean {@code true} in case the grid instance returns that it's running or {@code false} in other cases
     */
    public boolean getGridStatus(String gridUrl){
        Response response = given().get(gridUrl);

        JsonPath jsonPath = response.jsonPath();
        Map<String,Object> nodes = jsonPath.get("value");

        if(nodes.containsKey("ready")){
            return (boolean) nodes.get("ready");
        }
        return false;
    }

}
