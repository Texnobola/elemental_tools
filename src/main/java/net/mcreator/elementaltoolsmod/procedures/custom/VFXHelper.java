package net.mcreator.elementaltoolsmod.procedures.custom;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;

import net.mcreator.elementaltoolsmod.init.ElementalToolsModModParticles;

import java.awt.Color;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public class VFXHelper {

    @OnlyIn(Dist.CLIENT)
    public static void spawnStaticVFX(double x, double y, double z, String vfxPath) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;
        
        // System.out.println("[Elemental Tools] spawnStaticVFX starting at " + x + ", " + y + ", " + z);
        
        // Enhanced Lodestone implementation for Bloody Dash - Watery Blood Trail
        Color bloodRed = new Color(255, 0, 0); 
        Color darkBlood = new Color(100, 0, 0);
        Color crimson = new Color(220, 20, 60);
        Color bloodOrange = new Color(255, 69, 0);
        Color wateryBlood = new Color(200, 0, 0, 180); // More transparent for watery effect
        Color deepBlood = new Color(80, 0, 0, 220);

        if (vfxPath.equals("lodestone:bloody_dash")) {
            // #region agent log
            debugLog("H3", "VFXHelper.spawnStaticVFX", "bloody dash vfx spawn", Map.of(
                    "x", x, "y", y, "z", z, "vfxPath", vfxPath));
            // #endregion
            spawnBloodyDash(level, x, y, z, bloodRed, darkBlood, crimson, wateryBlood, deepBlood);
        } else if (vfxPath.equals("lodestone:crit_wave")) {
            spawnCritWave(level, x, y, z, bloodRed, darkBlood, crimson);
        } else if (vfxPath.equals("lodestone:bloody_resurrection")) {
            spawnBloodyResurrection(level, x, y, z);
        }
    }

    private static void spawnBloodyResurrection(Level level, double x, double y, double z) {
        try {
            Color darkPurple = new Color(50, 0, 70);
            Color bloodRed = new Color(150, 0, 0);
            Color deepCrimson = new Color(80, 0, 0);
            Color glisteningRed = new Color(255, 50, 50);
            Color black = new Color(0, 0, 0);
            
            // 1. Sinister Rising Droplets (Liquid fluid rising, no clouds)
            for (int i = 0; i < 40; i++) {
                WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.3f + level.random.nextFloat() * 0.4f, 0.05f)
                        .setEasing(Easing.SINE_IN_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.9f, 0.0f).build())
                    .setColorData(ColorParticleData.create(deepCrimson, black).build())
                    .setLifetime(40 + level.random.nextInt(20))
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .addMotion(
                        (level.random.nextFloat() - 0.5f) * 0.02f,
                        0.08f + level.random.nextFloat() * 0.05f,
                        (level.random.nextFloat() - 0.5f) * 0.02f
                    )
                    .spawn(level, x + (level.random.nextFloat() - 0.5) * 0.8, 
                           y - 0.5, 
                           z + (level.random.nextFloat() - 0.5) * 0.8);
            }

            // 2. Heavy Liquid Swirls (Dense droplets spiraling)
            for (int i = 0; i < 60; i++) {
                double angle = i * 0.25;
                double radius = 0.5 + (i * 0.03);
                WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.45f, 0.1f).build())
                    .setColorData(ColorParticleData.create(bloodRed, deepCrimson).build())
                    .setLifetime(25 + level.random.nextInt(15))
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .addMotion(Math.cos(angle) * 0.05, 0.12, Math.sin(angle) * 0.05)
                    .spawn(level, x + Math.cos(angle) * radius, y - 0.2, z + Math.sin(angle) * radius);
            }

            // 3. Glistening "Longing" Highlights (Soul-like sharp flecks)
            for (int i = 0; i < 20; i++) {
                WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.2f, 0.6f).setEasing(Easing.EXPO_IN).build())
                    .setColorData(ColorParticleData.create(glisteningRed, darkPurple).build())
                    .setLifetime(50 + level.random.nextInt(30))
                    .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                    .addMotion(0, 0.02f, 0)
                    .spawn(level, x + (level.random.nextFloat() - 0.5) * 2.0, 
                           y + 0.5 + (level.random.nextFloat() * 2.0), 
                           z + (level.random.nextFloat() - 0.5) * 2.0);
            }

            // 4. Sharp Fluid Shockwave (Blood splatter expansion)
            for (int i = 0; i < 80; i++) {
                double angle = Math.toRadians(i * 4.5);
                WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.6f, 0.0f).setEasing(Easing.QUAD_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.8f, 0.0f).build())
                    .setColorData(ColorParticleData.create(bloodRed, black).build())
                    .setLifetime(12 + level.random.nextInt(6))
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .addMotion(Math.cos(angle) * 0.5, 0, Math.sin(angle) * 0.5)
                    .spawn(level, x, y + 0.2, z);
            }

            // 5. Impact Damage Flecks (Minecraft built-in for extra fluid feel)
            for (int i = 0; i < 15; i++) {
                level.addParticle(net.minecraft.core.particles.ParticleTypes.DAMAGE_INDICATOR,
                    x + (level.random.nextFloat() - 0.5), y + 1.0, z + (level.random.nextFloat() - 0.5),
                    0, 0.1, 0);
            }

            // 6. Ground Crack VFX (Ring of small cracks for flat ground impact)
            for (int ring = 0; ring < 3; ring++) {
                int particles = 8 + ring * 8;
                double radius = 1.5 + ring * 1.5;
                for (int i = 0; i < particles; i++) {
                    double angle = Math.toRadians(i * (360.0 / particles));
                    WorldParticleBuilder.create(ElementalToolsModModParticles.GROUND_CRACK.get())
                        .setScaleData(GenericParticleData.create(1.5f + ring * 0.5f, 0.0f).setEasing(Easing.EXPO_OUT).build())
                        .setTransparencyData(GenericParticleData.create(0.9f, 0.0f).setEasing(Easing.QUINTIC_OUT).build())
                        .setColorData(ColorParticleData.create(new Color(120, 0, 0), new Color(60, 0, 0)).build())
                        .setLifetime(80)
                        .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                        .spawn(level, 
                            x + Math.cos(angle) * radius, 
                            y + 0.05, 
                            z + Math.sin(angle) * radius);
                }
            }
                
        } catch (Exception e) {}
    }

    private static void spawnCritWave(Level level, double x, double y, double z, Color bloodRed, Color darkBlood, Color crimson) {
        try {
            // 1. Red Spin Core (THIN particles, NO CLOUD/SMOKE)
            for (int i = 0; i < 10; i++) {
                WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.6f + level.random.nextFloat() * 0.4f, 0.0f)
                        .setEasing(Easing.QUAD_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.7f, 0.0f)
                        .setEasing(Easing.QUINTIC_OUT).build())
                    .setColorData(ColorParticleData.create(bloodRed, darkBlood)
                        .setCoefficient(2.0f).setEasing(Easing.QUAD_OUT).build())
                    .setLifetime(10 + level.random.nextInt(8))
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .addMotion(
                        (level.random.nextFloat() - 0.5f) * 0.08f,
                        level.random.nextFloat() * 0.1f - 0.05f,
                        (level.random.nextFloat() - 0.5f) * 0.08f
                    )
                    .enableNoClip()
                    .spawn(level, x, y - 0.6, z); // Lowered to waist level
            }

            // 2. Sharp "Blade Sweep" (Thin, fast streaks)
            for (int i = 0; i < 8; i++) {
                WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setScaleData(GenericParticleData.create(1.5f, 0.0f)
                        .setEasing(Easing.SINE_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0.0f).build())
                    .setColorData(ColorParticleData.create(crimson, new Color(120, 0, 0)).build())
                    .setLifetime(8 + level.random.nextInt(4))
                    .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                    .addMotion(
                        (level.random.nextFloat() - 0.5f) * 0.5f,
                        -0.02f,
                        (level.random.nextFloat() - 0.5f) * 0.5f
                    )
                    .enableNoClip()
                    .spawn(level, x, y - 0.6, z); // Lowered
            }

            // 3. Sharp Red Highlights (Glistening droplets)
            for (int i = 0; i < 6; i++) {
                WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.4f + level.random.nextFloat() * 0.3f, 0.0f)
                        .setEasing(Easing.EXPO_OUT).build())
                    .setColorData(ColorParticleData.create(new Color(255, 100, 100), bloodRed).build())
                    .setLifetime(6 + level.random.nextInt(4))
                    .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                    .spawn(level, x + (level.random.nextFloat() - 0.5) * 1.2, 
                           y - 0.4 + (level.random.nextFloat() - 0.5) * 0.4, 
                           z + (level.random.nextFloat() - 0.5) * 1.2);
            }
        } catch (Exception e) {}
    }

    private static void spawnBloodyDash(Level level, double x, double y, double z, Color bloodRed, Color darkBlood, Color crimson, Color wateryBlood, Color deepBlood) {
        try {
            // 1. Liquid blood streaks - small dense droplets, no cloud wisps
            for (int i = 0; i < 10; i++) {
                WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.25f + level.random.nextFloat() * 0.2f, 0.05f)
                        .setEasing(Easing.SINE_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.95f, 0.3f)
                        .setEasing(Easing.QUINTIC_OUT).build())
                    .setColorData(ColorParticleData.create(bloodRed, deepBlood)
                        .setCoefficient(1.2f).setEasing(Easing.QUAD_OUT).build())
                    .setLifetime(18 + level.random.nextInt(10))
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .addMotion(
                        (level.random.nextFloat() - 0.5f) * 0.03f,
                        -0.06f - level.random.nextFloat() * 0.04f,
                        (level.random.nextFloat() - 0.5f) * 0.03f
                    )
                    .enableNoClip()
                    .spawn(level, x + (level.random.nextFloat() - 0.5f) * 0.35,
                           y + (level.random.nextFloat() - 0.5f) * 0.25,
                           z + (level.random.nextFloat() - 0.5f) * 0.35);
            }

            // 2. Thin blood ribbons along dash path
            for (int i = 0; i < 8; i++) {
                WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.35f + level.random.nextFloat() * 0.25f, 0.0f)
                        .setEasing(Easing.CUBIC_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.85f, 0.0f)
                        .setEasing(Easing.QUINTIC_OUT).build())
                    .setColorData(ColorParticleData.create(crimson, darkBlood)
                        .setCoefficient(1.3f).setEasing(Easing.QUAD_OUT).build())
                    .setLifetime(22 + level.random.nextInt(12))
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .addMotion(
                        (level.random.nextFloat() - 0.5f) * 0.02f,
                        -0.05f - level.random.nextFloat() * 0.03f,
                        (level.random.nextFloat() - 0.5f) * 0.02f
                    )
                    .enableNoClip()
                    .spawn(level, x + (level.random.nextFloat() - 0.5f) * 0.5,
                           y + (level.random.nextFloat() - 0.5f) * 0.3,
                           z + (level.random.nextFloat() - 0.5f) * 0.5);
            }

            // 3. Dripping blood drops with gravity
            for (int i = 0; i < 12; i++) {
                WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.35f + level.random.nextFloat() * 0.25f, 0.0f)
                        .setEasing(Easing.SINE_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.95f, 0.0f)
                        .setEasing(Easing.QUINTIC_OUT).build())
                    .setColorData(ColorParticleData.create(bloodRed, darkBlood)
                        .setCoefficient(1.5f).setEasing(Easing.SINE_OUT).build())
                    .setLifetime(28 + level.random.nextInt(14))
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .addMotion(
                        (level.random.nextFloat() - 0.5f) * 0.04f,
                        -0.1f - level.random.nextFloat() * 0.08f,
                        (level.random.nextFloat() - 0.5f) * 0.04f
                    )
                    .enableNoClip()
                    .spawn(level, x + (level.random.nextFloat() - 0.5f) * 0.3,
                           y + (level.random.nextFloat() - 0.15f),
                           z + (level.random.nextFloat() - 0.5f) * 0.3);
            }

            // 4. Wet highlights - tiny bright specs, not fog
            for (int i = 0; i < 5; i++) {
                WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.15f + level.random.nextFloat() * 0.15f, 0.0f)
                        .setEasing(Easing.QUAD_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.7f, 0.0f)
                        .setEasing(Easing.CUBIC_OUT).build())
                    .setColorData(ColorParticleData.create(new Color(255, 80, 80), bloodRed)
                        .setCoefficient(2.0f).build())
                    .setLifetime(10 + level.random.nextInt(6))
                    .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                    .addMotion(0, -0.04f, 0)
                    .spawn(level, x + (level.random.nextFloat() - 0.5f) * 0.4,
                           y + (level.random.nextFloat() - 0.5f) * 0.3,
                           z + (level.random.nextFloat() - 0.5f) * 0.4);
            }

            // 5. Blood splatter impact flecks
            for (int i = 0; i < 4; i++) {
                level.addParticle(net.minecraft.core.particles.ParticleTypes.DAMAGE_INDICATOR,
                    x + (level.random.nextFloat() - 0.5f) * 0.3,
                    y + 0.15,
                    z + (level.random.nextFloat() - 0.5f) * 0.3,
                    0, 0.04, 0);
            }

        } catch (Exception e) {
            // #region agent log
            debugLog("H3", "VFXHelper.spawnBloodyDash", "vfx error", Map.of("error", e.getMessage()));
            // #endregion
            e.printStackTrace();
        }
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
