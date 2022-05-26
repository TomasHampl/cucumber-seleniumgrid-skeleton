package cz.tomas.test.shared;

import cz.tomas.test.dto.Browser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
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
        JsonPath jsonPath = getStatus(gridUrl);
        Map<String,Object> nodes = jsonPath.get("value");

        if(nodes.containsKey("ready")){
            return (boolean) nodes.get("ready");
        }
        return false;
    }

    public List<Browser> getBrowsers(String gridUrl){
        List<Browser> browserList = new ArrayList<>();
        JsonPath jsonPath = getStatus(gridUrl);
        Map<String,String> nodes = jsonPath.getMap("value.nodes*.slots*.stereotype[0][0]", String.class, String.class);
        if(!nodes.isEmpty()){
            Browser browser = Browser.builder()
                    .name(nodes.get("browserName"))
                    .os(nodes.get("platformName"))
                    .build();
            browserList.add(browser);
        }

        return browserList;
    }

    private JsonPath getStatus(String gridUrl){
        RestAssured.defaultParser = Parser.JSON;
        return given()
                .headers("Content-Type",ContentType.JSON,"Accept",ContentType.JSON)
                .get(gridUrl)
                .jsonPath();
    }


}
