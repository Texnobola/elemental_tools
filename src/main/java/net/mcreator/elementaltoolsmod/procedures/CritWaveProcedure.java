package net.mcreator.elementaltoolsmod.procedures;

import net.minecraftforge.network.PacketDistributor;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;

import net.mcreator.elementaltoolsmod.network.ElementalToolsModModVariables;
import net.mcreator.elementaltoolsmod.network.PlayPlayerAnimationMessage;
import net.mcreator.elementaltoolsmod.network.SpawnVFXMessage;
import net.mcreator.elementaltoolsmod.procedures.custom.CritSpinHelper;
import net.mcreator.elementaltoolsmod.init.ElementalToolsModModItems;
import net.mcreator.elementaltoolsmod.init.ElementalToolsModModSounds;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

public class CritWaveProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;

        int stage = getSwordStage(entity);
        if (stage < 5)
            return;

        entity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            if (capability.crit_cooldown <= 0) {
                capability.crit_cooldown = 300; // 15 seconds (300 ticks)
                capability.syncPlayerVariables(entity);

                if (entity instanceof ServerPlayer serverPlayer) {
                    // #region agent log
                    debugLog("H1", "CritWaveProcedure.execute", "crit wave triggered", Map.of(
                            "playerId", serverPlayer.getId(),
                            "yawBeforeSpin", serverPlayer.getYRot(),
                            "stage", stage));
                    // #endregion

                    // Manual 360 spin over 20 ticks (animation is 1s, torso bone can't spin the player)
                    CritSpinHelper.startSpin(serverPlayer, 20, 360f);

                    // Trigger animation via PlayerAnimator
                    ElementalToolsModMod.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> serverPlayer), 
                        new PlayPlayerAnimationMessage(serverPlayer.getId(), "crit_wave"));

                    // VFX - Spawn circular spin points around player (18 points for performance and visibility)
                    for (int i = 0; i < 18; i++) {
                        double angle = Math.toRadians(i * 20);
                        double radius = 2.2;
                        double spawnX = x + Math.cos(angle) * radius;
                        double spawnZ = z + Math.sin(angle) * radius;
                        ElementalToolsModMod.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> serverPlayer), 
                            new SpawnVFXMessage(spawnX, y + 0.2, spawnZ, "lodestone:crit_wave"));
                    }

                    // Damage calculation (2.5x original sword damage)
                    ItemStack sword = serverPlayer.getMainHandItem();
                    double baseDamage = sword.getAttributeModifiers(net.minecraft.world.entity.EquipmentSlot.MAINHAND)
                        .get(Attributes.ATTACK_DAMAGE).stream()
                        .mapToDouble(net.minecraft.world.entity.ai.attributes.AttributeModifier::getAmount)
                        .sum() + 1.0; // +1 for base fist damage
                    float finalDamage = (float) (baseDamage * 2.5);

                    // Damage entities in a 2-block radius
                    AABB area = entity.getBoundingBox().inflate(2.0);
                    List<Entity> targets = world.getEntitiesOfClass(Entity.class, area, e -> e != entity && e instanceof LivingEntity);
                    for (Entity target : targets) {
                        if (target.distanceTo(entity) <= 2.5) { // 2.5 radius to cover the 2x2 box well
                            target.hurt(entity.damageSources().playerAttack(serverPlayer), finalDamage);
                            // Circular knockback
                            Vec3 knockbackVec = target.position().subtract(entity.position()).normalize();
                            if (target instanceof LivingEntity livingTarget) {
                                livingTarget.knockback(1.0, -knockbackVec.x, -knockbackVec.z);
                            }
                        }
                    }
                    
                    // Sound feedback
            if (world instanceof Level _level && !_level.isClientSide()) {
                _level.playSound(null, BlockPos.containing(x, y, z), 
                    ElementalToolsModModSounds.CRIT_WAVE_SFX.get(), SoundSource.PLAYERS, 1.2f, 1.0f);
            }
                }
            }
        });
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

    // #region agent log
    private static void debugLog(String hypothesisId, String location, String message, Map<String, Object> data) {
        try {
            StringBuilder dataJson = new StringBuilder("{");
            boolean first = true;
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (!first)
                    dataJson.append(',');
                first = false;
                dataJson.append('"').append(entry.getKey()).append("\":");
                Object value = entry.getValue();
                if (value instanceof String s)
                    dataJson.append('"').append(s.replace("\"", "\\\"")).append('"');
                else
                    dataJson.append(value);
            }
            dataJson.append('}');
            Path logPath = Path.of("c:/Users/dev/MCreatorWorkspaces/elemental_tools_mod/debug-179cbb.log");
            String line = "{\"sessionId\":\"179cbb\",\"hypothesisId\":\"" + hypothesisId
                    + "\",\"location\":\"" + location + "\",\"message\":\"" + message
                    + "\",\"data\":" + dataJson + ",\"timestamp\":" + System.currentTimeMillis() + "}\n";
            Files.writeString(logPath, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception ignored) {
        }
    }
    // #endregion
}
