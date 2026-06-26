package net.mcreator.elementaltoolsmod.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.elementaltoolsmod.block.display.KuchaytirishstoliDisplayItem;

public class KuchaytirishstoliDisplayModel extends GeoModel<KuchaytirishstoliDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(KuchaytirishstoliDisplayItem animatable) {
		return new ResourceLocation("elemental_tools_mod", "animations/ascending_table.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(KuchaytirishstoliDisplayItem animatable) {
		return new ResourceLocation("elemental_tools_mod", "geo/ascending_table.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(KuchaytirishstoliDisplayItem entity) {
		return new ResourceLocation("elemental_tools_mod", "textures/block/table_texture_final.png");
	}
}