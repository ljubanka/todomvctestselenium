package ua.net.itlabs;

import org.junit.Test;
import org.openqa.selenium.Keys;
import ua.net.itlabs.todomvctest.pages.TodoMVCPage;
import ua.net.itlabs.todomvctest.testconfigs.BaseTest;

import static org.openqa.selenium.Keys.ESCAPE;
import static ua.net.itlabs.todomvctest.pages.TodoMVCPage.TaskType.ACTIVE;
import static ua.net.itlabs.todomvctest.pages.TodoMVCPage.TaskType.COMPLETED;

public class TodoMVCAllFilterTest extends BaseTest {

    TodoMVCPage page = new TodoMVCPage();

    @Test
    public void testComplete() {
        page.givenAtAll(ACTIVE, "1", "2");

        page.toggle("2");
        page.assertTasks("1", "2");
        page.assertItemsLeft(1);
    }

    @Test
    public void testReopen() {
        page.givenAtAll(COMPLETED, "1", "2");

        page.toggle("2");
        page.assertTasks("1", "2");
        page.assertItemsLeft(1);
    }

    @Test
    public void testCompleteAll() {
        page.givenAtAll(page.aTask("1", ACTIVE), page.aTask("2", COMPLETED), page.aTask("3", ACTIVE));

        page.toggleAll();
        page.assertTasks("1", "2", "3");
        page.assertItemsLeft(0);
    }

    @Test
    public void testClearCompleted() {
        page.givenAtAll(COMPLETED, "1", "2", "3");

        page.clearCompleted();
        page.assertNoTasks();
    }

    @Test
    public void testCancelEdit() {
        page.givenAtAll(ACTIVE, "1");

        page.startEdit("1", "1 cancel edit").sendKeys(ESCAPE);
        page.assertTasks("1");
        page.assertItemsLeft(1);
    }

    @Test
    public void testEditClickTab() {
        page.givenAtAll(ACTIVE, "1", "2", "3");

        page.startEdit("2", "2 edited").sendKeys(Keys.TAB);
        page.assertTasks("1", "2 edited", "3");
        page.assertItemsLeft(3);
    }

    @Test
    public void testDeleteByEmpty() {
        page.givenAtAll(ACTIVE, "1", "2", "3");

        page.startEdit("2", "").sendKeys(Keys.ENTER);
        page.assertTasks("1", "3");
    }
}
