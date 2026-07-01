
package net.mcreator.elementaltoolsmod.init;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;

import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

public class ElementalToolsModModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ElementalToolsModMod.MODID);

	public static final RegistryObject<SoundEvent> CRIT_WAVE_SFX = REGISTRY.register("crit_wave_sfx", 
		() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ElementalToolsModMod.MODID, "crit_wave_sfx")));
	public static final RegistryObject<SoundEvent> LIFESTEAL_SFX = REGISTRY.register("lifesteal_sfx", 
		() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ElementalToolsModMod.MODID, "lifesteal_sfx")));
	public static final RegistryObject<SoundEvent> BLOODY_DASH_SFX = REGISTRY.register("bloody_dash_sfx", 
		() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ElementalToolsModMod.MODID, "bloody_dash_sfx")));
	public static final RegistryObject<SoundEvent> IMMORTALITY_SFX = REGISTRY.register("immortality_sfx", 
		() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ElementalToolsModMod.MODID, "immortality_sfx")));

	public static void register(IEventBus bus) {
		REGISTRY.register(bus);
	}
}
