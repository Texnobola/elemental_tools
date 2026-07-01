package net.mcreator.elementaltoolsmod.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.player.Input;

import net.mcreator.elementaltoolsmod.network.ElementalToolsModModVariables;

@Mod.EventBusSubscriber
public class ResurrectionStunHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide()) {
            Player player = event.player;
            player.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                if (capability.resurrection_stun_ticks > 0) {
                    capability.resurrection_stun_ticks--;
                    
                    // Force zero velocity during stun on server
                    player.setDeltaMovement(0, player.getDeltaMovement().y, 0);
                    player.hurtMarked = true;
                    
                    if (capability.resurrection_stun_ticks <= 0) {
                        capability.syncPlayerVariables(player);
                    }
                }
            });
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(value = Dist.CLIENT)
    public static class ClientHandler {
        @SubscribeEvent
        public static void onMovementInput(MovementInputUpdateEvent event) {
            Player player = event.getEntity();
            if (player != null) {
                player.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                    if (capability.resurrection_stun_ticks > 0) {
                        Input input = event.getInput();
                        input.forwardImpulse = 0;
                        input.leftImpulse = 0;
                        input.up = false;
                        input.down = false;
                        input.left = false;
                        input.right = false;
                        input.jumping = false;
                        input.shiftKeyDown = false;
                    }
                });
            }
        }
    }
}
