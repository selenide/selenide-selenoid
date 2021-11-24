package org.selenide.selenoid.setup;

import org.openqa.selenium.safari.SafariOptions;

public class SelenoidSafariOptions extends SafariOptions {
  public SelenoidSafariOptions(SelenoidOptions options) {
    setCapability("selenoid:options", options.asMap());
  }
}
