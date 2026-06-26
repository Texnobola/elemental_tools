/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.elementaltoolsmod.init;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

import net.mcreator.elementaltoolsmod.client.gui.TableguiScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ElementalToolsModModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(ElementalToolsModModMenus.TABLEGUI.get(), TableguiScreen::new);
		});
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}