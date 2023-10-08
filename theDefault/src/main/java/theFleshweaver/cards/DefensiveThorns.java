package theFleshweaver.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.actions.GainStatAction;
import theFleshweaver.characters.TheFleshweaver;
import theFleshweaver.patches.CurrentLargestStat;
import theFleshweaver.powers.MagicalRotPower;

import static theFleshweaver.TheFleshweaverMod.makeCardPath;

public class DefensiveThorns extends AbstractDynamicCard {
    public static final String ID = TheFleshweaverMod.makeID(DefensiveThorns.class.getSimpleName());
    public static final String IMG = makeCardPath("RitualStaffV.png");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFleshweaver.Enums.COLOR_GRAY;
    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_NUMBER = 1;
    private static final int COST = 1;

    public DefensiveThorns() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, this.block));
        AbstractDungeon.actionManager.addToBottom(new GainStatAction(p, 0, magicNumber, 0, true));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
