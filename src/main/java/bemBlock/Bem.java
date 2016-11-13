package bemBlock;

import org.openqa.selenium.WebElement;

/**
 * Created by def on 13.11.16.
 */
public interface Bem extends WebElement {
    Bem atBlock(String name);

    Bem atElement(String name);

    Bem withModifier(String name);

    Bem withModifier(String key, String value);

    Bem mixedWith(String blockName);

    Bem mixedWith(String blockName, String elementName);

    Bem fillField(String data);

    Bem clickButton();

    Bem clickButton2();
}
