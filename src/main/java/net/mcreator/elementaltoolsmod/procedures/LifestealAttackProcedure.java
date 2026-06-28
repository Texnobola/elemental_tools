package net.mcreator.elementaltoolsmod.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.elementaltoolsmod.network.ElementalToolsModModVariables;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class LifestealAttackProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(Entity sourceentity) {
		execute(null, sourceentity, 0f);
	}

	private static void execute(@Nullable Event event, Entity sourceentity, float damageAmount) {
		if (sourceentity == null)
			return;

		var playerCap = sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElseGet(ElementalToolsModModVariables.PlayerVariables::new);

		boolean hasLifestealSword =
				(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)
						.is(ItemTags.create(ResourceLocation.parse("mod:qonli_qilich_lifesteal")))
				|| (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)
						.is(ItemTags.create(ResourceLocation.parse("mod:qonli_qilich_lifesteal")));

		boolean isPlayer = (ForgeRegistries.ENTITY_TYPES.getKey(sourceentity.getType()).toString()).equals("minecraft:player");

		// Only run lifesteal logic if: toggle on + sword equipped + is player + cooldown ready
		if (playerCap.lifesteal_toggle && hasLifestealSword && isPlayer && playerCap.lifesteal_cooldown <= 0) {

			// FIX 1: Use actual damage dealt (damageAmount), not getDamageValue()
			if (sourceentity instanceof LivingEntity _entity) {
				_entity.setHealth(_entity.getHealth() + damageAmount);
			}

			// Start cooldown
			sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.lifesteal_cooldown = 600;
				capability.syncPlayerVariables(sourceentity);
			});

			// FIX 2: Slot filling is now INSIDE the lifesteal condition
			// Fill the first available steal slot, or refresh slot 1 if all full
			if (playerCap.steal_timer_1 <= 0) {
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_amount_1 = damageAmount;
					capability.steal_timer_1 = 5020;
					capability.syncPlayerVariables(sourceentity);
				});
			} else if (playerCap.steal_timer_2 <= 0) {
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_amount_2 = damageAmount;
					capability.steal_timer_2 = 5020;
					capability.syncPlayerVariables(sourceentity);
				});
			} else if (playerCap.steal_timer_3 <= 0) {
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_amount_3 = damageAmount;
					capability.steal_timer_3 = 5020;
					capability.syncPlayerVariables(sourceentity);
				});
			} else if (playerCap.steal_timer_4 <= 0) {
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_amount_4 = damageAmount;
					capability.steal_timer_4 = 5020;
					capability.syncPlayerVariables(sourceentity);
				});
			} else if (playerCap.steal_timer_5 <= 0) {
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_amount_5 = damageAmount;
					capability.steal_timer_5 = 5020;
					capability.syncPlayerVariables(sourceentity);
				});
			} else {
				// All slots full — refresh slot 1
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_timer_1 = 5020;
					capability.syncPlayerVariables(sourceentity);
				});
			}
		}
	}
}