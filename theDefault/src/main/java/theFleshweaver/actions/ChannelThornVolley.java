package theFleshweaver.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theFleshweaver.orbs.ThornVolley;
import theFleshweaver.powers.InfectedVeinsPower;
import theFleshweaver.powers.MagicalRotPower;

public class ChannelThornVolley extends AbstractGameAction {
    public ChannelThornVolley(int amount) {
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
