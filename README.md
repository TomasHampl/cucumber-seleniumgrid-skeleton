# cucumber-seleniumgrid-skeleton
This repository contains skeleton for a working cucumber - selenium grid app

## How to use
Clone this repo and adapt as you see fit...there are few pre-requisites you need to have before running:
* selenium grid downloaded and running on your machine
* if your selenium grid uses different browser than Chrome, adjust the WebDriverInit class (this was supposed to be quick & dirty, better implementation made this configurable in pom.xml perhaps)
* you need to have your selenium grid url (default defined in pom is http://172.17.0.1:4444)
* if you wish to override the default grid url, simply pass the ```DSelenium.grid.url=xxx``` (where **xxx** is the url you wish to use) or modify the ```pom.xml```
* screenshots can be taken, they will be stored in 'target' (so if you wish to keep them it's best to implement better location outside of 'temp' folder like 'target')

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