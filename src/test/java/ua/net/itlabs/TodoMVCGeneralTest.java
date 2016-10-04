package ua.net.itlabs;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.Keys;
import ru.yandex.qatools.allure.annotations.Step;
import ua.net.itlabs.categories.Smoke;
import ua.net.itlabs.todomvctest.pages.TodoMVCPage;
import ua.net.itlabs.todomvctest.testconfigs.BaseTest;

import static ua.net.itlabs.todomvctest.pages.TodoMVCPage.TaskType.ACTIVE;
import static ua.net.itlabs.todomvctest.pages.TodoMVCPage.TaskType.COMPLETED;

public class TodoMVCGeneralTest extends BaseTest {


    TodoMVCPage page = new TodoMVCPage();

    @Test
    @Category(Smoke.class)
    public void testTasksCommonFlow() {
        page.givenAtAll();

        page.add("1");
        page.startEdit("1", "1 edited").sendKeys(Keys.ENTER);
        page.assertTasks("1 edited");
        page.assertItemsLeft(1);

        page.filterActive();
        page.assertTasks("1 edited");

        //complete
        page.toggle("1 edited");
        page.assertNoTasks();

        page.filterCompleted();
        page.assertTasks("1 edited");

        page.filterAll();
        page.assertTasks("1 edited");

        page.delete("1 edited");
        page.assertNoTasks();
    }

    @Test
    public void testFilterFromAllToCompleted() {
        page.givenAtAll(page.aTask("1", ACTIVE), page.aTask("2", COMPLETED));

        page.filterCompleted();
        page.assertTasks("2");
        page.assertItemsLeft(1);
    }

    @Test
    public void testFilterFromActiveToAll() {
        page.givenAtActive(page.aTask("1", ACTIVE), page.aTask("2", COMPLETED));

        page.filterAll();
        page.assertTasks("1", "2");
        page.assertItemsLeft(1);
    }

    @Test
    public void testFilterFromCompletedToActive() {
        page.givenAtCompleted(page.aTask("1", ACTIVE), page.aTask("2", COMPLETED));

        page.filterActive();
        page.assertTasks("1");
        page.assertItemsLeft(1);
    }

}
