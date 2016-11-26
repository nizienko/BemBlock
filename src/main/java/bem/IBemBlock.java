package bem;

import org.openqa.selenium.WebDriver;

/**
 * Created by nizienko on 23.11.2016.
 */
public interface IBemBlock {
    void setBem(Bem bem);
    void setWebDriver(WebDriver webDriver);
    void describeBem();
    Bem getBem();
}
