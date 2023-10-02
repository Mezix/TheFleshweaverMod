package theFleshweaver.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.characters.TheFleshweaver;
import theFleshweaver.powers.LethalityPower;
import theFleshweaver.powers.ThaumaturgyPower;
import theFleshweaver.powers.VitalityPower;

import static theFleshweaver.TheFleshweaverMod.makeCardPath;

public class RitualOrb extends AbstractDynamicCard {

    public static final String ID = TheFleshweaverMod.makeID(RitualOrb.class.getSimpleName());
    public static final String IMG = makeCardPath("RitualOrb.png");
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFleshweaver.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_NUMBER = 1;

    public RitualOrb() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, this.block));
        if(!upgraded)
        {
            int randomInt = (int) Math.floor(Math.random() * 3);
            if (randomInt == 0) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThaumaturgyPower(p, p, magicNumber)));
            else if(randomInt == 1) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LethalityPower(p, p, magicNumber)));
            else AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VitalityPower(p, p, magicNumber)));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThaumaturgyPower(p, p, magicNumber)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LethalityPower(p, p, magicNumber)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VitalityPower(p, p, magicNumber)));
        }
    }
}