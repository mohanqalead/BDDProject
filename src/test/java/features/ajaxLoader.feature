Feature: Verify AJAX Loader functionality on WebDriverUniversity

  Scenario: Verify AJAX Loader and Popup functionality
    Given I launch the URL "http://webdriveruniversity.com/index.html"
    When I verify the AJAX LOADER is present
    And I click on the AJAX LOADER link
    And I click on the Click Me button
    Then I verify the popup is displayed