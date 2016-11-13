package bemBlock;

import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by def on 13.11.16.
 */
public abstract class AbstractBemObject implements ExtendedBem {
    private final static String CONTAINS_TEMPLATE = "[contains(concat(' ', @class, ' '), ' %s ')]";
    private final SearchContext searchContext;
    private final String className;
    private final List<String> modificators = new ArrayList<>();
    private final List<String> mixedBemList = new ArrayList<>();
    private ExtendedBem parentBlock;

    AbstractBemObject(SearchContext searchContext, String className) {
        this.searchContext = searchContext;
        this.className = className;
    }

    AbstractBemObject(SearchContext searchContext, String className, ExtendedBem parentBlock) {
        this.searchContext = searchContext;
        this.className = className;
        this.parentBlock = parentBlock;
    }

    public Bem atBlock(String innerBlockName) {
        return new BemBlock(innerBlockName, searchContext, this);
    }

    public Bem atElement(String innerElementName) {
        return new BemElement(innerElementName, searchContext, this);
    }

    public Bem withModifier(String name) {
        modificators.add(name);
        return this;
    }

    public Bem withModifier(String key, String value) {
        modificators.add(key + "_" + value);
        return this;
    }

    public Bem mixedWith(String blockName) {
        mixedBemList.add(blockName);
        return this;
    }

    public Bem mixedWith(String blockName, String elementName) {
        mixedBemList.add(blockName + "__" + elementName);
        return this;
    }

    public Bem fillField(String data) {
        Bem field = this.atBlock("input")
                .atElement("control");
        field.clear();
        field.sendKeys(data);
        return this;
    }

    public Bem clickButton() {
        this.atBlock("button").click();
        return this;
    }

    public Bem clickButton2() {
        this.atBlock("button2").click();
        return this;
    }


    @Override
    public String toString() {
        return this.getXpathString();
    }

    private WebElement getThisElement() {
        for (WebElement element : searchContext.findElements(
                By.xpath(getXpathString()))) {
            if (element.isDisplayed()) {
                return element;
            }
        }
        return searchContext.findElement(
                By.xpath(getXpathString()));
    }

    @Override
    public void click() {
        getThisElement()
                .click();
    }

    @Override
    public void submit() {
        getThisElement()
                .submit();
    }

    @Override
    public void sendKeys(CharSequence... charSequences) {
        getThisElement()
                .sendKeys(charSequences);
    }

    @Override
    public void clear() {
        getThisElement()
                .clear();
    }

    @Override
    public String getTagName() {
        return getThisElement()
                .getTagName();
    }

    @Override
    public String getAttribute(String s) {
        return getThisElement()
                .getAttribute(s);
    }

    @Override
    public boolean isSelected() {
        return getThisElement()
                .isSelected();
    }

    @Override
    public boolean isEnabled() {
        return getThisElement().isEnabled();
    }

    @Override
    public String getText() {
        return getThisElement().getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return getThisElement().findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return getThisElement().findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return getThisElement().isDisplayed();
    }

    @Override
    public Point getLocation() {
        return getThisElement().getLocation();
    }

    @Override
    public Dimension getSize() {
        return getThisElement().getSize();
    }

    @Override
    public Rectangle getRect() {
        return getThisElement().getRect();
    }

    @Override
    public String getCssValue(String s) {
        return getThisElement().getCssValue(s);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return getThisElement().getScreenshotAs(outputType);
    }

    public String getXpathString() {
        final StringBuilder xpath = new StringBuilder();
        if (parentBlock != null) {
            xpath
                    .append(parentBlock.getXpathString());
        }
        xpath
                .append("//*")
                .append(String.format(CONTAINS_TEMPLATE, className));
        modificators.forEach((m) ->
                xpath
                        .append(String.format(CONTAINS_TEMPLATE,
                                className + "_" + m)));
        mixedBemList.forEach((m) -> xpath
                .append(String.format(CONTAINS_TEMPLATE, m)));
        return xpath.toString();
    }
}
