package theFleshweaver.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Neutralize;
import com.megacrit.cardcrawl.cards.purple.Eruption;
import com.megacrit.cardcrawl.cards.purple.Vigilance;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.characters.Watcher;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theFleshweaver.powers.LethalityPower;
import theFleshweaver.powers.ThaumaturgyPower;
import theFleshweaver.powers.VitalityPower;

import java.util.ArrayList;
import java.util.Iterator;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public final class UtilityClass {

    public static boolean isInCombat() {
        return CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }
    public static int GetLethality(AbstractCreature target)
    {
        if(target.hasPower(LethalityPower.POWER_ID)) return target.getPower(LethalityPower.POWER_ID).amount;
        return 0;
    }
    public static int GetVitality(AbstractCreature target)
    {
        if(target.hasPower(VitalityPower.POWER_ID)) return target.getPower(VitalityPower.POWER_ID).amount;
        return 0;
    }
    public static int GetThaumaturgy(AbstractCreature target)
    {
        if(target.hasPower(ThaumaturgyPower.POWER_ID)) return target.getPower(ThaumaturgyPower.POWER_ID).amount;
        return 0;
    }
    public static AbstractCard returnTrulyRandomTwoCostCardInCombat() {
        ArrayList<AbstractCard> list = new ArrayList();
        Iterator var2 = srcCommonCardPool.group.iterator();

        AbstractCard c;
        while (var2.hasNext()) {
            c = (AbstractCard) var2.next();
            if (c.cost == 2 && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        var2 = srcUncommonCardPool.group.iterator();

        while (var2.hasNext()) {
            c = (AbstractCard) var2.next();
            if (c.cost == 2 && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        var2 = srcRareCardPool.group.iterator();

        while (var2.hasNext()) {
            c = (AbstractCard) var2.next();
            if (c.cost == 2 && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }
        return (AbstractCard) list.get(cardRandomRng.random(list.size() - 1));
    }

    public static AbstractCard returnTrulyRandomCommonAttackCardInCombat() {

        ArrayList<AbstractCard> list = new ArrayList();
        Iterator var2 = srcCommonCardPool.group.iterator();

        AbstractCard c;
        while (var2.hasNext()) {
            c = (AbstractCard) var2.next();
            if (c.type == AbstractCard.CardType.ATTACK && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }
        return (AbstractCard) list.get(cardRandomRng.random(list.size() - 1));

    }
}
