package theFleshweaver.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.actions.GainStatAction;
import theFleshweaver.characters.TheFleshweaver;
import theFleshweaver.util.UtilityClass;

import static theFleshweaver.TheFleshweaverMod.makeCardPath;

public class BullheadedCharge extends AbstractDynamicCard {

    public static final String ID = TheFleshweaverMod.makeID(BullheadedCharge.class.getSimpleName());
    public static final String IMG = makeCardPath("RitualStaffL.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheFleshweaver.Enums.COLOR_TEAL;

    private static final int COST = 2;
    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int MAGIC_NUMBER = 6;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 2;

    public BullheadedCharge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
            upgradeDamage(UPGRADE_PLUS_DMG);
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        int Lethality = UtilityClass.GetLethality(p);
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(m, p, Lethality, AbstractGameAction.AttackEffect.NONE));
        if(Lethality >= magicNumber)
            AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, Lethality, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
}
