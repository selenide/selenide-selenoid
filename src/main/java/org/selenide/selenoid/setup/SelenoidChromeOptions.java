package org.selenide.selenoid.setup;

import org.openqa.selenium.chrome.ChromeOptions;

public class SelenoidChromeOptions extends ChromeOptions {
  public SelenoidChromeOptions(SelenoidOptions options) {
    setCapability("selenoid:options", options.asMap());
  }
}

