package theFleshweaver.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.actions.ChannelThornVolleyAction;
import theFleshweaver.actions.GainStatAction;
import theFleshweaver.characters.TheFleshweaver;

import static theFleshweaver.TheFleshweaverMod.makeCardPath;

public class PressAdvantage extends AbstractDynamicCard {

    public static final String ID = TheFleshweaverMod.makeID(PressAdvantage.class.getSimpleName());
    public static final String IMG = makeCardPath("RitualStaffL.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFleshweaver.Enums.COLOR_TEAL;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 1;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 1;

    public PressAdvantage() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ChannelThornVolleyAction(magicNumber));
        if(!(m.intent.equals(AbstractMonster.Intent.ATTACK) || m.intent.equals(AbstractMonster.Intent.ATTACK_BUFF) || m.intent.equals(AbstractMonster.Intent.ATTACK_DEFEND) || m.intent.equals(AbstractMonster.Intent.ATTACK_DEBUFF)))
            AbstractDungeon.actionManager.addToBottom(new GainStatAction(p, magicNumber, 0, 0, true));
    }
}