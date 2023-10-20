package theFleshweaver.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.actions.ChannelThornVolleyAction;
import theFleshweaver.actions.GainStatAction;
import theFleshweaver.characters.TheFleshweaver;
import theFleshweaver.orbs.ThornVolley;

import java.util.List;

import static theFleshweaver.TheFleshweaverMod.makeCardPath;

public class GatheringPower extends AbstractDynamicCard {

    public static final String ID = TheFleshweaverMod.makeID(GatheringPower.class.getSimpleName());
    public static final String IMG = makeCardPath("RitualStaffL.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFleshweaver.Enums.COLOR_TEAL;

    private static final int COST = 2;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 1;

    public GatheringPower() {
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
        List<AbstractOrb> orbs = p.orbs;
        for (AbstractOrb orb : orbs)
        {
            if(orb.ID.equals(ThornVolley.ORB_ID))
            {
                orb.evokeAmount++;
                orb.passiveAmount *= magicNumber;
            }
        }
    }
}