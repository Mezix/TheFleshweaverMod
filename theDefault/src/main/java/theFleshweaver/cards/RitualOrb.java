package theFleshweaver.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.actions.GainStatAction;
import theFleshweaver.characters.TheFleshweaver;
import theFleshweaver.patches.CurrentLargestStat;
import theFleshweaver.powers.LethalityPower;
import theFleshweaver.powers.ThaumaturgyPower;
import theFleshweaver.powers.VitalityPower;

import java.util.ArrayList;
import java.util.Iterator;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theFleshweaver.TheFleshweaverMod.makeCardPath;

public class RitualOrb extends AbstractDynamicCard {

    public static final String ID = TheFleshweaverMod.makeID(RitualOrb.class.getSimpleName());
    public static final String IMG = makeCardPath("RitualOrb.png");
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFleshweaver.Enums.COLOR_GRAY;
    private static final CardStrings STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final int COST = 1;
    private static final int BLOCK = 3;
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
            this.rawDescription = "Gain !B! Block. Gain 1 Stat of your choosing.";
            //this.rawDescription = STRINGS.UPGRADE_DESCRIPTION; //TODO: this crashes for some reason
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, this.block));
        if(!upgraded)
        {
            int randomInt = (int) Math.floor(Math.random() * 3);
            if (randomInt == 0) AbstractDungeon.actionManager.addToBottom(new GainStatAction(p, magicNumber, CurrentLargestStat.StatType.Lethality));
            else if(randomInt == 1) AbstractDungeon.actionManager.addToBottom(new GainStatAction(p, magicNumber, CurrentLargestStat.StatType.Vitality));
            else AbstractDungeon.actionManager.addToBottom(new GainStatAction(p, magicNumber, CurrentLargestStat.StatType.Thaumaturgy));
        }
        else
        {
            //TODO: Stat choosing screen similar to Wish from the Watcher

            ArrayList<AbstractCard> stanceChoices = new ArrayList();
            stanceChoices.add(new LethalityCard());
            stanceChoices.add(new VitalityCard());
            stanceChoices.add(new ThaumaturgyCard());

            this.addToBot(new ChooseOneAction(stanceChoices));
        }
    }
}