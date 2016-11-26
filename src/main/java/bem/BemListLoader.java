package bem;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by nizienko on 23.11.2016.
 */
public class BemListLoader<B extends IBemBlock> {
    private WebDriver webDriver;
    private Supplier<Bem> bemSupplier;
    private Class clazz;

    public BemListLoader(WebDriver webDriver, Supplier<Bem> bemSupplier, Class clazz) {
        this.webDriver = webDriver;
        this.bemSupplier = bemSupplier;
        this.clazz = clazz;
    }

    public BemListLoader(WebDriver webDriver, Bem parentBem, Class clazz) {
        this.webDriver = webDriver;
        BemDescription bemDescription = (BemDescription) clazz.getAnnotation(BemDescription.class);
        if (bemDescription == null) {
            throw new IllegalStateException(
                    String.format("Нужно описать бем блок %s @BemDescription на уровне класса", clazz.getName()));
        }
        this.bemSupplier = () -> BemBlockFactory.parseBemFromBemDescription(
                bemDescription, webDriver, (BemExtended) parentBem);
        this.clazz = clazz;
    }

    public List<B> getBlocks() {
        List<B> blocks = new ArrayList<>();

        int count = bemSupplier.get().count();
        for (int i = 1; i <= count; i++) {
            B block =
                    (B) BemBlockFactory
                            .createBlock(clazz, webDriver, bemSupplier.get().index(i));
            blocks.add(block);
        }
        return blocks;
    }
}
