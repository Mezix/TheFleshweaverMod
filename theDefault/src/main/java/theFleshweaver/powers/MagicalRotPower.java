package theFleshweaver.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theFleshweaver.DefaultMod;
import theFleshweaver.util.TextureLoader;

import static theFleshweaver.DefaultMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class MagicalRotPower extends AbstractPower{
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("MagicalRotPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    int magicalRotDamage = 0;

    public MagicalRotPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        updateMagicalRotDamageAmount();
    }

    private void updateMagicalRotDamageAmount() {
        int tmpAmount = this.amount;
        int tmpMagicalRotDamage = 0;
        while(tmpAmount > 0)
        {
            tmpAmount -= 10;
            if(tmpAmount >= 0) tmpMagicalRotDamage += 1;
        }
        magicalRotDamage = tmpMagicalRotDamage;
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if(magicalRotDamage > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(owner, new DamageInfo(owner, magicalRotDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.POISON, false, false));
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo != null && !mo.isDeadOrEscaped()) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(owner, magicalRotDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.POISON, false, false));
                }
            }
        }
    }

    @Override
    public void updateDescription() {
        updateMagicalRotDamageAmount();
        description = DESCRIPTIONS[0] + magicalRotDamage + DESCRIPTIONS[1];
    }
}
