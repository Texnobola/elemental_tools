package net.mcreator.elementaltoolsmod.init;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraft.core.particles.ParticleType;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

public class ElementalToolsModModParticles {
    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ElementalToolsModMod.MODID);

    public static final RegistryObject<LodestoneWorldParticleType> GROUND_CRACK = REGISTRY.register("ground_crack", LodestoneWorldParticleType::new);

    public static void register(IEventBus bus) {
        REGISTRY.register(bus);
    }
}
