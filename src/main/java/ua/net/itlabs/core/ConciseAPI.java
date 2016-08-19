package ua.net.itlabs.core;

import com.google.common.base.Function;
import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ConciseAPI {
    private static WebDriver driver;

    public static WebDriver getWebDriver() {
        return driver;
    }

    public static void setWebDriver(WebDriver driver) {
        ConciseAPI.driver = driver;
    }

    public static void open(String url) {
        getWebDriver().get(url);
    }

    public static void refresh() {
        getWebDriver().get(getWebDriver().getCurrentUrl());
    }

    public static void doubleClick(WebElement onElement) {
        (new Actions(driver)).doubleClick(onElement).perform();
    }

    public static WebElement $(By locator){
        return assertThat(visibilityOfElementLocated(locator));
    }

    public static WebElement $(String cssSelector){
        return $(By.cssSelector(cssSelector));
    }

    public static WebElement $(ExpectedCondition<WebElement> conditionToWaitParentElement, String innerElementCssSelector) {
        return $(conditionToWaitParentElement, By.cssSelector(innerElementCssSelector));
    }

    public static WebElement $(ExpectedCondition<WebElement> conditionToWaitParentElement, By innerElementLocator) {
         return assertThat(conditionToWaitParentElement).findElement(innerElementLocator);

    }

    public static WebElement $(WebElement parentElement, String innerElementCssSelector) {
        return $(parentElement, By.cssSelector(innerElementCssSelector));
    }

    public static WebElement $(WebElement parentElement, By innerElementLocator) {
        return parentElement.findElement(innerElementLocator);
    }



    public static By byText(String elementText) {
        return By.xpath("//*[text()='" + elementText + "']");
    }

    public static By byTitle(String title) {
        return By.xpath("//*[starts-with(@title,'" + title + "')]");
    }

//        super(".//*/text()[normalize-space(.) = " + Quotes.escape(elementText) + "]/parent::*");

    public static <V> V assertThat(Function<? super WebDriver, V> condition) {
        return assertThat(condition, Configuration.timeout);
    }

    public static <V> V assertThat(Function<? super WebDriver, V> condition, int timeout) {
        return (new WebDriverWait(getWebDriver(), timeout)).until(condition);
    }

}
