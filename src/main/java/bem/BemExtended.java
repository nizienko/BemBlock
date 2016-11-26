package bem;

import org.openqa.selenium.SearchContext;

/**
 * Created by nizienko on 15.11.2016.
 */
public interface BemExtended extends Bem {
    BemExtended init(String name, SearchContext searchContext, BemExtended parentBemObject, BemObjectType type);

    BemExtended describe(String name, BemObjectType type);

    String getFullXpath();

    String getXpath();

    BemExtended getParentBemObject();

    BemObjectType getType();

    String getName();

    SearchContext getSearchContext();

    String getClassName();

    String getContainsClasses();

    void setParentObject(BemExtended parentObject);
}
