package theFleshweaver.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.actions.GainStatAction;
import theFleshweaver.util.TextureLoader;

import static theFleshweaver.TheFleshweaverMod.makePowerPath;

public class LoseLethalityPower extends AbstractPower{
    public AbstractCreature source;

    public static final String POWER_ID = TheFleshweaverMod.makeID("LethalityPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture texture128 = TextureLoader.getTexture(makePowerPath("Lethality_128.png"));
    private static final Texture texture48 = TextureLoader.getTexture(makePowerPath("Lethality_48.png"));

    public LoseLethalityPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new GainStatAction(AbstractDungeon.player, amount,0, 0, true));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Flex"));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
