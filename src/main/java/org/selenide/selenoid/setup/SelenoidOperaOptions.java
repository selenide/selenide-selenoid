package org.selenide.selenoid.setup;

import org.openqa.selenium.opera.OperaOptions;

public class SelenoidOperaOptions extends OperaOptions {
  public SelenoidOperaOptions(SelenoidOptions options) {
    setCapability("selenoid:options", options.asMap());
  }
}
