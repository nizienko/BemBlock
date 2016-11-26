package yandex.pages;

import org.openqa.selenium.WebDriver;
import yandex.blocks.search.SearchBlock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.lift.match.DisplayedMatcher.displayed;
import static ru.yandex.qatools.matchers.decorators.MatcherDecorators.should;
import static ru.yandex.qatools.matchers.decorators.MatcherDecorators.timeoutHasExpired;

/**
 * Created by def on 26.11.16.
 */
public class YandexPage extends AbstractPage {
    public YandexPage(WebDriver webDriver) {
        super(webDriver);
    }

    private SearchBlock searchBlock;

    public YandexPage open() {
        webDriver.get("https://yandex.ru");
        return this;
    }

    public YandexPage fillSearchField(String text) {
        searchBlock.atSearchField().fill(text);
        return this;
    }

    public SerpPage clickSearchButton() {
        searchBlock.atSearchButton().click();

        final SerpPage serpPage = new SerpPage(webDriver);
        assertThat(
                serpPage.atSerpListBlock().getBem(),
                should(displayed()).whileWaitingUntil(timeoutHasExpired(2000)));
        return serpPage;
    }
}
