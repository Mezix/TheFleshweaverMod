package theFleshweaver.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(
        clz = GameActionManager.class,
        method = SpirePatch.CLASS
)
public class CurrentLargestStat {
    public static SpireField<StatType> currentLargestStat = new SpireField<>(() -> StatType.None);
    public enum StatType
    {
        None,
        Lethality,
        Vitality,
        Thaumaturgy
    }

    public static void SetLargestStat(StatType stat)
    {
        currentLargestStat.set(AbstractDungeon.actionManager, stat);
    }
}
