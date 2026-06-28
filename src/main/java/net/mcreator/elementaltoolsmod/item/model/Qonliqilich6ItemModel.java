package net.mcreator.elementaltoolsmod.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.elementaltoolsmod.item.Qonliqilich6Item;

public class Qonliqilich6ItemModel extends GeoModel<Qonliqilich6Item> {
	@Override
	public ResourceLocation getAnimationResource(Qonliqilich6Item animatable) {
		return new ResourceLocation("elemental_tools_mod", "animations/red_sword_stage4.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(Qonliqilich6Item animatable) {
		return new ResourceLocation("elemental_tools_mod", "geo/red_sword_stage4.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Qonliqilich6Item animatable) {
		return new ResourceLocation("elemental_tools_mod", "textures/item/stage4_texture.png");
	}
}