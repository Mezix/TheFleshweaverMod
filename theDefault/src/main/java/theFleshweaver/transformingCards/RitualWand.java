package theFleshweaver.transformingCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.actions.GainMagicalRotAction;
import theFleshweaver.cards.AbstractDynamicCard;
import theFleshweaver.characters.TheFleshweaver;
import theFleshweaver.powers.MagicalRotPower;

import static theFleshweaver.TheFleshweaverMod.makeCardPath;

public class RitualWand extends AbstractDynamicCard {

    public static final String ID = TheFleshweaverMod.makeID(RitualWand.class.getSimpleName());
    public static String IMG = makeCardPath("RitualStaffT.png");
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheFleshweaver.Enums.COLOR_TEAL;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int SECOND_MAGIC_NUMBER = 4; // Magical Rot gain amount

    public RitualWand() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseSecondMagicNumber = secondMagicNumber = SECOND_MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        int magicalRotAmount = 0;
        if (AbstractDungeon.player.hasPower(MagicalRotPower.POWER_ID))
            magicalRotAmount = AbstractDungeon.player.getPower(MagicalRotPower.POWER_ID).amount;
        if (magicalRotAmount > 0)
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
                if (!mo.isDeadOrEscaped())
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(p, magicalRotAmount, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        this.addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, MagicalRotPower.POWER_ID));
        AbstractDungeon.actionManager.addToBottom(new GainMagicalRotAction(AbstractDungeon.player, secondMagicNumber));
    }
}
