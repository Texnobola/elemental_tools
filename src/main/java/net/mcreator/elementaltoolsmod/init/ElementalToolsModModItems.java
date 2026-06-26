/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.elementaltoolsmod.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;

import net.mcreator.elementaltoolsmod.block.display.KuchaytirishstoliDisplayItem;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

public class ElementalToolsModModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ElementalToolsModMod.MODID);
	public static final RegistryObject<Item> KUCHAYTIRISHSTOLI = REGISTRY.register(ElementalToolsModModBlocks.KUCHAYTIRISHSTOLI.getId().getPath(),
			() -> new KuchaytirishstoliDisplayItem(ElementalToolsModModBlocks.KUCHAYTIRISHSTOLI.get(), new Item.Properties()));
	// Start of user code block custom items
	// End of user code block custom items
}