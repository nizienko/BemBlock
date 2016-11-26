package yandex;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.lift.match.DisplayedMatcher;
import ru.yandex.qatools.matchers.webdriver.driver.CanFindElementMatcher;
import yandex.pages.SerpPage;
import yandex.pages.YandexPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.openqa.selenium.lift.match.DisplayedMatcher.*;
import static ru.yandex.qatools.matchers.decorators.MatcherDecorators.should;
import static ru.yandex.qatools.matchers.decorators.MatcherDecorators.timeoutHasExpired;

/**
 * Created by def on 26.11.16.
 */
public class YandexTest {
    private static WebDriver webDriver;

    @BeforeClass
    public static void before() {
        System.setProperty("webdriver.gecko.driver", "/home/def/soft/ff/geckodriver");
        webDriver = new FirefoxDriver();
    }

    @AfterClass
    public static void closeDriver() {
        webDriver.quit();
    }

    @Test
    public void searchTest() throws InterruptedException {
        final SerpPage serpPage = new YandexPage(webDriver)
                .open()
                .fillSearchField("bem")
                .clickSearchButton();

        assertThat(
                serpPage.atSerpListBlock().hasItemWithSubtitle("tech.yandex.ru"),
                is(true));
    }
}
