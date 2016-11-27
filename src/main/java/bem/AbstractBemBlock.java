package bem;

import org.openqa.selenium.WebDriver;

/**
 * Created by nizienko on 23.11.2016.
 */
public abstract class AbstractBemBlock implements IBemBlock {
    protected Bem thisBem;
    protected WebDriver webDriver;

    @Override
    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public void describeBem() {

    }

    @Override
    public Bem getBem() {
        return thisBem;
    }

    @Override
    public void setBem(Bem bem) {
        this.thisBem = bem;
    }
}
