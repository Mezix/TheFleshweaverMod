package theFleshweaver.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.characters.TheFleshweaver;
import theFleshweaver.util.UtilityClass;

import java.util.ArrayList;

import static theFleshweaver.TheFleshweaverMod.makeCardPath;

public class IdleFingers extends AbstractDynamicCard {

    public static final String ID = TheFleshweaverMod.makeID(IdleFingers.class.getSimpleName());
    public static final String IMG = makeCardPath("RitualStaffL.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFleshweaver.Enums.COLOR_TEAL;

    private static final int COST = 1;

    public IdleFingers() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int orbCount = AbstractDungeon.player.filledOrbCount();
        if (orbCount > 0) for(int i = 0; i < orbCount; i++) AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
    }
}