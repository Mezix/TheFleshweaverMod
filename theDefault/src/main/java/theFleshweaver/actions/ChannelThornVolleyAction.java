package theFleshweaver.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theFleshweaver.cards.HaloOfThorns;
import theFleshweaver.orbs.ThornVolley;
import theFleshweaver.powers.HaloOfThornsPower;

public class ChannelThornVolleyAction extends AbstractGameAction {
    public ChannelThornVolleyAction(int amount) {
        this.amount = amount;
    }
    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        for(int i = 0; i < amount; i++)
        {
            this.addToBot(new IncreaseMaxOrbAction(1));
            this.addToBot(new ChannelAction(new ThornVolley(), false));
            if(p.hasPower(HaloOfThornsPower.POWER_ID))
            {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p.getPower(HaloOfThornsPower.POWER_ID).amount));
            }
        }
        this.isDone = true;
    }
}
