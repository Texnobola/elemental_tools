package net.mcreator.elementaltoolsmod.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.mcreator.elementaltoolsmod.network.ElementalToolsModModVariables;
import net.mcreator.elementaltoolsmod.procedures.custom.DashCapability;
import net.mcreator.elementaltoolsmod.network.SyncCustomDataMessage;
import net.mcreator.elementaltoolsmod.network.SpawnVFXMessage;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;
import net.minecraftforge.network.PacketDistributor;

public class DashProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		
		DashCapability.DashHolder dashData = entity.getCapability(DashCapability.CAPABILITY, null)
				.orElseGet(DashCapability.DashHolder::new);
		double cooldown = dashData.dashCooldown;

		if (cooldown <= 0) {
			// Dash logic
			Vec3 lookVec = entity.getLookAngle();
			double dashDistance = 5.0;
			
			// Collision detection: Check if there's a block in the dash path
			Vec3 startPos = entity.getEyePosition(1.0f);
			Vec3 endPos = startPos.add(lookVec.scale(dashDistance));
			
			BlockHitResult hitResult = world.clip(new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
			
			Vec3 finalTarget;
			if (hitResult.getType() != HitResult.Type.MISS) {
				// If hit a block, stop just before it
				finalTarget = hitResult.getLocation().subtract(lookVec.scale(0.5));
			} else {
				finalTarget = endPos;
			}

			// Apply movement
			// We use teleport to ensure precise 5 block movement as requested, 
			// but maintain some velocity for "feel"
			Vec3 _finalTarget = finalTarget;
			Vec3 _lookVec = lookVec;
			if (entity instanceof ServerPlayer _serverPlayer) {
				_serverPlayer.teleportTo(_finalTarget.x, _finalTarget.y, _finalTarget.z);
			} else {
				entity.setPos(_finalTarget.x, _finalTarget.y, _finalTarget.z);
			}
			
			// Optional: add some velocity in the dash direction to make it feel smoother
			entity.setDeltaMovement(_lookVec.scale(0.5));

			// Sound feedback
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), 
						ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.ender_dragon.flap")), 
						SoundSource.PLAYERS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, 
						ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.ender_dragon.flap")), 
						SoundSource.PLAYERS, 1, 1, false);
				}
			}

			// Set cooldown to 10 seconds (200 ticks)
			entity.getCapability(DashCapability.CAPABILITY, null).ifPresent(capability -> {
				capability.dashCooldown = 200;
				if (entity instanceof ServerPlayer serverPlayer) {
					SyncCustomDataMessage.send(serverPlayer);
					// Spawn Bloody Dash VFX at 5 points along the path for a continuous trail
                    Vec3 start = serverPlayer.position();
                    Vec3 direction = _lookVec;
                    
                    for (int i = 0; i <= 4; i++) {
                        double dist = i * (dashDistance / 4.0);
                        Vec3 spawnPos = start.add(direction.scale(dist));
                        // System.out.println("[Elemental Tools] Sending SpawnVFXMessage for point " + i + " at " + spawnPos);
                        ElementalToolsModMod.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> serverPlayer), 
                            new SpawnVFXMessage(spawnPos.x, spawnPos.y + 0.5, spawnPos.z, "lodestone:bloody_dash"));
                    }
				}
			});
		}
	}
}
