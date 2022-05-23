package cz.tomas.test.shared;

import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import jakarta.inject.Singleton;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * Utility class that is able to read the maven properties. For this to work, pom.xml has to use the
 * properties-maven-plugin which is able to output the properties defined in pom into a temp file. Effectively this class
 * then tries to read the temp property file :)
 */
@Singleton
public class MavenPropertiesReader {

    private static final String TEMP_PROPERTIES_FILE = "my.properties";
    private static final String SELENIUM_GRID_PROPERTY = "selenium.grid.url";
    private final Logger logger = LoggerFactory.getLogger(MavenPropertiesReader.class);

    private Properties properties;

    public String getSeleniumGridUrl(){
        if(null == properties){
            this.loadProperties();
        }
        if(null != properties){
            if(properties.containsKey("selenium.grid.url")){
                String gridUrl = properties.getProperty(SELENIUM_GRID_PROPERTY);
                Supplier<String> message = () -> "Selenium grid URL: " + gridUrl;
                logger.info(message);
                return gridUrl;
            }
            Supplier<String> message = () -> "Error has occurred - unable to find the property " + SELENIUM_GRID_PROPERTY;
            logger.error(message);
        }
        return null;
    }

    private void loadProperties(){
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(TEMP_PROPERTIES_FILE);
            if(null != inputStream){
                this.properties = new Properties();
                this.properties.load(inputStream);
            }
        } catch (IOException e) {
            System.out.println("Error has occurred when trying to access maven properties.");
        }

    }

}
