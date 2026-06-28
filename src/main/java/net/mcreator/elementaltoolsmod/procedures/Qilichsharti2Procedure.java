package net.mcreator.elementaltoolsmod.procedures;

import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.AdvancementEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.elementaltoolsmod.init.ElementalToolsModModItems;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class Qilichsharti2Procedure {
	@SubscribeEvent
	public static void onAdvancement(AdvancementEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof ServerPlayer _plr0 && _plr0.level() instanceof ServerLevel && _plr0.getAdvancements().getOrStartProgress(_plr0.server.getAdvancements().getAdvancement(ResourceLocation.parse("elemental_tools_mod:qon"))).isDone()) {
			if (entity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(ElementalToolsModModItems.QONLIQILICH_1.get()).copy();
				_setstack.setCount(1);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
		}
	}
}