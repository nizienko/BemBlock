package yandex.blocks.search;

import bem.AbstractBemBlock;
import bem.BemDescription;
import bem.BemListLoader;

import java.util.List;

/**
 * Created by def on 27.11.16.
 */

@BemDescription(block = "serp-list")
public class SerpListBlock extends AbstractBemBlock {

    public List<SerpItemBlock> getSerpList() {
        return new BemListLoader<SerpItemBlock>(
                webDriver, thisBem, SerpItemBlock.class).getBlocks();
    }

    public boolean hasItemWithSubtitle(String text) {
        for (SerpItemBlock serpItemBlock : getSerpList()) {
            if (serpItemBlock.atOrganicBlock().getSubtitleBem()
                    .getText()
                    .contains(text)) {
                return true;
            }
        }
        return false;
    }
}
