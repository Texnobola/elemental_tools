package net.mcreator.elementaltoolsmod.network;

import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.client.Minecraft;

import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ElementalToolsModModVariables {
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		ElementalToolsModMod.addNetworkMessage(SavedDataSyncMessage.class, SavedDataSyncMessage::buffer, SavedDataSyncMessage::new, SavedDataSyncMessage::handler);
		ElementalToolsModMod.addNetworkMessage(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::buffer, PlayerVariablesSyncMessage::new, PlayerVariablesSyncMessage::handler);
	}

	@SubscribeEvent
	public static void init(RegisterCapabilitiesEvent event) {
		event.register(PlayerVariables.class);
	}

	@Mod.EventBusSubscriber
	public static class EventBusVariableHandlers {
		@SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (!event.getEntity().level().isClientSide())
				((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElseGet(PlayerVariables::new)).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (!event.getEntity().level().isClientSide())
				((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElseGet(PlayerVariables::new)).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (!event.getEntity().level().isClientSide())
				((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElseGet(PlayerVariables::new)).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			event.getOriginal().revive();
			PlayerVariables original = ((PlayerVariables) event.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElseGet(PlayerVariables::new));
			PlayerVariables clone = ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElseGet(PlayerVariables::new));
			clone.lifesteal_toggle = original.lifesteal_toggle;
			clone.lifesteal_cooldown = original.lifesteal_cooldown;
			clone.steal_amount_1 = original.steal_amount_1;
			clone.steal_amount_2 = original.steal_amount_2;
			clone.steal_amount_3 = original.steal_amount_3;
			clone.steal_amount_4 = original.steal_amount_4;
			clone.steal_amount_5 = original.steal_amount_5;
			clone.steal_timer_1 = original.steal_timer_1;
			clone.steal_timer_2 = original.steal_timer_2;
			clone.steal_timer_3 = original.steal_timer_3;
			clone.steal_timer_4 = original.steal_timer_4;
			clone.steal_timer_5 = original.steal_timer_5;
			clone.sword_xp = original.sword_xp;
			if (!event.isWasDeath()) {
			}
		}

		@SubscribeEvent
		public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
			if (!event.getEntity().level().isClientSide()) {
				SavedData mapdata = MapVariables.get(event.getEntity().level());
				SavedData worlddata = WorldVariables.get(event.getEntity().level());
				if (mapdata != null)
					ElementalToolsModMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new SavedDataSyncMessage(0, mapdata));
				if (worlddata != null)
					ElementalToolsModMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new SavedDataSyncMessage(1, worlddata));
			}
		}

		@SubscribeEvent
		public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (!event.getEntity().level().isClientSide()) {
				SavedData worlddata = WorldVariables.get(event.getEntity().level());
				if (worlddata != null)
					ElementalToolsModMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new SavedDataSyncMessage(1, worlddata));
			}
		}
	}

	public static class WorldVariables extends SavedData {
		public static final String DATA_NAME = "elemental_tools_mod_worldvars";
		public double kill_count = 0;

		public static WorldVariables load(CompoundTag tag) {
			WorldVariables data = new WorldVariables();
			data.read(tag);
			return data;
		}

		public void read(CompoundTag nbt) {
			kill_count = nbt.getDouble("kill_count");
		}

		@Override
		public CompoundTag save(CompoundTag nbt) {
			nbt.putDouble("kill_count", kill_count);
			return nbt;
		}

		public void syncData(LevelAccessor world) {
			this.setDirty();
			if (world instanceof Level level && !level.isClientSide())
				ElementalToolsModMod.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(level::dimension), new SavedDataSyncMessage(1, this));
		}

		static WorldVariables clientSide = new WorldVariables();

		public static WorldVariables get(LevelAccessor world) {
			if (world instanceof ServerLevel level) {
				return level.getDataStorage().computeIfAbsent(e -> WorldVariables.load(e), WorldVariables::new, DATA_NAME);
			} else {
				return clientSide;
			}
		}
	}

	public static class MapVariables extends SavedData {
		public static final String DATA_NAME = "elemental_tools_mod_mapvars";

		public static MapVariables load(CompoundTag tag) {
			MapVariables data = new MapVariables();
			data.read(tag);
			return data;
		}

		public void read(CompoundTag nbt) {
		}

		@Override
		public CompoundTag save(CompoundTag nbt) {
			return nbt;
		}

		public void syncData(LevelAccessor world) {
			this.setDirty();
			if (world instanceof Level && !world.isClientSide())
				ElementalToolsModMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new SavedDataSyncMessage(0, this));
		}

		static MapVariables clientSide = new MapVariables();

		public static MapVariables get(LevelAccessor world) {
			if (world instanceof ServerLevelAccessor serverLevelAcc) {
				return serverLevelAcc.getLevel().getServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(e -> MapVariables.load(e), MapVariables::new, DATA_NAME);
			} else {
				return clientSide;
			}
		}
	}

	public static class SavedDataSyncMessage {
		private final int type;
		private SavedData data;

		public SavedDataSyncMessage(FriendlyByteBuf buffer) {
			this.type = buffer.readInt();
			CompoundTag nbt = buffer.readNbt();
			if (nbt != null) {
				this.data = this.type == 0 ? new MapVariables() : new WorldVariables();
				if (this.data instanceof MapVariables mapVariables)
					mapVariables.read(nbt);
				else if (this.data instanceof WorldVariables worldVariables)
					worldVariables.read(nbt);
			}
		}

		public SavedDataSyncMessage(int type, SavedData data) {
			this.type = type;
			this.data = data;
		}

		public static void buffer(SavedDataSyncMessage message, FriendlyByteBuf buffer) {
			buffer.writeInt(message.type);
			if (message.data != null)
				buffer.writeNbt(message.data.save(new CompoundTag()));
		}

		public static void handler(SavedDataSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer() && message.data != null) {
					if (message.type == 0)
						MapVariables.clientSide = (MapVariables) message.data;
					else
						WorldVariables.clientSide = (WorldVariables) message.data;
				}
			});
			context.setPacketHandled(true);
		}
	}

	public static final Capability<PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(new CapabilityToken<PlayerVariables>() {
	});

	@Mod.EventBusSubscriber
	private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
		@SubscribeEvent
		public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer))
				event.addCapability(ResourceLocation.fromNamespaceAndPath("elemental_tools_mod", "player_variables"), new PlayerVariablesProvider());
		}

		private final PlayerVariables playerVariables = new PlayerVariables();
		private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> playerVariables);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return cap == PLAYER_VARIABLES_CAPABILITY ? instance.cast() : LazyOptional.empty();
		}

		@Override
		public Tag serializeNBT() {
			return playerVariables.writeNBT();
		}

		@Override
		public void deserializeNBT(Tag nbt) {
			playerVariables.readNBT(nbt);
		}
	}

	public static class PlayerVariables {
		public boolean lifesteal_toggle = false;
		public double lifesteal_cooldown = 0;
		public double steal_amount_1 = 0;
		public double steal_amount_2 = 0;
		public double steal_amount_3 = 0;
		public double steal_amount_4 = 0;
		public double steal_amount_5 = 0;
		public double steal_timer_1 = 0;
		public double steal_timer_2 = 0;
		public double steal_timer_3 = 0;
		public double steal_timer_4 = 0;
		public double steal_timer_5 = 0;
		public double sword_xp = 0;

		public void syncPlayerVariables(Entity entity) {
			if (entity instanceof ServerPlayer serverPlayer)
				ElementalToolsModMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PlayerVariablesSyncMessage(this));
		}

		public Tag writeNBT() {
			CompoundTag nbt = new CompoundTag();
			nbt.putBoolean("lifesteal_toggle", lifesteal_toggle);
			nbt.putDouble("lifesteal_cooldown", lifesteal_cooldown);
			nbt.putDouble("steal_amount_1", steal_amount_1);
			nbt.putDouble("steal_amount_2", steal_amount_2);
			nbt.putDouble("steal_amount_3", steal_amount_3);
			nbt.putDouble("steal_amount_4", steal_amount_4);
			nbt.putDouble("steal_amount_5", steal_amount_5);
			nbt.putDouble("steal_timer_1", steal_timer_1);
			nbt.putDouble("steal_timer_2", steal_timer_2);
			nbt.putDouble("steal_timer_3", steal_timer_3);
			nbt.putDouble("steal_timer_4", steal_timer_4);
			nbt.putDouble("steal_timer_5", steal_timer_5);
			nbt.putDouble("sword_xp", sword_xp);
			return nbt;
		}

		public void readNBT(Tag tag) {
			CompoundTag nbt = (CompoundTag) tag;
			lifesteal_toggle = nbt.getBoolean("lifesteal_toggle");
			lifesteal_cooldown = nbt.getDouble("lifesteal_cooldown");
			steal_amount_1 = nbt.getDouble("steal_amount_1");
			steal_amount_2 = nbt.getDouble("steal_amount_2");
			steal_amount_3 = nbt.getDouble("steal_amount_3");
			steal_amount_4 = nbt.getDouble("steal_amount_4");
			steal_amount_5 = nbt.getDouble("steal_amount_5");
			steal_timer_1 = nbt.getDouble("steal_timer_1");
			steal_timer_2 = nbt.getDouble("steal_timer_2");
			steal_timer_3 = nbt.getDouble("steal_timer_3");
			steal_timer_4 = nbt.getDouble("steal_timer_4");
			steal_timer_5 = nbt.getDouble("steal_timer_5");
			sword_xp = nbt.getDouble("sword_xp");
		}
	}

	public static class PlayerVariablesSyncMessage {
		private final PlayerVariables data;

		public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
			this.data = new PlayerVariables();
			this.data.readNBT(buffer.readNbt());
		}

		public PlayerVariablesSyncMessage(PlayerVariables data) {
			this.data = data;
		}

		public static void buffer(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
			buffer.writeNbt((CompoundTag) message.data.writeNBT());
		}

		public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer()) {
					PlayerVariables variables = ((PlayerVariables) Minecraft.getInstance().player.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElseGet(PlayerVariables::new));
					variables.lifesteal_toggle = message.data.lifesteal_toggle;
					variables.lifesteal_cooldown = message.data.lifesteal_cooldown;
					variables.steal_amount_1 = message.data.steal_amount_1;
					variables.steal_amount_2 = message.data.steal_amount_2;
					variables.steal_amount_3 = message.data.steal_amount_3;
					variables.steal_amount_4 = message.data.steal_amount_4;
					variables.steal_amount_5 = message.data.steal_amount_5;
					variables.steal_timer_1 = message.data.steal_timer_1;
					variables.steal_timer_2 = message.data.steal_timer_2;
					variables.steal_timer_3 = message.data.steal_timer_3;
					variables.steal_timer_4 = message.data.steal_timer_4;
					variables.steal_timer_5 = message.data.steal_timer_5;
					variables.sword_xp = message.data.sword_xp;
				}
			});
			context.setPacketHandled(true);
		}
	}
}