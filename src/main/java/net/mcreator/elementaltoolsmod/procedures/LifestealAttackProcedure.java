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
			execute(event, event.getSource().getEntity());
		}
	}

	public static void execute(Entity sourceentity) {
		execute(null, sourceentity);
	}

	private static void execute(@Nullable Event event, Entity sourceentity) {
		if (sourceentity == null)
			return;
		if ((sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).lifesteal_toggle
				&& ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ItemTags.create(ResourceLocation.parse("mod:qonli_qilich_lifesteal")))
						|| (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).is(ItemTags.create(ResourceLocation.parse("mod:qonli_qilich_lifesteal"))))
				&& (ForgeRegistries.ENTITY_TYPES.getKey(sourceentity.getType()).toString()).equals("minecraft:player")
				&& (sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).lifesteal_cooldown <= 0) {
			if (sourceentity instanceof LivingEntity _entity)
				_entity.setHealth((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getDamageValue());
			{
				double _setval = 600;
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.lifesteal_cooldown = _setval;
					capability.syncPlayerVariables(sourceentity);
				});
			}
		}
		if ((sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_1 <= 0) {
			{
				double _setval = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getDamageValue();
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_amount_1 = _setval;
					capability.syncPlayerVariables(sourceentity);
				});
			}
			{
				double _setval = 5020;
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_timer_1 = _setval;
					capability.syncPlayerVariables(sourceentity);
				});
			}
		} else if ((sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_2 <= 0) {
			{
				double _setval = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getDamageValue();
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_amount_2 = _setval;
					capability.syncPlayerVariables(sourceentity);
				});
			}
			{
				double _setval = 5020;
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_timer_2 = _setval;
					capability.syncPlayerVariables(sourceentity);
				});
			}
		} else if ((sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_3 <= 0) {
			{
				double _setval = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getDamageValue();
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_amount_3 = _setval;
					capability.syncPlayerVariables(sourceentity);
				});
			}
			{
				double _setval = 5020;
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_timer_3 = _setval;
					capability.syncPlayerVariables(sourceentity);
				});
			}
		} else if ((sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_4 <= 0) {
			{
				double _setval = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getDamageValue();
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_amount_4 = _setval;
					capability.syncPlayerVariables(sourceentity);
				});
			}
			{
				double _setval = 5020;
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_timer_4 = _setval;
					capability.syncPlayerVariables(sourceentity);
				});
			}
		} else if ((sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).steal_timer_5 <= 0) {
			{
				double _setval = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getDamageValue();
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_amount_5 = _setval;
					capability.syncPlayerVariables(sourceentity);
				});
			}
			{
				double _setval = 5020;
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_timer_5 = _setval;
					capability.syncPlayerVariables(sourceentity);
				});
			}
		} else {
			{
				double _setval = 5020;
				sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.steal_timer_1 = _setval;
					capability.syncPlayerVariables(sourceentity);
				});
			}
		}
	}
}