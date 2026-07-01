package net.mcreator.elementaltoolsmod.procedures.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.mcreator.elementaltoolsmod.network.ElementalToolsModModVariables;

@Mod.EventBusSubscriber
public class ElementalToolsCheatCommand {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("etools")
            .requires(s -> s.hasPermission(2)) // Require level 2 permission (cheat level)
            .then(Commands.literal("cooldown")
                .then(Commands.argument("enabled", BoolArgumentType.bool())
                    .executes(context -> {
                        boolean enabled = BoolArgumentType.getBool(context, "enabled");
                        ServerPlayer player = context.getSource().getPlayerOrException();
                        CheatCapability.setCooldownsDisabled(player, enabled);
                        context.getSource().sendSuccess(() -> Component.literal("Cooldowns bypass: " + (enabled ? "ON" : "OFF")), true);
                        return 1;
                    })
                )
            )
            .then(Commands.literal("xp")
                .then(Commands.literal("set")
                    .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0))
                        .executes(context -> {
                            double amount = DoubleArgumentType.getDouble(context, "amount");
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            player.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(c -> {
                                c.sword_xp = amount;
                                c.syncPlayerVariables(player);
                            });
                            context.getSource().sendSuccess(() -> Component.literal("Sword XP set to: " + amount), true);
                            return 1;
                        })
                    )
                )
                .then(Commands.literal("add")
                    .then(Commands.argument("amount", DoubleArgumentType.doubleArg())
                        .executes(context -> {
                            double amount = DoubleArgumentType.getDouble(context, "amount");
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            player.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(c -> {
                                c.sword_xp += amount;
                                c.syncPlayerVariables(player);
                            });
                            context.getSource().sendSuccess(() -> Component.literal("Added " + amount + " to Sword XP"), true);
                            return 1;
                        })
                    )
                )
            )
        );
    }
}
