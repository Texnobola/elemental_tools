/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.elementaltoolsmod.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;

import net.mcreator.elementaltoolsmod.block.entity.KuchaytirishstoliTileEntity;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

public class ElementalToolsModModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ElementalToolsModMod.MODID);
	public static final RegistryObject<BlockEntityType<KuchaytirishstoliTileEntity>> KUCHAYTIRISHSTOLI = REGISTRY.register("kuchaytirishstoli",
			() -> BlockEntityType.Builder.of(KuchaytirishstoliTileEntity::new, ElementalToolsModModBlocks.KUCHAYTIRISHSTOLI.get()).build(null));

	// Start of user code block custom block entities
	// End of user code block custom block entities
	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
}