package ua.net.itlabs.todomvctest.testconfigs;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.firefox.FirefoxDriver;
import ua.net.itlabs.core.Configuration;

import static ua.net.itlabs.core.ConciseAPI.getWebDriver;
import static ua.net.itlabs.core.ConciseAPI.setWebDriver;

public class BaseTest {

    @BeforeClass
    public static void setup() {
        setWebDriver(new FirefoxDriver());
        Configuration.timeout = 16;
    }

    @AfterClass
    public static void finish() {
        getWebDriver().quit();
    }


}
