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
public class CheatCapability {

    public static final Capability<CheatHolder> CAPABILITY = CapabilityManager.get(new CapabilityToken<CheatHolder>() {
    });

    private static final ResourceLocation CAPABILITY_ID = ResourceLocation.fromNamespaceAndPath("elemental_tools_mod", "cheat_data");

    public static class CheatHolder {
        public boolean cooldownsDisabled = false;

        public CompoundTag writeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putBoolean("cooldowns_disabled", cooldownsDisabled);
            return nbt;
        }

        public void readNBT(CompoundTag nbt) {
            cooldownsDisabled = nbt.getBoolean("cooldowns_disabled");
        }
    }

    private static class Provider implements ICapabilitySerializable<Tag> {
        private final CheatHolder holder = new CheatHolder();
        private final LazyOptional<CheatHolder> instance = LazyOptional.of(() -> holder);

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
        event.register(CheatHolder.class);
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
                newCap.cooldownsDisabled = oldCap.cooldownsDisabled;
            });
        });
    }

    public static boolean areCooldownsDisabled(Entity entity) {
        return entity.getCapability(CAPABILITY, null).map(c -> c.cooldownsDisabled).orElse(false);
    }

    public static void setCooldownsDisabled(Entity entity, boolean value) {
        entity.getCapability(CAPABILITY, null).ifPresent(c -> {
            c.cooldownsDisabled = value;
            if (entity instanceof ServerPlayer serverPlayer) {
                SyncCustomDataMessage.send(serverPlayer);
            }
        });
    }
}
