package net.mcreator.elementaltoolsmod.block.listener;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.mcreator.elementaltoolsmod.init.ElementalToolsModModBlockEntities;
import net.mcreator.elementaltoolsmod.block.renderer.KuchaytirishstoliTileRenderer;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

@Mod.EventBusSubscriber(modid = ElementalToolsModMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientListener {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ElementalToolsModModBlockEntities.KUCHAYTIRISHSTOLI.get(), context -> new KuchaytirishstoliTileRenderer());
	}
}