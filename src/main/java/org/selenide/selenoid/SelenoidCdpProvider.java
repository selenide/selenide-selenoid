package org.selenide.selenoid;

import com.codeborne.selenide.Config;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.impl.CdpProvider;
import com.codeborne.selenide.impl.SeleniumCdpProvider;
import java.util.Optional;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.devtools.CdpInfo;
import org.openqa.selenium.devtools.CdpVersionFinder;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.DevToolsException;
import org.openqa.selenium.devtools.SeleniumCdpConnection;
import org.openqa.selenium.devtools.noop.NoOpCdpInfo;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ParametersAreNonnullByDefault
public class SelenoidCdpProvider implements CdpProvider {
  private static final Logger log = LoggerFactory.getLogger(SelenoidCdpProvider.class);

  @CheckReturnValue
  @Nonnull
  @Override
  public DevTools getCdp(Driver driver) {
    Config config = driver.config();
    if (config.remote() == null) {
      log.debug("Working in local browser. Switching to a default devtools implementation.");
      return new SeleniumCdpProvider().getCdp(driver);
    } else {
      chromiumGuard(driver);
      String cdpUrl = getSelenoidCdpUrl(driver, config);
      HasCapabilities webDriver = (HasCapabilities) driver.getWebDriver();
      Capabilities capabilities = webDriver.getCapabilities();
      CdpInfo info = getCdpInfo(capabilities);
      Capabilities seleniumLikeCapabilities = addSe4CdpCapability(cdpUrl, capabilities);
      return createDevTools(info, seleniumLikeCapabilities);
    }
  }

  @CheckReturnValue
  @Nonnull
  private DevTools createDevTools(CdpInfo info, Capabilities seleniumLikeCapabilities) {
    Optional<DevTools> devTools = SeleniumCdpConnection
      .create(seleniumLikeCapabilities)
      .map(conn -> new DevTools(info::getDomains, conn));
    if (!devTools.isPresent()) {
      throw new DevToolsException("Unable to create DevTools connection");
    }
    DevTools devToolsValue = devTools.get();
    devToolsValue.createSessionIfThereIsNotOne();
    return devToolsValue;
  }

  @CheckReturnValue
  @Nonnull
  private String getSelenoidCdpUrl(Driver driver, Config config) {
    String selenoidAddress = config.remote().replace("/wd/hub", "");
    String sessionId = driver.getSessionId().toString();
    String cdpUrl = String.format("%s/devtools/%s", selenoidAddress, sessionId);
    log.debug("Selenoid CDP url is: {}", cdpUrl);
    return cdpUrl;
  }

  @CheckReturnValue
  @Nonnull
  private CdpInfo getCdpInfo(Capabilities caps) {
    String version = caps.getBrowserVersion();
    return new CdpVersionFinder().match(version).orElseGet(NoOpCdpInfo::new);
  }

  @CheckReturnValue
  @Nonnull
  private Capabilities addSe4CdpCapability(String cdpUrl, Capabilities capabilities) {
    DesiredCapabilities emptyCaps = new DesiredCapabilities();
    emptyCaps.setCapability("se:cdp", cdpUrl);
    return emptyCaps.merge(capabilities);
  }
}
