/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.elementaltoolsmod.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

public class ElementalToolsModModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ElementalToolsModMod.MODID);
	public static final RegistryObject<CreativeModeTab> TAB = REGISTRY.register("tab",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.elemental_tools_mod.tab")).icon(() -> new ItemStack(Items.DIAMOND_SWORD)).displayItems((parameters, tabData) -> {
				tabData.accept(ElementalToolsModModBlocks.KUCHAYTIRISHSTOLI.get().asItem());
				tabData.accept(ElementalToolsModModItems.QONLIQILICH_1.get());
			}).withSearchBar().build());
}