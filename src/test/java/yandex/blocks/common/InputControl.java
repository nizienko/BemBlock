package yandex.blocks.common;

import bem.AbstractBemBlock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by def on 26.11.16.
 */
public class InputControl extends AbstractBemBlock {

    public void fill(String text) {
        final WebElement inputControl = thisBem.findElement(
                By.xpath(".//*[contains(concat(' ', @class, ' '), ' input__control ')]"));
        inputControl.sendKeys(text);
    }
}
