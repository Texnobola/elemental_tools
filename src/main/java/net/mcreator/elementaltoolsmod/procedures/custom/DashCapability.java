package net.mcreator.elementaltoolsmod.procedures.custom;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;

import net.mcreator.elementaltoolsmod.network.SyncCustomDataMessage;

@Mod.EventBusSubscriber
public class DashCapability {

	public static final Capability<DashHolder> CAPABILITY = CapabilityManager.get(new CapabilityToken<DashHolder>() {
	});

	private static final ResourceLocation CAPABILITY_ID = ResourceLocation.fromNamespaceAndPath("elemental_tools_mod", "dash_data");

	public static class DashHolder {
		public double dashCooldown = 0.0D;

		public CompoundTag writeNBT() {
			CompoundTag nbt = new CompoundTag();
			nbt.putDouble("dash_cooldown", dashCooldown);
			return nbt;
		}

		public void readNBT(CompoundTag nbt) {
			dashCooldown = nbt.getDouble("dash_cooldown");
		}
	}

	private static class Provider implements ICapabilitySerializable<Tag> {
		private final DashHolder holder = new DashHolder();
		private final LazyOptional<DashHolder> instance = LazyOptional.of(() -> holder);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return cap == CAPABILITY ? instance.cast() : LazyOptional.empty();
		}

		@Override
		public Tag serializeNBT() {
			return holder.writeNBT();
		}

		@Override
		public void deserializeNBT(Tag nbt) {
			if (nbt instanceof CompoundTag compoundTag)
				holder.readNBT(compoundTag);
		}
	}

	@SubscribeEvent
	public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
		event.register(DashHolder.class);
	}

	@SubscribeEvent
	public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer)) {
			event.addCapability(CAPABILITY_ID, new Provider());
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event) {
		event.getOriginal().getCapability(CAPABILITY).ifPresent(oldCap -> {
			event.getEntity().getCapability(CAPABILITY).ifPresent(newCap -> {
				newCap.dashCooldown = oldCap.dashCooldown;
			});
		});
	}
}
