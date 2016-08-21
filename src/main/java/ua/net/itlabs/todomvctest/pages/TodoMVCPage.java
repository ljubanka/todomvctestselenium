package ua.net.itlabs.todomvctest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import ua.net.itlabs.core.ConciseAPI;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static ua.net.itlabs.core.ConciseAPI.*;
import static ua.net.itlabs.core.CustomConditions.*;

public class TodoMVCPage {
    public enum TaskType {
        ACTIVE("false"),
        COMPLETED("true");

        public final String isCompletedValue;

        TaskType(String isCompletedValue) {
            this.isCompletedValue = isCompletedValue;
        }

        public String toString() {
            return this.isCompletedValue;
        }
    }

    public static By tasks = byCSS("#todo-list>li");

    public void ensurePageOpened() {
        if (! url().equals("https://todomvc4tasj.herokuapp.com/")) {
            open("https://todomvc4tasj.herokuapp.com/");
        }
    }

    public class Task {
        String name;
        TaskType type;

        public Task(String name, TaskType type) {
            this.name = name;
            this.type = type;
        }

        public String toString() {
            return "{\\\"completed\\\":" + type + ", \\\"title\\\":\\\"" + name + "\\\"}, ";
        }
    }

    public Task aTask(String name, TaskType type) {
        return new Task(name, type);
    }

    public void givenAtAll(Task... tasks) {
        ensurePageOpened();

        String strJS = "localStorage.setItem(\"todos-troopjs\", \"[  ";
        for (Task task : tasks) {
            strJS += task;
        }
        strJS = strJS.substring(0, strJS.length()-2);
        strJS = strJS + "]\")";
        executeJavascript(strJS);
        refresh();
    }

    public void givenAtAll(TaskType taskType, String... taskTexts) {
        givenAtAll(aTasks(taskType, taskTexts));
    }

    public void givenAtActive(Task... tasks) {
        givenAtAll(tasks);
        filterActive();
    }

    public void givenAtActive(TaskType taskType, String... taskTexts) {
        givenAtAll(aTasks(taskType, taskTexts));
        filterActive();
    }

    public void givenAtCompleted(Task... tasks) {
        givenAtAll(tasks);
        filterCompleted();
    }

    public void givenAtCompleted(TaskType taskType, String... taskTexts) {
        givenAtAll(aTasks(taskType, taskTexts));
        filterCompleted();
    }

    public Task[] aTasks(TaskType taskType, String... taskTexts) {
        Task tasksArray[] = new Task[taskTexts.length];
        for (int i=0; i<taskTexts.length; i++) {
            tasksArray[i] = aTask(taskTexts[i], taskType);
        }
        return tasksArray;
    }

    public void add(String... taskTexts) {
        for (String text: taskTexts) {
            //assertThat(elementToBeClickable(By.cssSelector("#new-todo")));
            $(elementToBeClickable(byCSS("#new-todo"))).sendKeys(text + Keys.ENTER);
        }
    }

    public WebElement startEdit(String oldTask, String newTask) {
        doubleClick($(listElementWithText(tasks, oldTask), "label"));
        //return setValue($($(doubleClick($(listElementWithText(tasks, oldTask), "label")), ".editing"), ".edit"), newTask);
        return setValue($(listElementWithCSSClass(tasks, "editing"), ".edit"), newTask);
        //return setValue($($(doubleClick($(listElementWithText(tasks, oldTask), "label")), ".editing"), ".edit"), newTask);
//        $(listElementWithCSSClass(tasks, "editing"), ".edit").clear();
//        $(listElementWithCSSClass(tasks, "editing"), ".edit").sendKeys(newTask);
//        return $(listElementWithCSSClass(tasks, "editing"), ".edit");
    }

    public void delete(String taskText) {
        hover($(listElementWithText(tasks, taskText)));
        $(listElementWithText(tasks, taskText), ".destroy").click();
        //$(hover($(listElementWithText(tasks, taskText))), ".destroy").click();
    }

    public void toggle(String taskText) {
        $(listElementWithText(tasks, taskText), ".toggle").click();
    }

    public void toggleAll() {
        $("#toggle-all").click();
    }

    public void clearCompleted() {
        $("#clear-completed").click();
    }

    public void filterAll() {
        $(By.linkText("All")).click();
    }

    public void filterActive() {
        $(By.linkText("Active")).click();
    }

    public void filterCompleted() {
        $(By.linkText("Completed")).click();
    }

    public void assertTasks(String... taskTexts) {
        assertThat(visibleTextsOf(tasks, taskTexts));
    }

    public void assertNoTasks() {
        assertThat(sizeOfVisible(tasks, 0));
    }

    public void assertItemsLeft(int count) {
        listElementWithText(byCSS("#todo-count>strong"), Integer.toString(count));
    }
}
