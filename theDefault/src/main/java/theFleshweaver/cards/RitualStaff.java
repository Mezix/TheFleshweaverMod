package theFleshweaver.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.actions.GainMagicalRotAction;
import theFleshweaver.characters.TheFleshweaver;
import theFleshweaver.patches.CurrentLargestStat;
import theFleshweaver.powers.MagicalRotPower;
import theFleshweaver.transformingCards.RitualShield;
import theFleshweaver.transformingCards.RitualSword;
import theFleshweaver.transformingCards.RitualWand;
import theFleshweaver.util.UtilityClass;

import static theFleshweaver.TheFleshweaverMod.makeCardPath;

public class RitualStaff extends AbstractDynamicCard {

    public static final String ID = TheFleshweaverMod.makeID(RitualStaff.class.getSimpleName());
    public static String IMG = makeCardPath("RitualStaff.png");
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheFleshweaver.Enums.COLOR_TEAL;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;

    //  Transformations
    private static final CardStrings STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_DEFAULT = makeCardPath("RitualStaff.png");

    //  Lethality
    private static final CardStrings LETHALITY_STRINGS = CardCrawlGame.languagePack.getCardStrings(RitualSword.ID);
    public static final String IMG_LETHALITY = makeCardPath("RitualStaffL.png");
    private static final int MAGIC_NUMBER = 1;

    //  Vitality
    private static final CardStrings VITALITY_STRINGS = CardCrawlGame.languagePack.getCardStrings(RitualShield.ID);
    public static final String IMG_VITALITY = makeCardPath("RitualStaffV.png");
    private static final int BLOCK = 4;

    //  Thaumaturgy
    private static final CardStrings THAUMATURGY_STRINGS = CardCrawlGame.languagePack.getCardStrings(RitualWand.ID);
    public static final String IMG_THAUMATURGY = makeCardPath("RitualStaffT.png");
    private static final int SECOND_MAGIC_NUMBER = 4; // Magical Rot gain amount

    public RitualStaff() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        baseSecondMagicNumber = secondMagicNumber = SECOND_MAGIC_NUMBER;

        MultiCardPreview.add(this, new RitualSword(), new RitualShield(), new RitualWand());
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }

    }
    public void applyPowers() {
        super.applyPowers();
        CurrentLargestStat.StatType stat = CurrentLargestStat.currentLargestStat.get(AbstractDungeon.actionManager);
        if(stat.equals(CurrentLargestStat.StatType.Lethality))
        {
            IMG = IMG_LETHALITY;
            name = "Ritual Sword";
            rawDescription = LETHALITY_STRINGS.DESCRIPTION;
        }
        else if(stat.equals(CurrentLargestStat.StatType.Vitality))
        {
            IMG = IMG_VITALITY;
            name = "Ritual Shield";
            rawDescription = VITALITY_STRINGS.DESCRIPTION;
        }
        else if(stat.equals(CurrentLargestStat.StatType.Thaumaturgy))
        {
            IMG = IMG_THAUMATURGY;
            name = "Ritual Wand";
            rawDescription = THAUMATURGY_STRINGS.DESCRIPTION;
        }
        else
        {
            IMG = IMG_DEFAULT;
            name = "Ritual Staff";
            rawDescription = STRINGS.DESCRIPTION;
        }
        textureImg = IMG;
        if (IMG != null) loadCardImage(IMG);
        if(upgraded) name += "+";

        update();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        CurrentLargestStat.StatType stat = CurrentLargestStat.currentLargestStat.get(AbstractDungeon.actionManager);
        if(stat.equals(CurrentLargestStat.StatType.Lethality))
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
        }
        else if(stat.equals(CurrentLargestStat.StatType.Vitality))
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, this.block));

            int VitAmount = UtilityClass.GetVitality(AbstractDungeon.player);
            if(VitAmount > 0)
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
                if (!mo.isDeadOrEscaped()) if(mo.intent.equals(AbstractMonster.Intent.ATTACK))AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(p, VitAmount, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        else if(stat.equals(CurrentLargestStat.StatType.Thaumaturgy))
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

            int magicalRotAmount = 0;
            if(AbstractDungeon.player.hasPower(MagicalRotPower.POWER_ID)) magicalRotAmount = AbstractDungeon.player.getPower(MagicalRotPower.POWER_ID).amount;
                if(magicalRotAmount > 0)
                    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
                        if (!mo.isDeadOrEscaped()) AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(p, magicalRotAmount, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

            this.addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, MagicalRotPower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new GainMagicalRotAction(AbstractDungeon.player, secondMagicNumber));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }
}
