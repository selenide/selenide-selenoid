package integration;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.ClipboardConditions.content;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.FileDownloadMode.HTTPGET;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.clipboard;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenoidClipboardTest {

  @BeforeEach
  public void prepare() {
    Configuration.proxyEnabled = false;
    Configuration.fileDownload = HTTPGET;
    Configuration.remote = "http://localhost:4444/wd/hub";
    open("https://www.w3schools.com/howto/howto_js_copy_clipboard.asp");
    $(".snigel-cmp-framework").shouldBe(visible);
    executeJavaScript("const popup = document.getElementById('snigel-cmp-framework'); popup.parentNode.removeChild(popup);");
  }

  @Test
  public void getClipboardContent() {
    $("#myInput").shouldHave(attribute("value", "Hello World"));
    $("[onclick='myFunction()']").shouldBe(visible).click();
    clipboard().shouldHave(content("Hello World"));
    assertEquals("Hello World", clipboard().getText());
  }

  @Test
  public void setClipboardContent() {
    clipboard().setText("John Wick");
    assertEquals("John Wick", clipboard().getText());
    clipboard().shouldHave(content("John Wick"));
  }

  @Test
  public void setAndGetClipboardMultilineContent() {
    String multilineText = "John\nWick\r\nThe\nGreat\r";
    clipboard().setText(multilineText);
    assertEquals(multilineText, clipboard().getText());
    clipboard().shouldHave(content(multilineText));
  }

  @AfterEach
  public void tearDown() {
    closeWebDriver();
  }
}
