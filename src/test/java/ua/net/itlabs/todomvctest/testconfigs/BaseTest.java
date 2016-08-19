package ua.net.itlabs.todomvctest.testconfigs;

import com.google.common.io.Files;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;

import static ua.net.itlabs.core.ConciseAPI.getWebDriver;
import static ua.net.itlabs.core.ConciseAPI.setWebDriver;

public class BaseTest {

    @BeforeClass
    public static void setup() {
        setWebDriver(new FirefoxDriver());
    }

    @AfterClass
    public static void finish() {
        getWebDriver().quit();
    }


}
