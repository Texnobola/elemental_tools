package net.mcreator.elementaltoolsmod.procedures;

// NOTE: simplified by hand. The original 5-slot steal_timer_1..5 /
// steal_amount_1..5 delayed-payout system (the "4 minutes 11 seconds"
// queue) has been removed -- it's superseded by the instant per-hit
// max-health steal in procedures/custom/HeartStealHandler.java. All
// that's still needed here is ticking down the 30-second lifesteal
// attack cooldown.

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.entity.Entity;

import net.mcreator.elementaltoolsmod.network.ElementalToolsModModVariables;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class StealDecayProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player);
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if ((entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).lifesteal_cooldown > 0) {
			double _setval = (entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).lifesteal_cooldown - 1;
			entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.lifesteal_cooldown = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
	}
}