package net.mcreator.elementaltoolsmod.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;

import net.mcreator.elementaltoolsmod.procedures.CritWaveProcedure;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;

import java.util.function.Supplier;

public class CritWaveMessage {
	int type, pressedms;

	public CritWaveMessage(int type, int pressedms) {
		this.type = type;
		this.pressedms = pressedms;
	}

	public CritWaveMessage(FriendlyByteBuf buffer) {
		this.type = buffer.readInt();
		this.pressedms = buffer.readInt();
	}

	public static void buffer(CritWaveMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.type);
		buffer.writeInt(message.pressedms);
	}

	public static void handler(CritWaveMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			pressAction(context.getSender(), message.type, message.pressedms);
		});
		context.setPacketHandled(true);
	}

	public static void pressAction(Player entity, int type, int pressedms) {
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(entity.blockPosition()))
			return;
		if (type == 0) {
			CritWaveProcedure.execute(world, x, y, z, entity);
		}
	}

	public static void registerMessage(FMLCommonSetupEvent event) {
		ElementalToolsModMod.addNetworkMessage(CritWaveMessage.class, CritWaveMessage::buffer, CritWaveMessage::new, CritWaveMessage::handler);
	}
}
