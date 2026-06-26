package net.mcreator.elementaltoolsmod.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.elementaltoolsmod.block.entity.KuchaytirishstoliTileEntity;

public class KuchaytirishstoliBlockModel extends GeoModel<KuchaytirishstoliTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(KuchaytirishstoliTileEntity animatable) {
		return new ResourceLocation("elemental_tools_mod", "animations/ascending_table.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(KuchaytirishstoliTileEntity animatable) {
		return new ResourceLocation("elemental_tools_mod", "geo/ascending_table.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(KuchaytirishstoliTileEntity animatable) {
		return new ResourceLocation("elemental_tools_mod", "textures/block/table_texture_final.png");
	}
}