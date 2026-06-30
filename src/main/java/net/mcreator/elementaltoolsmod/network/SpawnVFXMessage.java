package net.mcreator.elementaltoolsmod.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;

import net.mcreator.elementaltoolsmod.procedures.custom.VFXHelper;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpawnVFXMessage {
    private final double x, y, z;
    private final String vfxPath;

    public SpawnVFXMessage(double x, double y, double z, String vfxPath) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vfxPath = vfxPath;
    }

    public SpawnVFXMessage(FriendlyByteBuf buffer) {
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
        this.vfxPath = buffer.readUtf();
    }

    public static void buffer(SpawnVFXMessage message, FriendlyByteBuf buffer) {
        buffer.writeDouble(message.x);
        buffer.writeDouble(message.y);
        buffer.writeDouble(message.z);
        buffer.writeUtf(message.vfxPath);
    }

    public static void handler(SpawnVFXMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (context.getDirection().getReceptionSide().isClient()) {
                // System.out.println("[Elemental Tools] Client received SpawnVFXMessage at: " + message.x + ", " + message.y + ", " + message.z);
                VFXHelper.spawnStaticVFX(message.x, message.y, message.z, message.vfxPath);
            }
        });
        context.setPacketHandled(true);
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        ElementalToolsModMod.addNetworkMessage(SpawnVFXMessage.class, SpawnVFXMessage::buffer, SpawnVFXMessage::new, SpawnVFXMessage::handler);
    }
}
