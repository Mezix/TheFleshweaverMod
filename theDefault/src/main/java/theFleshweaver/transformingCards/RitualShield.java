package theFleshweaver.transformingCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.cards.AbstractDynamicCard;
import theFleshweaver.characters.TheFleshweaver;
import theFleshweaver.util.UtilityClass;

import static theFleshweaver.TheFleshweaverMod.makeCardPath;

public class RitualShield extends AbstractDynamicCard {

    public static final String ID = TheFleshweaverMod.makeID(RitualShield.class.getSimpleName());
    public static String IMG = makeCardPath("RitualStaffV.png");
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheFleshweaver.Enums.COLOR_TEAL;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;

    //  Transformations
    private static final CardStrings STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int BLOCK = 4;

    public RitualShield() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseBlock = block = BLOCK;
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
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, this.block));

        int VitAmount = UtilityClass.GetVitality(AbstractDungeon.player);
        if(VitAmount > 0)
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
                if (!mo.isDeadOrEscaped()) if(mo.intent.equals(AbstractMonster.Intent.ATTACK))AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(p, VitAmount, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

    }
}
