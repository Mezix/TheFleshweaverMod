package theFleshweaver.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.characters.TheFleshweaver;
import theFleshweaver.powers.InfectedVeinsPower;

import static theFleshweaver.TheFleshweaverMod.makeCardPath;

public class InfectedVeins extends AbstractDynamicCard {
    public static final String ID = TheFleshweaverMod.makeID(InfectedVeins.class.getSimpleName());
    public static final String IMG = makeCardPath("InfectedVeins.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheFleshweaver.Enums.COLOR_TEAL;
    private static final int MAGIC_NUMBER = 1;
    private static final int COST = 2;

    public InfectedVeins() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InfectedVeinsPower(p, p, this.magicNumber)));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            initializeDescription();
        }
    }
}
