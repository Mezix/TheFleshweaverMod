package theFleshweaver.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.characters.TheFleshweaver;
import theFleshweaver.patches.CurrentLargestStat;
import theFleshweaver.powers.MagicalRotPower;
import theFleshweaver.powers.VitalityPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theFleshweaver.TheFleshweaverMod.makeCardPath;

public class RitualStaff extends AbstractDynamicCard {

    public static final String ID = TheFleshweaverMod.makeID(RitualStaff.class.getSimpleName());
    public static String IMG = makeCardPath("RitualStaff.png");
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheFleshweaver.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;

    //  Transformations
    private static final CardStrings STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_DEFAULT = makeCardPath("RitualStaff.png");

    //  Lethality
    public static final String IMG_LETHALITY = makeCardPath("RitualStaffL.png");
    private static final int MAGIC_NUMBER = 1;

    //  Vitality
    public static final String IMG_VITALITY = makeCardPath("RitualStaffV.png");
    private static final int BLOCK = 4;

    //  Thaumaturgy
    public static final String IMG_THAUMATURGY = makeCardPath("RitualStaffT.png");
    private static final int SECOND_MAGIC_NUMBER = 5;

    public RitualStaff() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;

        //this.cardsToPreview = new VitalityCard().makeCopy();
        MultiCardPreview.add(this, new LethalityCard(), new VitalityCard(), new ThaumaturgyCard());
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
            rawDescription = STRINGS.EXTENDED_DESCRIPTION[0] + STRINGS.EXTENDED_DESCRIPTION[1];
        }
        else if(stat.equals(CurrentLargestStat.StatType.Vitality))
        {
            IMG = IMG_VITALITY;
            rawDescription = STRINGS.EXTENDED_DESCRIPTION[0] + STRINGS.EXTENDED_DESCRIPTION[2];
        }
        else if(stat.equals(CurrentLargestStat.StatType.Thaumaturgy))
        {
            IMG = IMG_THAUMATURGY;
            rawDescription = STRINGS.EXTENDED_DESCRIPTION[0] + STRINGS.EXTENDED_DESCRIPTION[3];
        }
        else
        {
            IMG = IMG_DEFAULT;
            rawDescription = STRINGS.DESCRIPTION;
        }

        textureImg = IMG;
        if (IMG != null) {
            loadCardImage(IMG);
        }
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        CurrentLargestStat.StatType stat = CurrentLargestStat.currentLargestStat.get(AbstractDungeon.actionManager);
        if(stat.equals(CurrentLargestStat.StatType.Lethality))
        {
            this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        else if(stat.equals(CurrentLargestStat.StatType.Vitality))
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, this.block));

            int VitAmount = 0;
            if(AbstractDungeon.player.hasPower(VitalityPower.POWER_ID)) VitAmount = AbstractDungeon.player.getPower(VitalityPower.POWER_ID).amount;
            System.out.println(VitAmount);
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
                if (!mo.isDeadOrEscaped()) if(mo.intent.equals(AbstractMonster.Intent.ATTACK))AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(p, VitAmount, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        else if(stat.equals(CurrentLargestStat.StatType.Thaumaturgy))
        {
            int magicalRotAmount = 0;
            if(AbstractDungeon.player.hasPower(MagicalRotPower.POWER_ID)) magicalRotAmount = AbstractDungeon.player.getPower(MagicalRotPower.POWER_ID).amount;

            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
                if (!mo.isDeadOrEscaped()) AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(p, magicalRotAmount, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MagicalRotPower(p, p, defaultSecondMagicNumber)));
        }
    }
}
