package theFleshweaver.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.characters.TheFleshweaver;

import java.util.ArrayList;

import static theFleshweaver.TheFleshweaverMod.makeCardPath;

public class RitualOrb extends AbstractDynamicCard {

    public static final String ID = TheFleshweaverMod.makeID(RitualOrb.class.getSimpleName());
    public static final String IMG = makeCardPath("RitualOrb.png");
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFleshweaver.Enums.COLOR_TEAL;

    private static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int MAGIC_NUMBER = 1;

    public RitualOrb() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            isInnate = true;
            this.rawDescription = "Innate. Gain !B! Block. Gain 1 Stat of your choosing. Exhaust.";
            //this.rawDescription = STRINGS.UPGRADE_DESCRIPTION; //TODO: this crashes for some reason
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, this.block));

        ArrayList<AbstractCard> statChoices = new ArrayList();
        statChoices.add(new LethalityCard());
        statChoices.add(new VitalityCard());
        statChoices.add(new ThaumaturgyCard());

        this.addToBot(new ChooseOneAction(statChoices));
        /*
        if(!upgraded)
        {
            int randomInt = (int) Math.floor(Math.random() * 3);
            if (randomInt == 0) AbstractDungeon.actionManager.addToBottom(new GainStatAction(p, magicNumber, CurrentLargestStat.StatType.Lethality));
            else if(randomInt == 1) AbstractDungeon.actionManager.addToBottom(new GainStatAction(p, magicNumber, CurrentLargestStat.StatType.Vitality));
            else AbstractDungeon.actionManager.addToBottom(new GainStatAction(p, magicNumber, CurrentLargestStat.StatType.Thaumaturgy));
        }
        else
        {
        }*/
    }
}