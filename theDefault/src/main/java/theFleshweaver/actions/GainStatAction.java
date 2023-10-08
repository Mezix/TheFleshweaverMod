package theFleshweaver.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theFleshweaver.patches.CurrentLargestStat;
import theFleshweaver.powers.LethalityPower;
import theFleshweaver.powers.ThaumaturgyPower;
import theFleshweaver.powers.VitalityPower;

public class GainStatAction extends AbstractGameAction {
    public CurrentLargestStat.StatType typeToAdd;
    public GainStatAction(AbstractCreature target, int amount, CurrentLargestStat.StatType type) {
        this.amount = amount;
        this.typeToAdd = type;
        this.target = target;
    }

    @Override
    public void update() {
        if (!target.isDying && !target.isDead) {
            if (target instanceof AbstractPlayer)
            {
                CurrentLargestStat.StatType oldLargestStat = CurrentLargestStat.currentLargestStat.get(AbstractDungeon.actionManager);

                //  Gain the Stat

                if(typeToAdd.equals(CurrentLargestStat.StatType.Lethality))
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new LethalityPower(target, target, amount)));
                else if(typeToAdd.equals(CurrentLargestStat.StatType.Vitality))
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new VitalityPower(target, target, amount)));
                else if(typeToAdd.equals(CurrentLargestStat.StatType.Thaumaturgy))
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new ThaumaturgyPower(target, target, amount)));
                else System.out.println("Tried to add stat of type 'None'! Returning");

                //  Calculate Largest Stat

                CurrentLargestStat.StatType newLargestStat;
                int LAmount = 0, VAmount = 0, TAmount = 0;

                if(target.hasPower(LethalityPower.POWER_ID)) LAmount = target.getPower(LethalityPower.POWER_ID).amount;
                if(target.hasPower(VitalityPower.POWER_ID)) VAmount = target.getPower(VitalityPower.POWER_ID).amount;
                if(target.hasPower(ThaumaturgyPower.POWER_ID)) TAmount = target.getPower(ThaumaturgyPower.POWER_ID).amount;

                //  adding the power doesnt work fast enough, so we need to add a 1 to the counter
                if(typeToAdd.equals(CurrentLargestStat.StatType.Lethality)) LAmount +=1;
                if(typeToAdd.equals(CurrentLargestStat.StatType.Vitality)) VAmount +=1;
                if(typeToAdd.equals(CurrentLargestStat.StatType.Thaumaturgy)) TAmount +=1;

                if(LAmount == VAmount && LAmount == TAmount) newLargestStat = CurrentLargestStat.StatType.None;
                else if (LAmount > VAmount && LAmount > TAmount) newLargestStat = CurrentLargestStat.StatType.Lethality;
                else if (VAmount > TAmount) newLargestStat = CurrentLargestStat.StatType.Vitality;
                else newLargestStat = CurrentLargestStat.StatType.Thaumaturgy;

                System.out.println("Largest Stat is: " + newLargestStat.toString());
                if(!oldLargestStat.equals(newLargestStat)) CurrentLargestStat.SetLargestStat(newLargestStat);
            }
        }
        this.isDone = true;
    }
}
