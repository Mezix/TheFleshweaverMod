package theFleshweaver.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theFleshweaver.patches.CurrentLargestStat;
import theFleshweaver.powers.*;

public class GainMagicalRotAction extends AbstractGameAction {
    public GainMagicalRotAction(AbstractCreature target, int amount) {
        this.amount = amount;
        this.target = target;
    }
    @Override
    public void update() {
        if (!target.isDying && !target.isDead) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new MagicalRotPower(target, target, amount)));

            if (target instanceof AbstractPlayer)
                if(target.hasPower(InfectedVeinsPower.POWER_ID))
                    AbstractDungeon.actionManager.addToBottom(new DrawCardAction(target, target.getPower(InfectedVeinsPower.POWER_ID).amount));
        }
        this.isDone = true;
    }
}
