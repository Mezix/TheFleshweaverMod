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
import theFleshweaver.util.UtilityClass;

public class GainStatAction extends AbstractGameAction {
    int LethalityAmount;
    int VitalityAmount;
    int ThaumaturgyAmount;
    boolean shouldWeave;
    public GainStatAction(AbstractCreature target, int lethality, int vitality, int thaumaturgy, boolean triggerWeave) {
        this.LethalityAmount = lethality;
        this.VitalityAmount = vitality;
        this.ThaumaturgyAmount = thaumaturgy;
        this.target = target;
        this.shouldWeave = triggerWeave;
    }

    @Override
    public void update() {
        if (!target.isDying && !target.isDead) {
            if (target instanceof AbstractPlayer)
            {
                if(LethalityAmount == 0 && VitalityAmount == 0 && ThaumaturgyAmount == 0)
                {
                    this.isDone = true;
                    return;
                }

                CurrentLargestStat.StatType oldLargestStat = CurrentLargestStat.currentLargestStat.get(AbstractDungeon.actionManager);

                System.out.println("OLD: Largest Stat is: " + oldLargestStat.toString());
                //  Gain the Stats

                if(LethalityAmount != 0) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new LethalityPower(target, target, LethalityAmount)));
                if(VitalityAmount != 0) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new VitalityPower(target, target, VitalityAmount)));
                if(ThaumaturgyAmount != 0) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new ThaumaturgyPower(target, target, ThaumaturgyAmount)));

                //  Calculate Largest Stat

                CurrentLargestStat.StatType newLargestStat;

                int LAmount = UtilityClass.GetLethality(target);
                int VAmount = UtilityClass.GetVitality(target);
                int TAmount = UtilityClass.GetThaumaturgy(target);

                //  adding the power doesnt work fast enough, so we need to add the new values to the temp values
                LAmount += LethalityAmount;
                VAmount += VitalityAmount;
                TAmount += ThaumaturgyAmount;

                if(LAmount == VAmount && LAmount == TAmount) newLargestStat = CurrentLargestStat.StatType.None;
                else if (LAmount > VAmount && LAmount > TAmount) newLargestStat = CurrentLargestStat.StatType.Lethality;
                else if (VAmount > TAmount) newLargestStat = CurrentLargestStat.StatType.Vitality;
                else newLargestStat = CurrentLargestStat.StatType.Thaumaturgy;
                System.out.println("L: " + LAmount + " - V: " + VAmount + " - T: " + TAmount);
                System.out.println("NEW: Largest Stat is: " + newLargestStat.toString());
                if(!oldLargestStat.equals(newLargestStat)) {
                    CurrentLargestStat.SetLargestStat(newLargestStat);
                    if(shouldWeave) AbstractDungeon.actionManager.addToBottom(new WeaveAction());
                }
            }
        }
        this.isDone = true;
    }
}
