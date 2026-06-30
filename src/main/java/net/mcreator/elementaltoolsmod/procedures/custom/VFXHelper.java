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
import team.lodestar.lodestone.systems.particle.render_types.*;

import java.awt.Color;

public class VFXHelper {

    @OnlyIn(Dist.CLIENT)
    public static void spawnStaticVFX(double x, double y, double z, String vfxPath) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;
        
        // System.out.println("[Elemental Tools] spawnStaticVFX starting at " + x + ", " + y + ", " + z);
        
        // Lodestone implementation for Bloody Dash
        Color bloodRed = new Color(255, 0, 0); 
        Color darkBlood = new Color(100, 0, 0);

        try {
            // 1. Lodestone Particles (The requested high-quality VFX)
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setScaleData(GenericParticleData.create(2.5f, 0.0f).setEasing(Easing.QUARTIC_OUT).build())
                .setTransparencyData(GenericParticleData.create(1.0f, 0.0f).build())
                .setColorData(ColorParticleData.create(bloodRed, darkBlood).setCoefficient(1.4f).setEasing(Easing.QUARTIC_OUT).build())
                .setLifetime(40)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .addMotion(0, 0.02f, 0)
                .enableNoClip()
                .spawn(level, x, y, z);
                
            // Splash particles
            for (int i = 0; i < 15; i++) {
                WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(1.0f, 0.0f).build())
                    .setTransparencyData(GenericParticleData.create(0.9f, 0.0f).build())
                    .setColorData(ColorParticleData.create(bloodRed, Color.BLACK).build())
                    .setLifetime(20 + level.random.nextInt(25))
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .addMotion((level.random.nextFloat() - 0.5f) * 0.25f, (level.random.nextFloat() * 0.2f), (level.random.nextFloat() - 0.5f) * 0.25f)
                    .enableNoClip()
                    .spawn(level, x, y, z);
            }

            // 2. Vanilla Fallback (Redstone Dust) - Temporary, to confirm packet arrival
            // If you see red dust but no mist, Lodestone's engine is failing to render.
            // If you see NOTHING, the network packet is not reaching the client.
            for (int i = 0; i < 5; i++) {
                level.addParticle(net.minecraft.core.particles.ParticleTypes.SMOKE, 
                    x, y, z, 0, 0.1, 0);
            }
            
        } catch (Exception e) {
            // System.err.println("[Elemental Tools] Error spawning Lodestone VFX: " + e.getMessage());
        }
    }
}
