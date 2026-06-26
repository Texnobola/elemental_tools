/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.elementaltoolsmod.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import net.mcreator.elementaltoolsmod.block.KuchaytirishstoliBlock;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

public class ElementalToolsModModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, ElementalToolsModMod.MODID);
	public static final RegistryObject<Block> KUCHAYTIRISHSTOLI = REGISTRY.register("kuchaytirishstoli", KuchaytirishstoliBlock::new);
	// Start of user code block custom blocks
	// End of user code block custom blocks
}