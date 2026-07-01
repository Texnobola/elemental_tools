package net.mcreator.elementaltoolsmod.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.client.Minecraft;

import net.mcreator.elementaltoolsmod.procedures.custom.DashCapability;
import net.mcreator.elementaltoolsmod.procedures.custom.BonusHeartsCapability;
import net.mcreator.elementaltoolsmod.procedures.custom.CheatCapability;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SyncCustomDataMessage {
    private final double dashCooldown;
    private final double bonusHearts;
    private final boolean cooldownsDisabled;

    public SyncCustomDataMessage(double dashCooldown, double bonusHearts, boolean cooldownsDisabled) {
        this.dashCooldown = dashCooldown;
        this.bonusHearts = bonusHearts;
        this.cooldownsDisabled = cooldownsDisabled;
    }

    public SyncCustomDataMessage(FriendlyByteBuf buffer) {
        this.dashCooldown = buffer.readDouble();
        this.bonusHearts = buffer.readDouble();
        this.cooldownsDisabled = buffer.readBoolean();
    }

    public static void buffer(SyncCustomDataMessage message, FriendlyByteBuf buffer) {
        buffer.writeDouble(message.dashCooldown);
        buffer.writeDouble(message.bonusHearts);
        buffer.writeBoolean(message.cooldownsDisabled);
    }

    public static void handler(SyncCustomDataMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (!context.getDirection().getReceptionSide().isServer()) {
                Player player = Minecraft.getInstance().player;
                if (player != null) {
                    player.getCapability(DashCapability.CAPABILITY).ifPresent(c -> c.dashCooldown = message.dashCooldown);
                    player.getCapability(BonusHeartsCapability.CAPABILITY).ifPresent(c -> c.bonusHearts = message.bonusHearts);
                    player.getCapability(CheatCapability.CAPABILITY).ifPresent(c -> c.cooldownsDisabled = message.cooldownsDisabled);
                }
            }
        });
        context.setPacketHandled(true);
    }

    public static void send(ServerPlayer player) {
        double dash = player.getCapability(DashCapability.CAPABILITY).map(c -> c.dashCooldown).orElse(0.0D);
        double hearts = player.getCapability(BonusHeartsCapability.CAPABILITY).map(c -> c.bonusHearts).orElse(0.0D);
        boolean cheats = player.getCapability(CheatCapability.CAPABILITY).map(c -> c.cooldownsDisabled).orElse(false);
        ElementalToolsModMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player), new SyncCustomDataMessage(dash, hearts, cheats));
    }

    public static void registerMessage(FMLCommonSetupEvent event) {
        ElementalToolsModMod.addNetworkMessage(SyncCustomDataMessage.class, SyncCustomDataMessage::buffer, SyncCustomDataMessage::new, SyncCustomDataMessage::handler);
    }
}
