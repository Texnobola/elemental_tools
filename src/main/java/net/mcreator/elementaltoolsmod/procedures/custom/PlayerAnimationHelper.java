package net.mcreator.elementaltoolsmod.procedures.custom;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(modid = "elemental_tools_mod", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PlayerAnimationHelper {

    public static final ResourceLocation ANIMATION_LAYER_ID = ResourceLocation.fromNamespaceAndPath("elemental_tools_mod", "animation_layer");

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
            ANIMATION_LAYER_ID,
            1000,
            (player) -> new ModifierLayer<IAnimation>()
        );
    }

    public static void playAnimation(AbstractClientPlayer player, String animationName) {
        if (player == null) return;
        
        var animation = PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("elemental_tools_mod", animationName));
        if (animation != null) {
            var animationData = PlayerAnimationAccess.getPlayerAssociatedData(player).get(ANIMATION_LAYER_ID);
            if (animationData instanceof ModifierLayer<?> rawLayer) {
                ModifierLayer<IAnimation> layer = (ModifierLayer<IAnimation>) rawLayer;
                layer.setAnimation(new KeyframeAnimationPlayer(animation));
            }
        }
    }
}
