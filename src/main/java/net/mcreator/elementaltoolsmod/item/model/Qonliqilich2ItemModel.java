package net.mcreator.elementaltoolsmod.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.elementaltoolsmod.item.Qonliqilich2Item;

public class Qonliqilich2ItemModel extends GeoModel<Qonliqilich2Item> {
	@Override
	public ResourceLocation getAnimationResource(Qonliqilich2Item animatable) {
		return new ResourceLocation("elemental_tools_mod", "animations/red_sword_stage1.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(Qonliqilich2Item animatable) {
		return new ResourceLocation("elemental_tools_mod", "geo/red_sword_stage1.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Qonliqilich2Item animatable) {
		return new ResourceLocation("elemental_tools_mod", "textures/item/texture_stage1.png");
	}
}