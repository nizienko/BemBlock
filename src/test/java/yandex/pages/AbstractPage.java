package yandex.pages;

import bem.BemBlockFactory;
import org.openqa.selenium.WebDriver;

/**
 * Created by def on 26.11.16.
 */
public class AbstractPage {
    protected WebDriver webDriver;

    public AbstractPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        BemBlockFactory.initBlocksOnPage(webDriver, null, this);
    }
}
