package yandex.blocks.search;

import bem.AbstractBemBlock;
import bem.BemDescription;
import yandex.blocks.common.ButtonControl;
import yandex.blocks.common.InputControl;

/**
 * Created by def on 27.11.16.
 */

@BemDescription(block = "search2")
public class SearchBlock extends AbstractBemBlock {

    @BemDescription(element = "input")
    private InputControl searchField;

    @BemDescription(element = "button")
    private ButtonControl searchButton;

    public InputControl atSearchField() {
        return searchField;
    }

    public ButtonControl atSearchButton() {
        return searchButton;
    }
}
