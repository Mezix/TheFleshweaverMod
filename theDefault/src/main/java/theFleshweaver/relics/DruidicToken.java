package theFleshweaver.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import theFleshweaver.TheFleshweaverMod;
import theFleshweaver.actions.GainStatAction;
import theFleshweaver.patches.CurrentLargestStat;
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

    public int WeaveDamage = 3;

    public DruidicToken() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        tips.add(new PowerTip(
                BaseMod.getKeywordTitle(DESCRIPTIONS[1].toLowerCase()),
                BaseMod.getKeywordDescription(DESCRIPTIONS[1].toLowerCase())
        ));
        tips.add(new PowerTip(
                BaseMod.getKeywordTitle(DESCRIPTIONS[2].toLowerCase()),
                BaseMod.getKeywordDescription(DESCRIPTIONS[2].toLowerCase())
        ));
        tips.add(new PowerTip(
                BaseMod.getKeywordTitle(DESCRIPTIONS[3].toLowerCase()),
                BaseMod.getKeywordDescription(DESCRIPTIONS[3].toLowerCase())
        ));
        tips.add(new PowerTip(
                BaseMod.getKeywordTitle(DESCRIPTIONS[4].toLowerCase()),
                BaseMod.getKeywordDescription(DESCRIPTIONS[4].toLowerCase())
        ));
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw()
    {
        AbstractDungeon.actionManager.addToBottom(new GainStatAction(AbstractDungeon.player, 4, 4,4, false));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
