package net.mcreator.elementaltoolsmod.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.mcreator.elementaltoolsmod.network.ElementalToolsModModVariables;
import net.mcreator.elementaltoolsmod.init.ElementalToolsModModItems;

@Mod.EventBusSubscriber
public class BloodyCanConsumptionProcedure {
	@SubscribeEvent
	public static void onUseItemFinish(LivingEntityUseItemEvent.Finish event) {
		if (event != null && event.getEntity() != null) {
			execute(event.getEntity(), event.getItem());
		}
	}

	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (itemstack.getItem() == ElementalToolsModModItems.BLOODCAN.get()) {
			entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.has_consumed_bloody_can = true;
				capability.syncPlayerVariables(entity);
			});
		}
	}
}
