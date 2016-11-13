package bemBlock;

import org.openqa.selenium.SearchContext;


/**
 * Created by def on 13.11.16.
 */
public class BemBlock extends AbstractBemObject {
    private final String blockName;

    public BemBlock(String blockName, SearchContext searchContext) {
        super(searchContext, blockName);
        this.blockName = blockName;
    }

    public BemBlock(String blockName, SearchContext searchContext, ExtendedBem parentBlock) {
        super(searchContext, blockName, parentBlock);
        this.blockName = blockName;
    }

    @Override
    public String getBlockName() {
        return blockName;
    }

}
