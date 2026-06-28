package net.mcreator.elementaltoolsmod.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.entity.LivingEntity;
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
			{
				double _setval = (entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).lifesteal_cooldown - 1;
				entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.lifesteal_cooldown = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
		if ((entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_1 > 0) {
			{
				double _setval = (entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_1 - 1;
				entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_timer_1 = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if ((entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_1 <= 0) {
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)
							- (entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_amount_1));
				{
					double _setval = 0;
					entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.steal_amount_1 = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
		if ((entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_2 > 0) {
			{
				double _setval = (entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_2 - 1;
				entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_timer_2 = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if ((entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_2 <= 0) {
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)
							- (entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_amount_2));
				{
					double _setval = 0;
					entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.steal_amount_2 = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
		if ((entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_3 > 0) {
			{
				double _setval = (entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_3 - 1;
				entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_timer_3 = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if ((entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_3 <= 0) {
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)
							- (entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_amount_3));
				{
					double _setval = 0;
					entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.steal_amount_3 = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
		if ((entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_4 > 0) {
			{
				double _setval = (entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_4 - 1;
				entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_timer_4 = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if ((entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_4 <= 0) {
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)
							- (entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_amount_4));
				{
					double _setval = 0;
					entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.steal_amount_4 = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
		if ((entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_5 > 0) {
			{
				double _setval = (entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_5 - 1;
				entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_timer_5 = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if ((entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_5 <= 0) {
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)
							- (entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_amount_5));
				{
					double _setval = 0;
					entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.steal_amount_5 = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
	}
}