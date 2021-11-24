package integration;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.net.NetworkUtils;
import org.selenide.selenoid.setup.SelenoidEdgeOptions;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.FileDownloadMode.FOLDER;
import static com.codeborne.selenide.FileDownloadMode.HTTPGET;
import static com.codeborne.selenide.FileDownloadMode.PROXY;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.files.FileFilters.withExtension;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.selenide.selenoid.setup.SelenoidOptions.all;

public class SelenoidSelenideTest {
  @BeforeEach
  void setUp() {
    closeWebDriver();
    Configuration.remote = "http://localhost:4444/wd/hub";
    Configuration.browser = "edge";
    Configuration.browserCapabilities = new SelenoidEdgeOptions(all());
  }

  @Test
  void downloadViaHttpGet() throws IOException {
    Configuration.proxyEnabled = false;
    Configuration.fileDownload = HTTPGET;
    checkDownload();
  }

  @Test
  void downloadViaFolder() throws IOException {
    Configuration.proxyEnabled = false;
    Configuration.fileDownload = FOLDER;
    checkDownload();
  }

  @Test
  void downloadViaProxy() throws IOException {
    Configuration.proxyEnabled = true;
    Configuration.proxyHost = new NetworkUtils().getNonLoopbackAddressOfThisMachine();
    Configuration.fileDownload = PROXY;
    checkDownload();
  }

  private void checkDownload() throws IOException {
    for (int i = 0; i < 8; i++){
      open("https://the-internet.herokuapp.com/download");
      File file = $(byText("some-file.txt")).download(withExtension("txt"));
      assertThat(file).hasName("some-file.txt");
      assertThat(readFileToString(file, UTF_8)).startsWith("blah");
    }
  }
}
