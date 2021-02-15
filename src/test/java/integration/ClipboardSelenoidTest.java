package integration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.selenide.selenoid.ClipboardSelenoid;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClipboardSelenoidTest {

    @BeforeEach
    public void prepare() {
        Configuration.remote = "http://localhost:4444/wd/hub";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("86.0");
        Configuration.browserCapabilities = capabilities;
    }

    @Test
    public void getClipboardContent() {
        open("https://www.w3schools.com/howto/howto_js_copy_clipboard.asp");
        $("#myInput").should(Condition.attribute("value", "Hello World"));
        $("[onclick='myFunction()']").should(Condition.visible).click();
        String clipboard = new ClipboardSelenoid(WebDriverRunner.driver()).get();
        assertEquals("Hello World", clipboard, "clipboard content doesn't match");
    }
}
