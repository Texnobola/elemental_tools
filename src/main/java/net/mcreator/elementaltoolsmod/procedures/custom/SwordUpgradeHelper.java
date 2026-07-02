package net.mcreator.elementaltoolsmod.procedures.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.mcreator.elementaltoolsmod.init.ElementalToolsModModItems;

public class SwordUpgradeHelper {

    public static class UpgradeInfo {
        public final Item nextStage;
        public final double requiredXp;

        public UpgradeInfo(Item nextStage, double requiredXp) {
            this.nextStage = nextStage;
            this.requiredXp = requiredXp;
        }
    }

    public static UpgradeInfo getUpgradeInfo(ItemStack sword) {
        if (sword.isEmpty()) return null;
        
        Item item = sword.getItem();
        if (item == ElementalToolsModModItems.QONLIQILICH_1.get()) return new UpgradeInfo(ElementalToolsModModItems.QONLIQILICH_2.get(), 50);
        if (item == ElementalToolsModModItems.QONLIQILICH_2.get()) return new UpgradeInfo(ElementalToolsModModItems.QONLIQILICH_3.get(), 100);
        if (item == ElementalToolsModModItems.QONLIQILICH_3.get()) return new UpgradeInfo(ElementalToolsModModItems.QONLIQILICH_4.get(), 200);
        if (item == ElementalToolsModModItems.QONLIQILICH_4.get()) return new UpgradeInfo(ElementalToolsModModItems.QONLIQILICH_5.get(), 500);
        if (item == ElementalToolsModModItems.QONLIQILICH_5.get()) return new UpgradeInfo(ElementalToolsModModItems.QONLIQILICH_6.get(), 700);
        if (item == ElementalToolsModModItems.QONLIQILICH_6.get()) return new UpgradeInfo(ElementalToolsModModItems.QONLIQILICH_7.get(), 1000);
        
        return null;
    }
}
