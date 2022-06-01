# cucumber-seleniumgrid-skeleton
Working skeleton of cucumber java project with selenium grid and rest-assured.
## Technologies used
* cucumber (https://cucumber.io) - as framework implementing BDD principles
* selenium (https://www.selenium.dev) - to simulate browser interaction
* rest-assured (https://rest-assured.io) - to send simple http requests and assess the responses

## Pre-requisites
* maven
* java (at least 1.8 - see below)
## How to use
* Clone project
* Run using ```mvn clean test``` -> this will run the test suite using the class ```RunCucumberTest``` which sets the classpath to resolve to ```cz/tomas/test```. If you wish to modify the package names (understandable), don't forget to update this file to point to the 'new' classpath.
* Project uses a sample feature file ```src/test/resources/cz/tomas/test/simpleUrlTest.feature``` which has two very simple test scenarios

## Dependency Injection
Project is using https://github.com/google/guice as dependency injection framework, to allow you not to worry about instantiation of helper classes and services. Simply annotate them with ```@Singleton``` and inject using ```@Inject```. If you examine the source code, it should become apparent how to use that.



## What it contains
* sample gherkin feature file
* very simple Selenium-driven test
* very simple pure http request test (using https://rest-assured.io/)
* cucumber configured to provide html output (to **target** folder)

## Note
Currently, this skeleton project is set to use java 11, however technically there is no code that would not be compatible with java 8. To compile the code to java 8 simply update both source & target to **1.8**

```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.8.1</version>
    <configuration>
        <encoding>UTF-8</encoding>
        <source>11</source>
        <target>11</target>
        <annotationProcessorPaths>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>1.18.24</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```
## Future plans
* general code quality improvements
* find out which OS & browser combinations are available in the target grid and default to one