package theFleshweaver.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.actions.GainMagicalRotAction;
import theFleshweaver.actions.GainStatAction;
import theFleshweaver.characters.TheFleshweaver;
import theFleshweaver.patches.CurrentLargestStat;
import theFleshweaver.powers.MagicalRotPower;
import theFleshweaver.powers.ThaumaturgyPower;

import static theFleshweaver.TheFleshweaverMod.makeCardPath;

public class Contemplate extends AbstractDynamicCard {
    public static final String ID = TheFleshweaverMod.makeID(Contemplate.class.getSimpleName());
    public static final String IMG = makeCardPath("Contemplate.png");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFleshweaver.Enums.COLOR_GRAY;
    private static final int MAGIC_NUMBER = 1; // Thaumaturgy
    private static final int SECOND_MAGIC_NUMBER = 5; // Magical Rot
    private static final int UPGRADE_PLUS_SECOND_MAGIC_NUMBER = 5;
    private static final int COST = 1;

    public Contemplate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainStatAction(AbstractDungeon.player, 0,0, magicNumber, true));
        AbstractDungeon.actionManager.addToBottom(new GainMagicalRotAction(AbstractDungeon.player, defaultSecondMagicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECOND_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}
