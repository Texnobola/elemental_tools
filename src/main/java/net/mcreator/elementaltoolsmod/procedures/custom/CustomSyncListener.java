package net.mcreator.elementaltoolsmod.procedures.custom;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraft.server.level.ServerPlayer;
import net.mcreator.elementaltoolsmod.network.SyncCustomDataMessage;

@Mod.EventBusSubscriber
public class CustomSyncListener {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            // 1. Reapply the max health attribute modifier from the capability
            HeartStealHandler.reapplyStoredMaxHealth(serverPlayer);
            
            // 2. Sync our custom capabilities to the client
            SyncCustomDataMessage.send(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawned(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            // Reapply max health on respawn
            HeartStealHandler.reapplyStoredMaxHealth(serverPlayer);
            
            // Sync custom capabilities
            SyncCustomDataMessage.send(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            SyncCustomDataMessage.send(serverPlayer);
        }
    }
}
