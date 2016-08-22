package ua.net.itlabs.core;

import com.google.common.base.Function;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.ArrayList;
import java.util.List;

public class CustomConditions {
    public static ExpectedCondition<WebElement> listElementWithText(final By listLocator, final String text) {
        return elementExceptionsCatcher(new ExpectedCondition<WebElement>() {
            private List<String> texts;

            public WebElement apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(listLocator);
                texts = Helpers.getTexts(elements);

                for (int i=0; i< texts.size(); i++) {
                    if (texts.get(i).equals(text)) {
                        return elements.get(i);
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
            private List<String> classes;

            public WebElement apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(listLocator);
                classes = new ArrayList<String>();

                for (WebElement el: elements) {
                    classes.add(el.getAttribute("class"));
                }

                for (int i=0; i< classes.size(); i++) {
                    String[] classesList = StringUtils.split(classes.get(i), " ");
                    for (String el: classesList) {
                        if (el.equals(cssClass)) {
                            return elements.get(i);
                        }
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
            private List<String> texts;

            public List<WebElement> apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(elementsLocator);
                List<WebElement> visibleElements = Helpers.getVisibleElements(elements);
                texts = Helpers.getTexts(visibleElements);

                if (texts.size() != expectedTexts.length) {
                    return null;
                }
                for (int i = 0; i < texts.size(); i++) {
                    if (!texts.get(i).contains(expectedTexts[i])) {
                        return null;
                    }
                }
                return visibleElements;
            }

            public String toString() {
                return String.format("texts in list to be %s \n while actual texts are %s \n" , StringUtils.join(expectedTexts, ", "), StringUtils.join(texts, ", "));
            }
        });
    }

    public static ExpectedCondition<Boolean> sizeOfVisible(final By elementsLocator, final int expectedSize) {
        return elementExceptionsCatcher(new ExpectedCondition<Boolean>() {
            private int listSize;

            public Boolean apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(elementsLocator);
                List<WebElement> visibleElements = Helpers.getVisibleElements(elements);

                listSize = visibleElements.size();
                return listSize == expectedSize;
            }

            public String toString() {
                return String.format("\nsize of list located by: %s\n to be: %s\n while actual size is: %s\n", elementsLocator, expectedSize, listSize);
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
