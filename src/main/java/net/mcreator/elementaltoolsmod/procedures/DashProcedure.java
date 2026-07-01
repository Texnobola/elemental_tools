package net.mcreator.elementaltoolsmod.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;

import net.mcreator.elementaltoolsmod.network.ElementalToolsModModVariables;
import net.mcreator.elementaltoolsmod.procedures.custom.DashCapability;
import net.mcreator.elementaltoolsmod.network.SyncCustomDataMessage;
import net.mcreator.elementaltoolsmod.network.SpawnVFXMessage;
import net.mcreator.elementaltoolsmod.init.ElementalToolsModModItems;
import net.mcreator.elementaltoolsmod.init.ElementalToolsModModSounds;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;
import net.minecraftforge.network.PacketDistributor;

public class DashProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;

		// Check sword stage requirement (Stage 4+)
		int stage = getSwordStage(entity);
		if (stage < 4) {
			return; // Ability not unlocked yet
		}
		
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
			if (world instanceof Level _level && !_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), 
					ElementalToolsModModSounds.BLOODY_DASH_SFX.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
			}

			// Set cooldown to 10 seconds (200 ticks)
			entity.getCapability(DashCapability.CAPABILITY, null).ifPresent(capability -> {
				capability.dashCooldown = 200;
				if (entity instanceof ServerPlayer serverPlayer) {
					SyncCustomDataMessage.send(serverPlayer);
				}
			});

			if (entity instanceof ServerPlayer serverPlayer) {
				// Spawn Bloody Dash VFX at 12 points along the path for a smooth continuous trail
				// Trail should appear BEHIND the player, so we spawn from start position backwards
				Vec3 dashStart = new Vec3(x, y, z); // Original position before dash
				Vec3 dashEnd = serverPlayer.position(); // Position after dash
				Vec3 dashVector = dashEnd.subtract(dashStart); // Vector from start to end
				
				// Spawn particles along the path from start to end (behind the player)
				for (int i = 0; i <= 11; i++) {
					double progress = i / 11.0;
					Vec3 spawnPos = dashStart.add(dashVector.scale(progress));
					ElementalToolsModMod.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> serverPlayer), 
						new SpawnVFXMessage(spawnPos.x, spawnPos.y + 0.5, spawnPos.z, "lodestone:bloody_dash"));
				}
			}
		}
	}

	private static int getSwordStage(Entity entity) {
		if (!(entity instanceof LivingEntity living))
			return 0;
		Item item = living.getMainHandItem().getItem();
		if (item == ElementalToolsModModItems.QONLIQILICH_1.get())
			return 1;
		if (item == ElementalToolsModModItems.QONLIQILICH_2.get())
			return 2;
		if (item == ElementalToolsModModItems.QONLIQILICH_3.get())
			return 3;
		if (item == ElementalToolsModModItems.QONLIQILICH_4.get())
			return 4;
		if (item == ElementalToolsModModItems.QONLIQILICH_5.get())
			return 5;
		if (item == ElementalToolsModModItems.QONLIQILICH_6.get())
			return 6;
		if (item == ElementalToolsModModItems.QONLIQILICH_7.get())
			return 7;
		return 0;
	}
}
