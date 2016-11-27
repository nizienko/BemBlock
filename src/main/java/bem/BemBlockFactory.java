package bem;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Field;

/**
 * Created by nizienko on 23.11.2016.
 */
public class BemBlockFactory {

    public static void initBlocksOnPage(WebDriver webDriver, Bem parentBlock, Object page) {
        Class pageClass = page.getClass();
        while (pageClass != Object.class) {
            for (Field field : pageClass.getDeclaredFields()) {
                initBlockField(page, field, webDriver, parentBlock);
            }
            pageClass = pageClass.getSuperclass();
        }
    }

    private static void initBlockField(Object clazz, Field field, WebDriver webDriver, Bem parentBem) {
        if (IBemBlock.class.isAssignableFrom(field.getType())) {
            BemDescription bemDescription =
                    field.getAnnotation(BemDescription.class) != null ?
                            field.getAnnotation(BemDescription.class) :
                            field.getType().getAnnotation(BemDescription.class);
            if (bemDescription == null) {
                throw new IllegalStateException(
                        String.format("Нужно описать бем блок %s @bem.BemDescription на уровне поля или класса", field.getName()));
            }

            BemExtended thisBem = parseBemFromBemDescription(bemDescription, webDriver, (BemExtended) parentBem);

            IBemBlock thisBemBlock = createBlock(field.getType(), webDriver, thisBem);


            try {
                field.setAccessible(true);
                field.set(clazz, thisBemBlock);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (Bem.class.isAssignableFrom(field.getType())) {
            BemDescription bemDescription = field.getAnnotation(BemDescription.class);
            if (bemDescription != null) {
                try {
                    BemExtended thisBem = parseBemFromBemDescription(bemDescription, webDriver, (BemExtended) parentBem);
                    field.setAccessible(true);
                    field.set(clazz, thisBem);
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static IBemBlock createBlock(Class type, WebDriver webDriver, Bem bem) {
        try {
            final IBemBlock bemBlock = (IBemBlock) type.newInstance();
            bemBlock.setBem(bem);
            bemBlock.setWebDriver(webDriver);

            initBlocksOnPage(webDriver, bem, bemBlock);

            bemBlock.describeBem();
            return bemBlock;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalStateException(
                    String.format("Не удается сделать инстанс бем блока %s", type), e);
        }
    }

    public static BemExtended parseBemFromBemDescription(BemDescription bemDescription, SearchContext searchContext, BemExtended parentBem) {
        BemExtended bem = null;
        if (bemDescription.block().length() > 0) {
            bem = new BemBlock().init(bemDescription.block(),
                    searchContext,
                    bemDescription.noAncestors() ? null : parentBem,
                    BemObjectType.BLOCK);
        } else if (parentBem != null) {
            bem = parentBem;
        } else {
            throw new IllegalStateException("Элемент не имеет смысла вне контекста блока. Не удается инициализировать bem объект, отсутствует имя блока.");
        }
        if (bemDescription.element().length() > 0) {
            bem = (BemExtended) bem.atElement(bemDescription.element());
        }
        if (bemDescription.m_name().length() > 0) {
            bem = (BemExtended) bem.withModifier("name", bemDescription.m_name());
        }
        if (bemDescription.modifiers().length > 0) {
            for (Modifier modifier : bemDescription.modifiers()) {
                bem.withModifier(modifier.key(), modifier.value());
            }
        }
        if (bemDescription.mixed().length > 0) {
            for (MixedBlock mixedBlockDescription : bemDescription.mixed()) {
                Bem mixedBlock = new BemBlock()
                        .withName(mixedBlockDescription.block());
                if (mixedBlockDescription.element().length() > 0) {
                    mixedBlock = mixedBlock.atElement(mixedBlockDescription.element());
                }
                if (mixedBlockDescription.modifiers().length > 0) {
                    for (Modifier mixedModifier : mixedBlockDescription.modifiers()) {
                        mixedBlock.withModifier(mixedModifier.key(), mixedModifier.value());
                    }
                }
                bem.mixedWith(mixedBlock);
            }
        }
        if (bemDescription.hasText().length() > 0) {
            bem.hasText(bemDescription.hasText());
        }
        return bem;
    }
}
