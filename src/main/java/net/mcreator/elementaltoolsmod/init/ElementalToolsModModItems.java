/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.elementaltoolsmod.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mcreator.elementaltoolsmod.item.Qonliqilich7Item;
import net.mcreator.elementaltoolsmod.item.Qonliqilich6Item;
import net.mcreator.elementaltoolsmod.item.Qonliqilich5Item;
import net.mcreator.elementaltoolsmod.item.Qonliqilich4Item;
import net.mcreator.elementaltoolsmod.item.Qonliqilich3Item;
import net.mcreator.elementaltoolsmod.item.Qonliqilich2Item;
import net.mcreator.elementaltoolsmod.item.Qonliqilich1Item;
import net.mcreator.elementaltoolsmod.item.QonitemItem;
import net.mcreator.elementaltoolsmod.item.BloodcanItem;
import net.mcreator.elementaltoolsmod.block.display.KuchaytirishstoliDisplayItem;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

public class ElementalToolsModModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ElementalToolsModMod.MODID);
	public static final RegistryObject<Item> KUCHAYTIRISHSTOLI = REGISTRY.register(ElementalToolsModModBlocks.KUCHAYTIRISHSTOLI.getId().getPath(),
			() -> new KuchaytirishstoliDisplayItem(ElementalToolsModModBlocks.KUCHAYTIRISHSTOLI.get(), new Item.Properties()));
	public static final RegistryObject<Item> QONLIQILICH_1 = REGISTRY.register("qonliqilich_1", () -> new Qonliqilich1Item());
	public static final RegistryObject<Item> QONITEM = REGISTRY.register("qonitem", () -> new QonitemItem());
	public static final RegistryObject<Item> BLOODCAN = REGISTRY.register("bloodcan", () -> new BloodcanItem());
	public static final RegistryObject<Item> BLOODBLOCK = block(ElementalToolsModModBlocks.BLOODBLOCK);
	public static final RegistryObject<Item> QONLIQILICH_2 = REGISTRY.register("qonliqilich_2", () -> new Qonliqilich2Item());
	public static final RegistryObject<Item> QONLIQILICH_3 = REGISTRY.register("qonliqilich_3", () -> new Qonliqilich3Item());
	public static final RegistryObject<Item> QONLIQILICH_4 = REGISTRY.register("qonliqilich_4", () -> new Qonliqilich4Item());
	public static final RegistryObject<Item> QONLIQILICH_5 = REGISTRY.register("qonliqilich_5", () -> new Qonliqilich5Item());
	public static final RegistryObject<Item> QONLIQILICH_6 = REGISTRY.register("qonliqilich_6", () -> new Qonliqilich6Item());
	public static final RegistryObject<Item> QONLIQILICH_7 = REGISTRY.register("qonliqilich_7", () -> new Qonliqilich7Item());

	// Start of user code block custom items
	// End of user code block custom items
	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}