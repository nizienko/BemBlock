package bem;

import org.openqa.selenium.SearchContext;

/**
 * Created by nizienko on 15.11.2016.
 */
public class BemBlock extends AbstractBemObject {

    public BemBlock() {

    }

    public BemBlock(String name, SearchContext searchContext) {
        init(name, searchContext, null, BemObjectType.BLOCK);
    }

    public BemExtended withName(String name) {
        super.describe(name, BemObjectType.BLOCK);
        return this;
    }
}
