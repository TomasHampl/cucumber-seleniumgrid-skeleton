Feature: Simple url test

  Scenario: browser-based test
    Given url to visit with browser is "https://www.selenium.dev"
    Then page title is "selenium"
    And we save a screenshot

  Scenario: find and click test
    Given url to visit with browser is "https://www.selenium.dev"
    When we click on element with "Downloads" text
    Then page title is "Downloads | Selenium"
    And we save a screenshot

  Scenario: send http request
    Given url to send http get request to is "https://www.selenium.dev"
    Then http status is 200