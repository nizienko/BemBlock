package bem;

/**
 * Created by nizienko on 15.11.2016.
 */
public class BemElement extends AbstractBemObject {
    public BemExtended withName(String name) {
        super.describe(name, BemObjectType.ELEMENT);
        return this;
    }
}
