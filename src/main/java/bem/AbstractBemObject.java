package bem;

import org.openqa.selenium.*;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nizienko on 15.11.2016.
 */
public abstract class AbstractBemObject implements BemExtended {
    private final static String CONTAINS_TEMPLATE = "[contains(concat(' ', @class, ' '), ' %s ')]";
    private final static String HAS_STRING_TEMPLATE = "[text()='%s']";
    private final static String WITH_STRING_TEMPLATE = ".//*[contains(text(), '%s')]";
    private final List<String> modifiers = new ArrayList<>();
    private final List<BemExtended> mixedBemObjects = new ArrayList<>();
    private final List<BemExtended> hasBemObjects = new ArrayList<>();
    private final List<String> xpathList = new ArrayList<>();
    private String name;
    private SearchContext searchContext;
    private BemExtended parentBemObject;
    private BemObjectType bemObjectType;
    private int index = 0;

    @Override
    public BemExtended init(String name, SearchContext searchContext, BemExtended parentBemObject, BemObjectType type) {
        this.name = name;
        this.searchContext = searchContext;
        this.parentBemObject = parentBemObject;
        this.bemObjectType = type;
        return this;
    }

    @Override
    public BemExtended describe(String name, BemObjectType type) {
        this.name = name;
        this.bemObjectType = type;
        return this;
    }

    @Override
    public void setParentObject(BemExtended parentObject) {
        this.parentBemObject = parentObject;
    }

    public String getFullXpath() {
        final StringBuilder xpath = new StringBuilder();

        if (parentBemObject != null) {
            xpath.append(parentBemObject.getFullXpath());
        } else {
            xpath.append(".");
        }

        xpath
                .append(getXpath());
        return xpath.toString();
    }

    @Override
    public String getXpath() {
        final StringBuilder xpath = new StringBuilder();
        xpath
                .append("//*")
                .append(String.format(CONTAINS_TEMPLATE, getClassName()));
        modifiers.forEach((m) ->
                xpath
                        .append(String.format(CONTAINS_TEMPLATE,
                                getClassName() + "_" + m)));
        mixedBemObjects.forEach((m) ->
                xpath
                        .append(m.getContainsClasses()));
        hasBemObjects.forEach((m) ->
                xpath
                        .append(String.format("[.%s]", m.getXpath())));
        xpathList.forEach((x) -> xpath.append("[" + x + "]"));

        if (index > 0) {
            xpath.append("[" + index + "]");
        }
        return xpath.toString();
    }

    @Override
    public SearchContext getSearchContext() {
        return searchContext;
    }

    public String getContainsClasses() {
        final StringBuilder list = new StringBuilder();
        list.append(String.format(CONTAINS_TEMPLATE, getClassName()));
        modifiers.forEach((s) -> list.append(String.format(CONTAINS_TEMPLATE, getClassName() + "_" + s)));
        return list.toString();
    }

    @Override
    public BemExtended getParentBemObject() {
        return parentBemObject;
    }

    @Override
    public BemObjectType getType() {
        return bemObjectType;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        if (bemObjectType == BemObjectType.ELEMENT) {
            if (parentBemObject == null) {
                throw new IllegalArgumentException(
                        String.format("Элемент %s не может быть сам по себе, должен находится в блоке", name));
            } else {
                BemExtended parentBlock = parentBemObject;
                while (parentBlock != null
                        && parentBlock.getType() != BemObjectType.BLOCK) {
                    parentBlock = parentBlock.getParentBemObject();
                }
                if (parentBlock.getType() == BemObjectType.BLOCK) {
                    final String parentBlockName = parentBlock.getName();
                    return parentBlockName + "__" + name;
                } else {
                    throw new IllegalArgumentException(
                            String.format("Элемент %s не может быть сам по себе, должен находится в блоке", name));
                }
            }
        }
        return name;
    }

    private WebElement getThisElement() {
        try {
            for (WebElement element : searchContext.findElements(
                    By.xpath(getFullXpath()))) {
                if (element.isDisplayed()) {
                    return element;
                }
            }
            return searchContext.findElement(
                    By.xpath(getFullXpath()));
        } catch (UndeclaredThrowableException e) {
            throw new AssertionError(e.getCause().getCause());
        }
    }

    @Override
    public Bem withModifier(String key, String value) {
        modifiers.add(String.format("%s_%s", key, value));
        return this;
    }

    @Override
    public Bem mixedWith(Bem mixedElement) {
        mixedBemObjects.add((BemExtended) mixedElement);
        return this;
    }

    @Override
    public Bem withBem(Bem childBem) {
        BemExtended bem = (BemExtended) childBem;
        if (bem.getType() == BemObjectType.ELEMENT && bem.getParentBemObject() == null) {
            bem.setParentObject(this);
        }
        hasBemObjects.add(bem);
        return this;
    }

    @Override
    public Bem hasText(String text) {
        xpathList.add(String.format(WITH_STRING_TEMPLATE, text));
        return this;
    }

    @Override
    public Bem textIs(String text) {
        xpathList.add(String.format(HAS_STRING_TEMPLATE, text));
        return this;
    }

    @Override
    public Bem withXpath(String xpath) {
        xpathList.add(xpath);
        return this;
    }

    @Override
    public Bem atBlock(String name) {
        return new BemBlock().init(name, searchContext, this, BemObjectType.BLOCK);
    }

    @Override
    public Bem atElement(String name) {
        return new BemElement().init(name, searchContext, this, BemObjectType.ELEMENT);
    }

    @Override
    public int count() {
        return searchContext.findElements(By.xpath(getFullXpath())).size();
    }

    @Override
    public Bem index(int i) {
        this.index = i;
        return this;
    }

    // WebElement implementation

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
        try {
            return getThisElement().isDisplayed();
        } catch (Exception e) {
            return false;
        }
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
    public String getCssValue(String s) {
        return getThisElement().getCssValue(s);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return getThisElement().getScreenshotAs(outputType);
    }

    @Override
    public Rectangle getRect() {
        return getThisElement().getRect();
    }
}
