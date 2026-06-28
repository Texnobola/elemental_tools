package net.mcreator.elementaltoolsmod.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
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

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (((entity instanceof LivingEntity _entity) ? _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_1.get()) : false) || ((entity instanceof LivingEntity _entity) ? _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_2.get()) : false)
				|| ((entity instanceof LivingEntity _entity) ? _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_3.get()) : false)) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 99999, 3, false, false));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 99999, 1, false, false));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 99999, 1, false, false));
		}
		if (((entity instanceof LivingEntity _entity) ? _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_4.get()) : false) || ((entity instanceof LivingEntity _entity) ? _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_5.get()) : false)
				|| ((entity instanceof LivingEntity _entity) ? _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_6.get()) : false)
				|| ((entity instanceof LivingEntity _entity) ? _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_2.get()) : false)
				|| ((entity instanceof LivingEntity _entity) ? _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_7.get()) : false)) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.LUCK, 99999, 2, false, false));
		}
		if (((entity instanceof LivingEntity _entity) ? _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_3.get()) : false) || ((entity instanceof LivingEntity _entity) ? _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_4.get()) : false)
				|| ((entity instanceof LivingEntity _entity) ? _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_5.get()) : false)
				|| ((entity instanceof LivingEntity _entity) ? _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_6.get()) : false)
				|| ((entity instanceof LivingEntity _entity) ? _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_7.get()) : false)) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 99999, 2, false, false));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 99999, 3, false, false));
		}
		if (((entity instanceof LivingEntity _entity) ? _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_6.get()) : false)
				|| ((entity instanceof LivingEntity _entity) ? _entity.isHolding(ElementalToolsModModItems.QONLIQILICH_7.get()) : false)) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 99999, 2, false, false));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 99999, 4, false, false));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 99999, 3, false, false));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 99999, 4, false, false));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.LUCK, 99999, 3, false, false));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 99999, 2, false, false));
		}
	}
}