package theFleshweaver.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.actions.ChannelThornVolleyAction;
import theFleshweaver.actions.GainStatAction;
import theFleshweaver.characters.TheFleshweaver;
import theFleshweaver.powers.InfectedVeinsPower;
import theFleshweaver.powers.LoseLethalityPower;

import static theFleshweaver.TheFleshweaverMod.makeCardPath;

public class ThornShot extends AbstractDynamicCard {

    public static final String ID = TheFleshweaverMod.makeID(ThornShot.class.getSimpleName());
    public static final String IMG = makeCardPath("RitualStaffL.png");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheFleshweaver.Enums.COLOR_TEAL;

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int MAGIC_NUMBER = 1;
    private static final int SECOND_MAGIC_NUMBER = 1;

    public ThornShot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        baseSecondMagicNumber = secondMagicNumber = SECOND_MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new GainStatAction(p, magicNumber, 0, 0, true));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseLethalityPower(p, p, magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new ChannelThornVolleyAction(secondMagicNumber));
    }
}
