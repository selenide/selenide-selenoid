package org.selenide.selenoid;


import com.codeborne.selenide.Driver;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

public class ClipboardSelenoid {

    private Driver driver;

    public ClipboardSelenoid(Driver driver) {
        this.driver = driver;
    }

    @Nonnull
    @CheckReturnValue
    public String get() {
        return new SelenoidClient(driver.config().remote(), ((RemoteWebDriver) driver.getWebDriver()).getSessionId().toString()).getClipboard();
    }
}
