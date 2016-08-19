package ua.net.itlabs.core;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class CustomConditions {
    public static ExpectedCondition<WebElement> listElementWithText(final By listLocator, final String text) {
        return elementExceptionsCatcher(new ExpectedCondition<WebElement>() {
            private List<WebElement> elements;

            public WebElement apply(WebDriver driver) {
                elements = driver.findElements(listLocator);
                for (WebElement el: elements) {
                    if (el.getText().equals(text)) {
                        return el;
                    }
                }
                return null;
            }

            public String toString() {
                return String.format("element with text %s \nto be found in list %s\n", text, elements);
            }
        });
    }

    public static <V> ExpectedCondition<V> elementExceptionsCatcher(final Function<? super WebDriver, V> condition) {
        return new ExpectedCondition<V>() {
            public V apply(WebDriver input) {
                try {
                    return condition.apply(input);
                } catch (StaleElementReferenceException | ElementNotVisibleException | IndexOutOfBoundsException e) {
                    return null;
                }
            }

            public String toString() {
                return condition.toString();
            }
        };
    }

}
