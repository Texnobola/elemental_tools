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

public class DashProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		
		double cooldown = (entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElseGet(ElementalToolsModModVariables.PlayerVariables::new)).dash_cooldown;

		if (cooldown <= 0) {
			// Set cooldown to 10 seconds (200 ticks)
			entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.dash_cooldown = 200;
				capability.syncPlayerVariables(entity);
			});

			// Dash logic
			Vec3 lookVec = entity.getLookAngle();
			double dashDistance = 5.0;
			
			// Collision detection: Check if there's a block in the dash path
			Vec3 startPos = entity.eyePosition();
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
			if (entity instanceof ServerPlayer _serverPlayer) {
				_serverPlayer.teleportTo(finalTarget.x, finalTarget.y, finalTarget.z);
			} else {
				entity.setPos(finalTarget.x, finalTarget.y, finalTarget.z);
			}
			
			// Optional: add some velocity in the dash direction to make it feel smoother
			entity.setDeltaMovement(lookVec.scale(0.5));

			// Sound feedback
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), 
						ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.ender_drag_fly")), 
						SoundSource.PLAYERS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, 
						ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.ender_drag_fly")), 
						SoundSource.PLAYERS, 1, 1, false);
				}
			}
		}
	}
}
