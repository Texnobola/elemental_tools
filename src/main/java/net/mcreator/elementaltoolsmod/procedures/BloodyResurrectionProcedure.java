package net.mcreator.elementaltoolsmod.procedures;

import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.mcreator.elementaltoolsmod.network.PlayPlayerAnimationMessage;
import net.mcreator.elementaltoolsmod.network.SpawnVFXMessage;
import net.mcreator.elementaltoolsmod.network.ElementalToolsModModVariables;
import net.mcreator.elementaltoolsmod.init.ElementalToolsModModItems;
import net.mcreator.elementaltoolsmod.init.ElementalToolsModModBlocks;
import net.mcreator.elementaltoolsmod.init.ElementalToolsModModSounds;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

import java.util.List;
import java.util.ArrayList;

@Mod.EventBusSubscriber
public class BloodyResurrectionProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(LivingDeathEvent event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player player) {
			player.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				if (capability.has_consumed_bloody_can && isHoldingBloodySword(player)) {
					if (event != null)
						event.setCanceled(true);
					
					// Resurrection logic
					player.setHealth(player.getMaxHealth());
					player.removeAllEffects();
					player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
					player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
					player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
					
					capability.has_consumed_bloody_can = false;
					capability.resurrection_stun_ticks = 100; // 5 seconds (100 ticks)
					capability.syncPlayerVariables(player);

					if (player instanceof ServerPlayer serverPlayer) {
						// 1. Play Animation (Internal name 'immortality' from immortality_animation.json)
						ElementalToolsModMod.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> serverPlayer), 
							new PlayPlayerAnimationMessage(serverPlayer.getId(), "immortality"));

						// 2. Spawn Sinister VFX
						ElementalToolsModMod.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> serverPlayer), 
							new SpawnVFXMessage(x, y + 1.0, z, "lodestone:bloody_resurrection"));

						// 3. Place Blood Blocks in 10x10 radius
						BlockPos center = BlockPos.containing(x, y, z);
						int radius = 5;
						for (int dx = -radius; dx <= radius; dx++) {
							for (int dz = -radius; dz <= radius; dz++) {
								BlockPos targetPos = center.offset(dx, -1, dz);
								// Replace ground with blood blocks if it's solid
								if (world.getBlockState(targetPos).isSolidRender(world, targetPos)) {
									world.setBlock(targetPos, ElementalToolsModModBlocks.BLOODBLOCK.get().defaultBlockState(), 3);
								}
							}
						}

						// 4. Debuff nearby entities (Slowness 10, Weakness 2)
						List<Entity> entities = world.getEntitiesOfClass(Entity.class, player.getBoundingBox().inflate(10));
						for (Entity target : entities) {
							if (target instanceof LivingEntity livingTarget && livingTarget != player) {
								livingTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 9)); // Slowness 10 (stun) for 5s
								livingTarget.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1)); // Weakness 2 for 5s
							}
						}

						// 5. Play Immortality SFX
						if (world instanceof Level _level && !_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), 
								ElementalToolsModModSounds.IMMORTALITY_SFX.get(), SoundSource.PLAYERS, 1.5f, 1.0f);
						}
					}
				}
			});
		}
	}

	private static boolean isHoldingBloodySword(Player player) {
		ItemStack mainHand = player.getMainHandItem();
		return mainHand.is(ElementalToolsModModItems.QONLIQILICH_1.get()) ||
			   mainHand.is(ElementalToolsModModItems.QONLIQILICH_2.get()) ||
			   mainHand.is(ElementalToolsModModItems.QONLIQILICH_3.get()) ||
			   mainHand.is(ElementalToolsModModItems.QONLIQILICH_4.get()) ||
			   mainHand.is(ElementalToolsModModItems.QONLIQILICH_5.get()) ||
			   mainHand.is(ElementalToolsModModItems.QONLIQILICH_6.get()) ||
			   mainHand.is(ElementalToolsModModItems.QONLIQILICH_7.get());
	}
}
