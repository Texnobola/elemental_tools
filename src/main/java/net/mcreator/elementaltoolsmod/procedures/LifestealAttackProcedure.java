package net.mcreator.elementaltoolsmod.procedures;

// NOTE: disabled by hand. The old per-hit "heal yourself by attack
// damage attribute, on LivingAttackEvent" logic (including the dead
// steal_timer slot code) has been fully replaced by
// procedures/custom/HeartStealHandler.java, which:
//   - listens on LivingHurtEvent (final, post-mitigation damage,
//     matching the actual hearts removed from the health bar)
//   - grants the attacker permanent max HP equal to damage dealt
//   - removes that same amount of permanent max HP from the victim
//     (player or mob), with no floor
//   - still respects lifesteal_toggle and the 600-tick/30s
//     lifesteal_cooldown (decremented in StealDecayProcedure)
//
// This class is left in place (rather than deleted) so MCreator's own
// procedure list / regeneration doesn't get confused by a missing
// element file, but its event handler is intentionally a no-op now.
// If you ever re-enable this in MCreator's Blockly editor instead,
// make sure to remove/disable HeartStealHandler to avoid double-firing.

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

@Mod.EventBusSubscriber
public class LifestealAttackProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		// intentionally disabled -- see note above
	}
}