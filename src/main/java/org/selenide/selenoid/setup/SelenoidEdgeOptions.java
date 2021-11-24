package org.selenide.selenoid.setup;

import org.openqa.selenium.edge.EdgeOptions;

public class SelenoidEdgeOptions extends EdgeOptions {
  public SelenoidEdgeOptions(SelenoidOptions options) {
    setCapability("selenoid:options", options.asMap());
  }
}
