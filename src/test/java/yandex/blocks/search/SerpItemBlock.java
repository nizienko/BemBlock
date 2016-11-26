package yandex.blocks.search;

import bem.AbstractBemBlock;
import bem.BemDescription;

/**
 * Created by def on 27.11.16.
 */

@BemDescription(block = "serp-item")
public class SerpItemBlock extends AbstractBemBlock {

    private OrganicBlock organicBlock;

    public OrganicBlock atOrganicBlock() {
        return organicBlock;
    }
}
