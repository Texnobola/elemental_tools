package net.mcreator.elementaltoolsmod.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.mcreator.elementaltoolsmod.block.model.KuchaytirishstoliBlockModel;
import net.mcreator.elementaltoolsmod.block.entity.KuchaytirishstoliTileEntity;

public class KuchaytirishstoliTileRenderer extends GeoBlockRenderer<KuchaytirishstoliTileEntity> {
	public KuchaytirishstoliTileRenderer() {
		super(new KuchaytirishstoliBlockModel());
	}

	@Override
	public RenderType getRenderType(KuchaytirishstoliTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}