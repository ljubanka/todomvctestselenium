package ua.net.itlabs.core;

import com.google.common.base.Function;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomConditions {
    public static ExpectedCondition<WebElement> listElementWithText(final By listLocator, final String text) {
        return elementExceptionsCatcher(new ExpectedCondition<WebElement>() {
            private List<WebElement> elements;
            private List<String> texts;

            public WebElement apply(WebDriver driver) {
                elements = driver.findElements(listLocator);
                texts = Helpers.getTexts(elements);

                for (WebElement el: elements) {
                    if (el.getText().equals(text)) {
                        return el;
                    }
                }
                return null;
            }

            public String toString() {
                return String.format("element with text %s \nto be found in list %s\n", text, texts);
            }
        });
    }

    public static ExpectedCondition<WebElement> listElementWithCSSClass(final By listLocator, final String cssClass) {
        return elementExceptionsCatcher(new ExpectedCondition<WebElement>() {
            private List<WebElement> elements;
            private List<String> classes;

            public WebElement apply(WebDriver driver) {
                elements = driver.findElements(listLocator);
                classes = Helpers.getTexts(elements);

                for (WebElement el: elements) {
                    if (el.getAttribute("class").contains(cssClass)) {
                        return el;
                    }
                }
                return null;
            }

            public String toString() {
                return String.format("element with CSS class %s \nto be found in list %s\n", cssClass, classes);
            }
        });
    }

    public static ExpectedCondition<List<WebElement>> visibleTextsOf(final By elementsLocator, final String... expectedTexts) {
        return elementExceptionsCatcher(new ExpectedCondition<List<WebElement>>() {
            private List<WebElement> elements;
            private List<String> texts;

            public List<WebElement> apply(WebDriver webDriver) {
                elements = webDriver.findElements(elementsLocator);
                texts = new ArrayList<String>();//Collections.emptyList();
                for (WebElement el: elements) {
                    if (el.isDisplayed()) {
                        texts.add(el.getText());
                    }
                }
                if (texts.size() ==0 && expectedTexts.length == 1 && expectedTexts[0].equals("")) {
                    return elements;
                }
                //texts = Helpers.getTexts(elements);
                if (texts.size() != expectedTexts.length) {
                    return null;
                }
                for (int i = 0; i < texts.size(); i++) {
                    if (!texts.get(i).contains(expectedTexts[i])) {
                        return null;
                    }
                }
                return elements;
            }

            public String toString() {
                return String.format("texts in list to be %s \n while actual texts are %s \n" , StringUtils.join(expectedTexts, ", "), StringUtils.join(texts, ", "));
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
