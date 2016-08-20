package ua.net.itlabs;

import org.junit.Test;
import ua.net.itlabs.todomvctest.pages.TodoMVCPage;
import ua.net.itlabs.todomvctest.testconfigs.BaseTest;

import static org.openqa.selenium.Keys.ENTER;
import static ua.net.itlabs.core.ConciseAPI.$;
import static ua.net.itlabs.todomvctest.pages.TodoMVCPage.TaskType.ACTIVE;
import static ua.net.itlabs.todomvctest.pages.TodoMVCPage.TaskType.COMPLETED;


public class TodoMVCActiveFilterTest extends BaseTest {

    TodoMVCPage page = new TodoMVCPage();

    @Test
    public void testAdd() {
        page.givenAtActive(ACTIVE, "1");

        page.add("2");
        page.assertTasks("1", "2");
        page.assertItemsLeft(2);
    }

    @Test
    public void testEdit() {
        page.givenAtActive(ACTIVE, "1", "2");

        page.startEdit("2", "2 edited").sendKeys(ENTER);
        page.assertTasks("1", "2 edited");
        page.assertItemsLeft(2);
    }

    @Test
    public void testDelete() {
        page.givenAtActive(ACTIVE, "1");

        page.delete("1");
        page.assertNoTasks();
    }

    @Test
    public void testCompleteAll() {
        page.givenAtActive(ACTIVE, "1", "2");

        page.toggleAll();
        page.assertNoTasks();
        page.assertItemsLeft(0);
    }

    @Test
    public void testClearCompleted() {
        page.givenAtActive(page.aTask("1", ACTIVE), page.aTask("2", COMPLETED), page.aTask("3", COMPLETED));

        page.clearCompleted();
        page.assertTasks("1");
        page.assertItemsLeft(1);
    }

    @Test
    public void testEditClickOutside() {
        page.givenAtActive(ACTIVE, "1", "2");

        page.startEdit("2", "2 edited");
        $("#new-todo").click();
        page.assertTasks("1", "2 edited");
        page.assertItemsLeft(2);
    }
}
