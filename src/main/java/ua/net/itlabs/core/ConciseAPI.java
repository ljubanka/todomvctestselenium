package ua.net.itlabs.core;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static ua.net.itlabs.core.CustomConditions.listElementWithCSSClass;
import static ua.net.itlabs.todomvctest.pages.TodoMVCPage.tasks;

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

    public static String url() {
        return getWebDriver().getCurrentUrl();
    }

    public static void refresh() {
        getWebDriver().navigate().refresh();
    }

    public static Actions actions() {
        return new Actions(getWebDriver());
    }

    public static WebElement doubleClick(WebElement onElement) {
        actions().doubleClick(onElement).perform();
        return onElement;
    }

    public static WebElement hover(WebElement onElement) {
        actions().moveToElement(onElement).perform();
        return onElement;
    }

    public static WebElement setValue(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
        return element;
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
        return assertThat(visibilityOf(parentElement.findElement(innerElementLocator)));
    }

    public static WebElement $(ExpectedCondition<WebElement> conditionToWaitElement) {
        return assertThat(conditionToWaitElement);
    }

    public static WebElement $(By locator){
        return assertThat(visibilityOfElementLocated(locator));
    }

    public static WebElement $(String cssSelector){
        return $(By.cssSelector(cssSelector));
    }

    public static void executeJavascript(String JSstring) {
        ((JavascriptExecutor)driver).executeScript(JSstring);
    }

    public static By byText(String elementText) {
        return By.xpath("//*[text()='" + elementText + "']");
    }

    public static By byTitle(String title) {
        return By.xpath("//*[starts-with(@title,'" + title + "')]");
    }

    public static By byCSS(String cssSelector) {
        return By.cssSelector(cssSelector);
    }

//        super(".//*/text()[normalize-space(.) = " + Quotes.escape(elementText) + "]/parent::*");

    public static <V> V assertThat(Function<? super WebDriver, V> condition) {
        return assertThat(condition, Configuration.timeout);
    }

    public static <V> V assertThat(Function<? super WebDriver, V> condition, int timeout) {
        return (new WebDriverWait(getWebDriver(), timeout)).until(condition);
    }

}
