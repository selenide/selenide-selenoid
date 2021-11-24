package integration;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.edge.EdgeDriver;
import org.selenide.selenoid.setup.SelenoidEdgeOptions;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.files.FileFilters.withExtension;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.selenide.selenoid.setup.SelenoidOptions.all;

public class FileDownloadTest {
  @BeforeEach
  void setUp() {
    closeWebDriver();

    Configuration.fileDownload = FileDownloadMode.FOLDER;
    Configuration.browser = "edge";
    Configuration.browserCapabilities = new SelenoidEdgeOptions(all());
  }

  @Test
  void downloadFileInSelenoid() throws IOException {
    Configuration.remote = "http://localhost:4444/wd/hub";
    open("https://the-internet.herokuapp.com/download");

    File file = $(byText("some-file.txt")).download(withExtension("txt"));

    assertThat(file.getName()).isEqualTo("some-file.txt");
    assertThat(readFileToString(file, UTF_8)).isEqualTo("blah");
  }

  @Test
  void downloadFileInLocalBrowser() throws IOException {
    Configuration.remote = null;
    Configuration.browserCapabilities = new MutableCapabilities();
    open("https://the-internet.herokuapp.com/download");

    File file = $(byText("some-file.txt")).download(withExtension("txt"));

    assertThat(file.getName()).isEqualTo("some-file.txt");
    assertThat(readFileToString(file, UTF_8)).isEqualTo("blah");
    assertThat(WebDriverRunner.getWebDriver()).isInstanceOf(EdgeDriver.class);
  }
}
