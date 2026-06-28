package net.mcreator.elementaltoolsmod.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.elementaltoolsmod.item.Qonliqilich3Item;

public class Qonliqilich3ItemModel extends GeoModel<Qonliqilich3Item> {
	@Override
	public ResourceLocation getAnimationResource(Qonliqilich3Item animatable) {
		return new ResourceLocation("elemental_tools_mod", "animations/red_sword_stage1.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(Qonliqilich3Item animatable) {
		return new ResourceLocation("elemental_tools_mod", "geo/red_sword_stage1.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Qonliqilich3Item animatable) {
		return new ResourceLocation("elemental_tools_mod", "textures/item/texture_stage1.png");
	}
}