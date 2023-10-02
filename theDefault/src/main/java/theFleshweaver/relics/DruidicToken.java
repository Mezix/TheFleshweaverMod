package theFleshweaver.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.powers.LethalityPower;
import theFleshweaver.powers.ThaumaturgyPower;
import theFleshweaver.powers.VitalityPower;
import theFleshweaver.util.TextureLoader;

import static theFleshweaver.TheFleshweaverMod.makeRelicOutlinePath;
import static theFleshweaver.TheFleshweaverMod.makeRelicPath;

public class DruidicToken extends CustomRelic {

    // ID, images, text.
    public static final String ID = TheFleshweaverMod.makeID("DruidicToken");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DruidicToken.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DruidicToken.png"));

    public DruidicToken() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw()
    {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThaumaturgyPower(p, p, 4)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LethalityPower(p, p, 4)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VitalityPower(p, p, 4)));
    }
}
