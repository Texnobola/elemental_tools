package net.mcreator.elementaltoolsmod.procedures;

// FIX (hand-applied): the original version called _entity.addEffect(...)
// unconditionally every single tick for every potion in this list. In
// Forge/vanilla, re-adding a potion effect that's already active replaces
// the existing instance -- for HEALTH_BOOST specifically this also forces
// a full recalculation of the player's max-health attribute modifier,
// which resets any bonus hearts back to whatever fraction they start at,
// 20 times a second. That's what caused "filled bonus hearts immediately
// reset" and the general HUD flicker on every potion.
//
// Fix: each addEffect call is now guarded by hasEffect(...) (or, where the
// amplifier can increase between sword stages, by checking the current
// amplifier is already at least as high as the target). The effect is
// only (re-)applied when it's missing or needs to be upgraded -- not on
// every tick it's already correctly active.

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.mcreator.elementaltoolsmod.init.ElementalToolsModModItems;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class PassiveeffectsProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player);
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	/**
	 * Applies (or upgrades) a potion effect only if the entity doesn't
	 * already have it at this amplifier or higher. Prevents the every-tick
	 * reset that was breaking Health Boost's bonus hearts.
	 */
	private static void applyIfNeeded(LivingEntity entity, MobEffect effect, int amplifier) {
		MobEffectInstance current = entity.getEffect(effect);
		if (current != null && current.getAmplifier() >= amplifier && current.getDuration() > 20) {
			// already active at this level or higher, with plenty of duration
			// left -- leave it alone so we don't reset Health Boost's hearts
			// (or interrupt/restart any other effect unnecessarily).
			return;
		}
		entity.addEffect(new MobEffectInstance(effect, 99999, amplifier, false, false));
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (!(entity instanceof LivingEntity _entity) || _entity.level().isClientSide())
			return;

		if (_entity.isHolding(ElementalToolsModModItems.QONLIQILICH_1.get()) || _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_2.get())
				|| _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_3.get())) {
			applyIfNeeded(_entity, MobEffects.MOVEMENT_SPEED, 3);
			applyIfNeeded(_entity, MobEffects.JUMP, 1);
			applyIfNeeded(_entity, MobEffects.NIGHT_VISION, 1);
		}
		if (_entity.isHolding(ElementalToolsModModItems.QONLIQILICH_4.get()) || _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_5.get())
				|| _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_6.get()) || _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_2.get())
				|| _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_7.get())) {
			applyIfNeeded(_entity, MobEffects.LUCK, 2);
		}
		if (_entity.isHolding(ElementalToolsModModItems.QONLIQILICH_3.get()) || _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_4.get())
				|| _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_5.get()) || _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_6.get())
				|| _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_7.get())) {
			applyIfNeeded(_entity, MobEffects.REGENERATION, 2);
			applyIfNeeded(_entity, MobEffects.HEALTH_BOOST, 3);
		}
		if (_entity.isHolding(ElementalToolsModModItems.QONLIQILICH_6.get()) || _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_7.get())) {
			applyIfNeeded(_entity, MobEffects.JUMP, 2);
			applyIfNeeded(_entity, MobEffects.HEALTH_BOOST, 4);
			applyIfNeeded(_entity, MobEffects.REGENERATION, 3);
			applyIfNeeded(_entity, MobEffects.MOVEMENT_SPEED, 4);
			applyIfNeeded(_entity, MobEffects.LUCK, 3);
			applyIfNeeded(_entity, MobEffects.NIGHT_VISION, 2);
		}
	}
}