/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.elementaltoolsmod.init;

import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import net.mcreator.elementaltoolsmod.network.LifestealToggleMessage;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ElementalToolsModModKeyMappings {
	public static final KeyMapping LIFESTEAL_TOGGLE = new KeyMapping("key.elemental_tools_mod.lifesteal_toggle", GLFW.GLFW_KEY_Z, "key.categories.elementaltools") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				ElementalToolsModMod.PACKET_HANDLER.sendToServer(new LifestealToggleMessage(0, 0));
				LifestealToggleMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(LIFESTEAL_TOGGLE);
	}

	@Mod.EventBusSubscriber({Dist.CLIENT})
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onClientTick(TickEvent.ClientTickEvent event) {
			if (Minecraft.getInstance().screen == null) {
				LIFESTEAL_TOGGLE.consumeClick();
			}
		}
	}
}