package net.mcreator.elementaltoolsmod.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.elementaltoolsmod.item.Qonliqilich1Item;

public class Qonliqilich1ItemModel extends GeoModel<Qonliqilich1Item> {
	@Override
	public ResourceLocation getAnimationResource(Qonliqilich1Item animatable) {
		return new ResourceLocation("elemental_tools_mod", "animations/red_sword_stage1.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(Qonliqilich1Item animatable) {
		return new ResourceLocation("elemental_tools_mod", "geo/red_sword_stage1.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Qonliqilich1Item animatable) {
		return new ResourceLocation("elemental_tools_mod", "textures/item/texture_stage1.png");
	}
}