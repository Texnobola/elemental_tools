package net.mcreator.elementaltoolsmod.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.elementaltoolsmod.network.ElementalToolsModModVariables;

public class LifestealToggleOnKeyPressedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			boolean _setval = !(entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).lifesteal_toggle;
			entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.lifesteal_toggle = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
	}
}