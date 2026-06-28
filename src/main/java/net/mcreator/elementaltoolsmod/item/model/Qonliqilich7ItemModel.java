package net.mcreator.elementaltoolsmod.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.elementaltoolsmod.item.Qonliqilich7Item;

public class Qonliqilich7ItemModel extends GeoModel<Qonliqilich7Item> {
	@Override
	public ResourceLocation getAnimationResource(Qonliqilich7Item animatable) {
		return new ResourceLocation("elemental_tools_mod", "animations/red_sword_stage7.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(Qonliqilich7Item animatable) {
		return new ResourceLocation("elemental_tools_mod", "geo/red_sword_stage7.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Qonliqilich7Item animatable) {
		return new ResourceLocation("elemental_tools_mod", "textures/item/stage7_texture.png");
	}
}