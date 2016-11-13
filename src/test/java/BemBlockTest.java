import bemBlock.Bem;
import bemBlock.BemBlock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.lift.match.DisplayedMatcher.displayed;
import static ru.yandex.qatools.matchers.decorators.MatcherDecorators.should;
import static ru.yandex.qatools.matchers.decorators.MatcherDecorators.timeoutHasExpired;
import static ru.yandex.qatools.matchers.webdriver.TextMatcher.text;

/**
 * Created by def on 13.11.16.
 */
public class BemBlockTest {
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
    public void test() throws InterruptedException {
        webDriver.get("http://money.yandex.ru");

        final Bem headerBlock = new BemBlock("header2", webDriver);
        headerBlock
                .atElement("nav")
                .atBlock("button").withModifier("size", "ml")
                .click();

        final Bem domik = new BemBlock("domik", webDriver);
        assertThat(domik, displayed());

        final Bem authBlock = domik
                .atBlock("auth");
        authBlock
                .atElement("username")
                .fillField("xxxxx");
        authBlock
                .atElement("password")
                .fillField("xxxxx");
        authBlock
                .clickButton2();

        WebElement popupErrorElement = new BemBlock("popup", webDriver)
                .withModifier("visibility", "visible")
                .atElement("content")
                .mixedWith("auth", "error");
        assertThat(popupErrorElement, should(
                text(("Your password should not be the same as your username")))
                .whileWaitingUntil(timeoutHasExpired(5000)));
    }
}
