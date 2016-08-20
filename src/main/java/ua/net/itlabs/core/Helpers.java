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
}
