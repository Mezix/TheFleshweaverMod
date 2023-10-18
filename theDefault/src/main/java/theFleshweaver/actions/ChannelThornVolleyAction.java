package theFleshweaver.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import theFleshweaver.orbs.ThornVolley;

public class ChannelThornVolleyAction extends AbstractGameAction {
    public ChannelThornVolleyAction(int amount) {
        this.amount = amount;
    }
    @Override
    public void update() {
        for(int i = 0; i < amount; i++)
        {
            this.addToBot(new IncreaseMaxOrbAction(1));
            this.addToBot(new ChannelAction(new ThornVolley(), false));
        }
        this.isDone = true;
    }
}
