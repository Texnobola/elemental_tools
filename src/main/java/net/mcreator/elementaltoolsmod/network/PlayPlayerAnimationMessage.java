package net.mcreator.elementaltoolsmod.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;

import net.mcreator.elementaltoolsmod.procedures.custom.PlayerAnimationHelper;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

import java.util.function.Supplier;

public class PlayPlayerAnimationMessage {
    private final int playerId;
    private final String animationName;

    public PlayPlayerAnimationMessage(int playerId, String animationName) {
        this.playerId = playerId;
        this.animationName = animationName;
    }

    public PlayPlayerAnimationMessage(FriendlyByteBuf buffer) {
        this.playerId = buffer.readInt();
        this.animationName = buffer.readUtf();
    }

    public static void buffer(PlayPlayerAnimationMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.playerId);
        buffer.writeUtf(message.animationName);
    }

    public static void handler(PlayPlayerAnimationMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (context.getDirection().getReceptionSide().isClient()) {
                handleClient(message);
            }
        });
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleClient(PlayPlayerAnimationMessage message) {
        if (Minecraft.getInstance().level == null) return;
        Entity entity = Minecraft.getInstance().level.getEntity(message.playerId);
        if (entity instanceof AbstractClientPlayer clientPlayer) {
            PlayerAnimationHelper.playAnimation(clientPlayer, message.animationName);
        }
    }

    public static void registerMessage(FMLCommonSetupEvent event) {
        ElementalToolsModMod.addNetworkMessage(PlayPlayerAnimationMessage.class, PlayPlayerAnimationMessage::buffer, PlayPlayerAnimationMessage::new, PlayPlayerAnimationMessage::handler);
    }
}
