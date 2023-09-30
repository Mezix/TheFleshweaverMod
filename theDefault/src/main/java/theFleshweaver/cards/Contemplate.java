package theFleshweaver.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import theFleshweaver.DefaultMod;
import theFleshweaver.characters.TheDefault;
import theFleshweaver.powers.MagicalRotPower;

import static theFleshweaver.DefaultMod.makeCardPath;

public class Contemplate extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(Contemplate.class.getSimpleName());
    public static final String IMG = makeCardPath("Contemplate.png");
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
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
        //TODO: Gain Thaumaturgy
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MagicalRotPower(p, p, defaultSecondMagicNumber)));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECOND_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}
