package yandex.pages;

import org.openqa.selenium.WebDriver;
import yandex.blocks.search.SearchBlock;
import yandex.blocks.search.SerpListBlock;

/**
 * Created by def on 27.11.16.
 */
public class SerpPage extends AbstractPage {
    private SearchBlock searchBlock;
    private SerpListBlock serpListBlock;

    public SerpPage(WebDriver webDriver) {
        super(webDriver);
    }

    public SearchBlock atSearchBlock() {
        return searchBlock;
    }

    public SerpListBlock atSerpListBlock() {
        return serpListBlock;
    }
}
