package yandex.blocks.search;

import bem.AbstractBemBlock;
import bem.Bem;
import bem.BemDescription;

/**
 * Created by def on 27.11.16.
 */

@BemDescription(block = "organic")
public class OrganicBlock extends AbstractBemBlock {

    @BemDescription(element = "title-wrapper")
    private Bem titleBem;

    @BemDescription(element = "subtitle")
    private Bem subtitleBem;

    @BemDescription(element = "text")
    private Bem text;

    public Bem getTitleBem() {
        return titleBem;
    }

    public Bem getSubtitleBem() {
        return subtitleBem;
    }

    public Bem getText() {
        return text;
    }
}
