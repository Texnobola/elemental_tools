package net.mcreator.elementaltoolsmod.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.mcreator.elementaltoolsmod.block.model.KuchaytirishstoliDisplayModel;
import net.mcreator.elementaltoolsmod.block.display.KuchaytirishstoliDisplayItem;

public class KuchaytirishstoliDisplayItemRenderer extends GeoItemRenderer<KuchaytirishstoliDisplayItem> {
	public KuchaytirishstoliDisplayItemRenderer() {
		super(new KuchaytirishstoliDisplayModel());
	}

	@Override
	public RenderType getRenderType(KuchaytirishstoliDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}