package yandex.pages;

import org.openqa.selenium.WebDriver;
import yandex.blocks.search.SearchBlock;

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
        return new SerpPage(webDriver);
    }
}
