package yandex.blocks.common;

import bem.AbstractBemBlock;
import org.openqa.selenium.By;

/**
 * Created by def on 27.11.16.
 */
public class ButtonControl extends AbstractBemBlock {
    public void click() {
        thisBem.findElement(
                By.xpath(".//*[contains(concat(' ', @class, ' '), ' button ')]"))
                .click();

    }
}
