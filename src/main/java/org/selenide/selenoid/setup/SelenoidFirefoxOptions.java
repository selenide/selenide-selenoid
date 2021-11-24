package org.selenide.selenoid.setup;

import org.openqa.selenium.firefox.FirefoxOptions;

public class SelenoidFirefoxOptions extends FirefoxOptions {
  public SelenoidFirefoxOptions(SelenoidOptions options) {
    setCapability("selenoid:options", options.asMap());
  }
}
