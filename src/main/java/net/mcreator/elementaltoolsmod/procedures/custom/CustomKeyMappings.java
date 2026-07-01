package net.mcreator.elementaltoolsmod.procedures.custom;

import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import net.mcreator.elementaltoolsmod.network.DashMessage;
import net.mcreator.elementaltoolsmod.network.CritWaveMessage;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class CustomKeyMappings {
    public static final KeyMapping DASH_KEY = new KeyMapping("key.elemental_tools_mod.dash_key", GLFW.GLFW_KEY_R, "key.categories.elementaltools") {
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (isDownOld != isDown && isDown) {
                ElementalToolsModMod.PACKET_HANDLER.sendToServer(new DashMessage(0, 0));
                DashMessage.pressAction(Minecraft.getInstance().player, 0, 0);
            }
            isDownOld = isDown;
        }
    };

    public static final KeyMapping CRIT_WAVE_KEY = new KeyMapping("key.elemental_tools_mod.crit_wave_key", GLFW.GLFW_KEY_V, "key.categories.elementaltools") {
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (isDownOld != isDown && isDown) {
                ElementalToolsModMod.PACKET_HANDLER.sendToServer(new CritWaveMessage(0, 0));
                CritWaveMessage.pressAction(Minecraft.getInstance().player, 0, 0);
            }
            isDownOld = isDown;
        }
    };

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(DASH_KEY);
        event.register(CRIT_WAVE_KEY);
    }

    @Mod.EventBusSubscriber({Dist.CLIENT})
    public static class KeyEventListener {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (Minecraft.getInstance().screen == null) {
                DASH_KEY.consumeClick();
                CRIT_WAVE_KEY.consumeClick();
            }
        }
    }
}
