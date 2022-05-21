Feature: Simple url test

  Scenario: browser-based test
    Given url to visit with browser is "https://www.selenium.dev"
    Then page title is "selenium"

  Scenario: send http request
    Given url to send http get request to is "https://www.selenium.dev"
    Then http status is 200