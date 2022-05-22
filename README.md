# cucumber-seleniumgrid-skeleton
This repository contains skeleton for a working cucumber - selenium grid app

## How to use
Clone this repo and adapt as you see fit...there are few pre-requisited you need to have before running:
* selenium grid downloaded and running on your machine
* if your selenium grid uses different browser than Chrome, adjust the WebDriverInit class (this was supposed to be quick & dirty, better implementation made this configurable in pom.xml perhaps)
* you need to have your selenium grid url (skeleton assumes it's http://172.17.0.1:4444)

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
* using some form of input parameter to allow for customization of selenium grid url 
* browser tear-down feature (currently the browser window stays opened even after the tests finish)
* refactor the webdriver initialization 
* general code quality improvements