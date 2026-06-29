package net.mcreator.elementaltoolsmod.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.mcreator.elementaltoolsmod.network.ElementalToolsModModVariables;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class KillxpProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(Entity entity, Entity sourceentity) {
		execute(null, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if ((ForgeRegistries.ENTITY_TYPES.getKey(sourceentity.getType()).toString()).equals("minecraft:player")
				&& ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ItemTags.create(ResourceLocation.parse("elemental_tools_mod:qonli_qilich_swords")))
						|| (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).is(ItemTags.create(ResourceLocation.parse("elemental_tools_mod:qonli_qilich_swords"))))) {
			if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:bosses")))) {
				{
					double _setval = (sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).sword_xp + 50;
					sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.sword_xp = _setval;
						capability.syncPlayerVariables(sourceentity);
					});
				}
			} else if ((ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:zombie") || (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:skeleton")
					|| (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:spider") || (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:creeper")
					|| (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:drowned") || (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:witch")
					|| (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:husk") || (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:pillager")
					|| (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:enderman") || (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:vindicator")) {
				{
					double _setval = (sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).sword_xp + 10;
					sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.sword_xp = _setval;
						capability.syncPlayerVariables(sourceentity);
					});
				}
			} else if ((ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:wolf") || (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:iron_golem")
					|| (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:bee") || (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:goat")
					|| (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:polar_bear") || (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:llama")
					|| (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:zoglin")) {
				{
					double _setval = (sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).sword_xp + 3;
					sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.sword_xp = _setval;
						capability.syncPlayerVariables(sourceentity);
					});
				}
			} else if (true) {
				{
					double _setval = (sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).sword_xp + 1;
					sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.sword_xp = _setval;
						capability.syncPlayerVariables(sourceentity);
					});
				}
			}
		}
	}
}