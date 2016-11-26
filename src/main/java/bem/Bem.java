package bem;

import org.openqa.selenium.WebElement;

/**
 * Created by nizienko on 15.11.2016.
 */
public interface Bem extends WebElement {
    Bem withModifier(String key, String value);

    Bem mixedWith(Bem mixedElement);

    Bem withBem(Bem childBem);

    Bem hasText(String text);

    Bem textIs(String text);

    Bem withXpath(String xpath);

    Bem atBlock(String name);

    Bem atElement(String name);

    Bem withName(String name);

    int count();

    Bem index(int i);

}
