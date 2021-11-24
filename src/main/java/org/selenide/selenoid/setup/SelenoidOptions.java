package org.selenide.selenoid.setup;

import java.util.HashMap;
import java.util.Map;

public class SelenoidOptions {
  private final boolean enableVNC;
  private final boolean enableVideo;
  private final boolean enableLog;

  public static SelenoidOptions all() {
    return new SelenoidOptions(true, true, true);
  }

  public SelenoidOptions() {
    this(false, false, false);
  }

  public SelenoidOptions(boolean enableVNC, boolean enableVideo, boolean enableLog) {
    this.enableVNC = enableVNC;
    this.enableVideo = enableVideo;
    this.enableLog = enableLog;
  }

  public SelenoidOptions withVNC() {
    return new SelenoidOptions(true, enableVideo, enableLog);
  }

  public SelenoidOptions withVideo() {
    return new SelenoidOptions(enableVNC, true, enableLog);
  }

  public SelenoidOptions withLog() {
    return new SelenoidOptions(enableVNC, enableVideo, true);
  }

  Map<String, Boolean> asMap() {
    Map<String, Boolean> options = new HashMap<>();
    options.put("enableVNC", enableVNC);
    options.put("enableVideo", enableVideo);
    options.put("enableLog", enableLog);
    return options;
  }
}
