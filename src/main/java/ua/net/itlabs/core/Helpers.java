package ua.net.itlabs.core;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Helpers {
    public static List<String> getTexts(List<WebElement> elements) {
        List<String> texts = new ArrayList<String>();

        for (WebElement el: elements) {
            texts.add(el.getText());
        }
        return texts;
    }

    public static List<WebElement> getVisibleElements(List<WebElement> elements) {
        List<WebElement> visibleElements = new ArrayList<WebElement>();
        for (WebElement el: elements) {
            if (el.isDisplayed()) {
                visibleElements.add(el);
            }
        }
        return visibleElements;
    }
}
