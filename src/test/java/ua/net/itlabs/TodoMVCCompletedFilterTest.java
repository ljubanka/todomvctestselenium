package ua.net.itlabs;

import org.junit.Test;
import ua.net.itlabs.todomvctest.pages.TodoMVCPage;
import ua.net.itlabs.todomvctest.testconfigs.BaseTest;

import static ua.net.itlabs.todomvctest.pages.TodoMVCPage.TaskType.COMPLETED;

public class TodoMVCCompletedFilterTest extends BaseTest {

    TodoMVCPage page = new TodoMVCPage();

    @Test
    public void testDelete() {
        page.givenAtCompleted(COMPLETED, "1", "2");

        page.delete("2");
        page.assertTasks("1");
        page.assertItemsLeft(0);
    }

    @Test
    public void testReopen() {
        page.givenAtCompleted(COMPLETED, "1");

        page.toggle("1");
        page.assertNoTasks();
        page.assertItemsLeft(1);
    }

    @Test
    public void testClearCompleted() {
        page.givenAtCompleted(COMPLETED, "1");

        page.clearCompleted();
        page.assertNoTasks();
    }

    @Test
    public void testReopenAll() {
        page.givenAtCompleted(COMPLETED, "1", "2");

        page.toggleAll();
        page.assertNoTasks();
        page.assertItemsLeft(2);
    }
}
