package theFleshweaver.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.util.TextureLoader;

import static theFleshweaver.TheFleshweaverMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class HaloOfThornsPower extends AbstractPower{
    public AbstractCreature source;

    public static final String POWER_ID = TheFleshweaverMod.makeID("HaloOfThornsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture texture128 = TextureLoader.getTexture(makePowerPath("InfectedVeins_128.png"));
    private static final Texture texture48 = TextureLoader.getTexture(makePowerPath("InfectedVeins_48.png"));

    public HaloOfThornsPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(texture128, 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(texture48, 0, 0, 48, 48);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
