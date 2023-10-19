package theFleshweaver.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theFleshweaver.powers.InfectedVeinsPower;
import theFleshweaver.powers.MagicalRotPower;
import theFleshweaver.powers.ThornweavingPower;
import theFleshweaver.relics.DruidicToken;

public class WeaveAction extends AbstractGameAction {
    public WeaveAction() {
    }
    @Override
    public void update() {
        if(AbstractDungeon.player.hasRelic(DruidicToken.ID))
        {
            DruidicToken token = (DruidicToken) AbstractDungeon.player.getRelic(DruidicToken.ID);
            int damage = token.WeaveDamage;
            this.addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS), AttackEffect.SLASH_HORIZONTAL));
        }
        if(AbstractDungeon.player.hasPower(ThornweavingPower.POWER_ID))
            AbstractDungeon.actionManager.addToBottom(new ChannelThornVolleyAction(1));
        this.isDone = true;
    }
}
