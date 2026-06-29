package net.mcreator.elementaltoolsmod.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;

import net.mcreator.elementaltoolsmod.network.ElementalToolsModModVariables;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class LifestealAttackProcedure {

    // FIX 1: correct namespace elemental_tools_mod, not mod
    private static final TagKey<Item> LIFESTEAL_TAG = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("elemental_tools_mod:qonli_qilich_lifesteal"));

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && event.getEntity() != null) {
            execute(event, event.getSource().getEntity());
        }
    }

    public static void execute(Entity sourceentity) {
        execute(null, sourceentity);
    }

    private static void execute(@Nullable Event event, Entity sourceentity) {
        if (sourceentity == null)
            return;

        // Read player variables once
        ElementalToolsModModVariables.PlayerVariables vars = sourceentity
                .getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElseGet(ElementalToolsModModVariables.PlayerVariables::new);

        // Get held item
        ItemStack heldItem = (sourceentity instanceof LivingEntity le)
                ? le.getMainHandItem() : ItemStack.EMPTY;

        // All conditions must pass
        boolean toggleOn     = vars.lifesteal_toggle;
        boolean holdingSword = heldItem.is(LIFESTEAL_TAG);
        boolean isPlayer     = ForgeRegistries.ENTITY_TYPES
                .getKey(sourceentity.getType()).toString().equals("minecraft:player");
        boolean offCooldown  = vars.lifesteal_cooldown <= 0;

        if (!toggleOn || !holdingSword || !isPlayer || !offCooldown)
            return;

        // FIX 2: get actual attack damage from the item's attribute, not getDamageValue()
        double attackDamage = 0;
        if (sourceentity instanceof LivingEntity le) {
            AttributeInstance attr = le.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attr != null)
                attackDamage = attr.getValue(); // includes base + all modifiers
        }

        // Heal the player
        if (sourceentity instanceof LivingEntity le) {
            float newHealth = le.getHealth() + (float) attackDamage;
            le.setHealth(Math.min(newHealth, le.getMaxHealth())); // cap at max HP
        }

        // Set cooldown
        final double healedAmount = attackDamage;
        sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .ifPresent(cap -> {
                    cap.lifesteal_cooldown = 600;
                    cap.syncPlayerVariables(sourceentity);
                });

        // FIX 3: steal slots are now INSIDE the main if block
        ElementalToolsModModVariables.PlayerVariables v = sourceentity
                .getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElseGet(ElementalToolsModModVariables.PlayerVariables::new);

        final double dmg = attackDamage;
        sourceentity.getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .ifPresent(cap -> {
                    if (cap.steal_timer_1 <= 0) {
                        cap.steal_amount_1 = dmg;
                        cap.steal_timer_1 = 5020;
                    } else if (cap.steal_timer_2 <= 0) {
                        cap.steal_amount_2 = dmg;
                        cap.steal_timer_2 = 5020;
                    } else if (cap.steal_timer_3 <= 0) {
                        cap.steal_amount_3 = dmg;
                        cap.steal_timer_3 = 5020;
                    } else if (cap.steal_timer_4 <= 0) {
                        cap.steal_amount_4 = dmg;
                        cap.steal_timer_4 = 5020;
                    } else if (cap.steal_timer_5 <= 0) {
                        cap.steal_amount_5 = dmg;
                        cap.steal_timer_5 = 5020;
                    } else {
                        cap.steal_timer_1 = 5020;
                    }
                    cap.syncPlayerVariables(sourceentity);
                });
    }
}