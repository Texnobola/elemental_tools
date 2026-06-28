package net.mcreator.elementaltoolsmod.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.mcreator.elementaltoolsmod.network.ElementalToolsModModVariables;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class QilichShartiProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, Entity sourceentity) {
		execute(null, world, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity sourceentity) {
		if (sourceentity == null)
			return;
		if ((ForgeRegistries.ENTITY_TYPES.getKey(sourceentity.getType()).toString()).equals("minecraft:player")) {
			ElementalToolsModModVariables.WorldVariables.get(world).kill_count = ElementalToolsModModVariables.WorldVariables.get(world).kill_count + 1;
			ElementalToolsModModVariables.WorldVariables.get(world).syncData(world);
			if (ElementalToolsModModVariables.WorldVariables.get(world).kill_count >= 100) {
				if (sourceentity instanceof ServerPlayer _player) {
					Advancement _adv = _player.server.getAdvancements().getAdvancement(ResourceLocation.parse("elemental_tools_mod:qon"));
					AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
					if (!_ap.isDone()) {
						for (String criteria : _ap.getRemainingCriteria())
							_player.getAdvancements().award(_adv, criteria);
					}
				}
			}
		}
	}
}