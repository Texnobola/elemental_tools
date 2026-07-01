package net.mcreator.elementaltoolsmod.client;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.mcreator.elementaltoolsmod.init.ElementalToolsModModParticles;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientParticleRegistry {
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ElementalToolsModModParticles.GROUND_CRACK.get(), LodestoneWorldParticleType.Factory::new);
    }
}
