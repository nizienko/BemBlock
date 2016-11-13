package bemBlock;

import org.openqa.selenium.SearchContext;

/**
 * Created by def on 13.11.16.
 */
public class BemElement extends AbstractBemObject {
    private final String elementName;
    private final String blockName;

    public BemElement(String elementName, SearchContext searchContext, ExtendedBem parentBem) {
        super(searchContext, parentBem.getBlockName() + "__" + elementName, parentBem);
        this.elementName = elementName;
        this.blockName = parentBem.getBlockName();
    }

    @Override
    public String getBlockName() {
        return blockName;
    }
}
