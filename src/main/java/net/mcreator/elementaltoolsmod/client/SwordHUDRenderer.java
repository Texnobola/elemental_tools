package net.mcreator.elementaltoolsmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mcreator.elementaltoolsmod.ElementalToolsModMod;
import net.mcreator.elementaltoolsmod.init.ElementalToolsModModItems;
import net.mcreator.elementaltoolsmod.network.ElementalToolsModModVariables;

/**
 * SwordHUDRenderer — Qonli Qilich ability HUD, bottom-left corner.
 * Place at: src/main/java/net/mcreator/elementaltoolsmod/client/SwordHUDRenderer.java
 */
@Mod.EventBusSubscriber(
        modid = ElementalToolsModMod.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = Dist.CLIENT
)
public class SwordHUDRenderer {

    // ── Textures ──────────────────────────────────────────────────────────────
    private static final ResourceLocation ICON_LIFESTEAL =
            ResourceLocation.fromNamespaceAndPath(ElementalToolsModMod.MODID, "textures/screens/lifesteal_icon.png");
    private static final ResourceLocation ICON_DASH =
            ResourceLocation.fromNamespaceAndPath(ElementalToolsModMod.MODID, "textures/screens/dash_icon.png");
    private static final ResourceLocation ICON_CRIT =
            ResourceLocation.fromNamespaceAndPath(ElementalToolsModMod.MODID, "textures/screens/crit_wave_icon.png");
    private static final ResourceLocation ICON_IMMORTALITY =
            ResourceLocation.fromNamespaceAndPath(ElementalToolsModMod.MODID, "textures/screens/immortality_icon.png");
    private static final ResourceLocation ICON_LOCK =
            ResourceLocation.fromNamespaceAndPath(ElementalToolsModMod.MODID, "textures/screens/lock_icon.png");

    // ── Cooldown constants (ticks) ────────────────────────────────────────────
    private static final int LIFESTEAL_MAX = 600;   // 30s
    private static final int DASH_MAX      = 200;   // 10s
    private static final int CRIT_MAX      = 1200;  // 60s

    // ── Layout ────────────────────────────────────────────────────────────────
    private static final int PANEL_X      = 6;
    private static final int ICON_SIZE    = 14;
    private static final int BAR_W        = 72;
    private static final int BAR_H        = 4;
    private static final int ROW_H        = 26;   // icon + label + gap
    private static final int PAD          = 6;

    // ── Colours ───────────────────────────────────────────────────────────────
    private static final int C_PANEL_BG   = 0xCC0A0A0A;
    private static final int C_BORDER     = 0xFF2A2A2A;
    private static final int C_ACCENT     = 0xFFAA3300;  // dark red accent
    private static final int C_TITLE      = 0xFFFFCC44;  // warm gold
    private static final int C_STAGE      = 0xFFFF6622;  // orange stage number
    private static final int C_LABEL      = 0xFFCCCCCC;  // light grey labels
    private static final int C_READY      = 0xFF44FF88;  // bright green
    private static final int C_COOLING    = 0xFFFF7733;  // orange
    private static final int C_TIMER      = 0xFFFFDD44;  // yellow timer
    private static final int C_BAR_BG     = 0xFF1A1A1A;
    private static final int C_LOCKED_TXT = 0xFF666666;
    private static final int C_TOGGLE_ON  = 0xFF44FF88;
    private static final int C_TOGGLE_OFF = 0xFF884422;
    private static final int C_IMMORTAL   = 0xFFCC2222;

    // ─────────────────────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onRenderGui(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || mc.options.hideGui) return;

        ItemStack held = player.getMainHandItem();
        int stage = getSwordStage(held.getItem());
        if (stage == 0) return;

        ElementalToolsModModVariables.PlayerVariables vars = player
                .getCapability(ElementalToolsModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElseGet(ElementalToolsModModVariables.PlayerVariables::new);

        GuiGraphics gfx = event.getGuiGraphics();
        int screenH = mc.getWindow().getGuiScaledHeight();

        // ── Panel dimensions ─────────────────────────────────────────────────
        // 4 ability rows + title block + separator
        int panelW = PAD + ICON_SIZE + 4 + BAR_W + 44 + PAD;  // ~150px
int panelH = PAD + 18 + 10 + 2 + (ROW_H * 4) + PAD;   // title+xp+sep+rows

        int px = PANEL_X;
        int py = screenH - 22 - panelH - 3;  // just above hotbar

        // ── Panel background ─────────────────────────────────────────────────
        gfx.fill(px, py, px + panelW, py + panelH, C_PANEL_BG);

        // Left accent bar
        gfx.fill(px, py, px + 2, py + panelH, C_ACCENT);

        // Outer border (top + right + bottom only; left is the accent)
        gfx.fill(px + 2, py,              px + panelW, py + 1,              C_BORDER);
        gfx.fill(px + 2, py + panelH - 1, px + panelW, py + panelH,         C_BORDER);
        gfx.fill(px + panelW - 1, py,     px + panelW, py + panelH,         C_BORDER);

        int cx = px + PAD + 2;  // content x (after accent + padding)
        int cy = py + PAD;

        // ── Title block ──────────────────────────────────────────────────────
        gfx.drawString(mc.font, "QONLI QILICH", cx, cy, C_TITLE, false);
        // Stage on same line, right-aligned look
        String stageStr = "S" + stage;
        int stageX = px + panelW - PAD - mc.font.width(stageStr) - 2;
        gfx.drawString(mc.font, stageStr, stageX, cy, C_STAGE, false);
        cy += 10;

    // Sub-label: sword name in smaller muted text
gfx.drawString(mc.font, "Stage " + stage + " Sword", cx, cy, 0xFF886644, false);
cy += 10;

// XP display: shows accumulated sword_xp, right-aligned on its own line
String xpStr = "XP: " + (int) vars.sword_xp;
gfx.drawString(mc.font, xpStr, cx, cy, C_TIMER, false);
cy += 10;
        // Separator line
        gfx.fill(cx, cy, px + panelW - PAD, cy + 1, C_ACCENT);
        cy += 5;

        // ── Ability rows ─────────────────────────────────────────────────────

        // 1. LIFESTEAL (stage 2+)
        drawRow(gfx, mc, cx, cy, ICON_LIFESTEAL, "Lifesteal",
                stage >= 2, (int) vars.lifesteal_cooldown, LIFESTEAL_MAX,
                vars.lifesteal_toggle, false, false);
        cy += ROW_H;

        // 2. DASH (stage 4+)  — wire dash_cooldown here once variable exists
        int dashCd = 0; // TODO: replace with (int) vars.dash_cooldown
        drawRow(gfx, mc, cx, cy, ICON_DASH, "Dash",
                stage >= 4, dashCd, DASH_MAX,
                false, false, false);
        cy += ROW_H;

        // 3. CRIT WAVE (stage 5+)
        int critCd = 0; // TODO: replace with (int) vars.crit_cooldown
        drawRow(gfx, mc, cx, cy, ICON_CRIT, "Crit Wave",
                stage >= 5, critCd, CRIT_MAX,
                false, false, false);
        cy += ROW_H;

        // 4. IMMORTALITY (stage 7)
        boolean immortalReady = false; // TODO: replace with vars.immortality_ready
        drawRow(gfx, mc, cx, cy, ICON_IMMORTALITY, "Immortality",
                stage >= 7, 0, 0,
                false, true, immortalReady);
    }

    /**
     * Draws one ability row.
     *
     * Layout:
     *   [icon]  ABILITY NAME
     *           [===bar===]  12s / READY  [ON]
     *
     * @param hasToggle      true for lifesteal (shows ON/OFF indicator)
     * @param isImmortal     true for immortality (no bar, shows READY/SPENT)
     * @param immortalReady  only used when isImmortal=true
     */
    private static void drawRow(GuiGraphics gfx, Minecraft mc,
                                int x, int y,
                                ResourceLocation icon, String abilityName,
                                boolean unlocked,
                                int cooldown, int maxCooldown,
                                boolean toggleOn,
                                boolean isImmortal, boolean immortalReady) {

        int iconY = y + 1;
        int textX = x + ICON_SIZE + 5;
        int nameY = y;
        int barY  = y + 11;

        if (unlocked) {
            // Icon
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            gfx.blit(icon, x, iconY, 0f, 0f, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);

            // Ability name (white label)
            gfx.drawString(mc.font, abilityName, textX, nameY, C_LABEL, false);

            if (isImmortal) {
                // ── Immortality: text status only ─────────────────────────────
                if (immortalReady) {
                    gfx.drawString(mc.font, "READY", textX, barY, C_READY, false);
                } else {
                    gfx.drawString(mc.font, "SPENT", textX, barY, C_IMMORTAL, false);
                    gfx.drawString(mc.font, "(needs Blood Can)", textX + 36, barY, 0xFF885533, false);
                }

            } else {
                // ── Cooldown bar ──────────────────────────────────────────────
                // Bar background
                gfx.fill(textX, barY, textX + BAR_W, barY + BAR_H, C_BAR_BG);

                // Bar fill
                float progress = (maxCooldown > 0 && cooldown > 0)
                        ? 1f - Math.min(1f, (float) cooldown / maxCooldown)
                        : 1f;
                int fillW = Math.max(0, (int)(BAR_W * progress));
                int barColor = (cooldown <= 0) ? C_READY : C_COOLING;
                if (fillW > 0)
                    gfx.fill(textX, barY, textX + fillW, barY + BAR_H, barColor);

                // Bar border
                gfx.fill(textX,          barY,          textX + BAR_W,     barY + 1,         0x44FFFFFF);
                gfx.fill(textX,          barY + BAR_H - 1, textX + BAR_W,  barY + BAR_H,     0x44FFFFFF);

                // Status text (right of bar)
                int statusX = textX + BAR_W + 4;
                if (cooldown > 0) {
                    int secs = (int) Math.ceil(cooldown / 20.0);
                    gfx.drawString(mc.font, secs + "s", statusX, barY - 2, C_TIMER, false);
                } else {
                    gfx.drawString(mc.font, "READY", statusX, barY - 2, C_READY, false);
                }

                // Toggle indicator (only for lifesteal)
                if (mc.font.width(abilityName) < BAR_W - 10) { // only show if space
                    if (toggleOn) {
                        int toggleX = textX + mc.font.width(abilityName) + 4;
                        gfx.drawString(mc.font, "[ON]", toggleX, nameY, C_TOGGLE_ON, false);
                    } else if (cooldown == 0) {
                        // Only show OFF hint when not on cooldown
                        int toggleX = textX + mc.font.width(abilityName) + 4;
                        gfx.drawString(mc.font, "[OFF]", toggleX, nameY, C_TOGGLE_OFF, false);
                    }
                }
            }

        } else {
            // ── Locked row ────────────────────────────────────────────────────
            // Dim icon + lock overlay
            RenderSystem.setShaderColor(0.35f, 0.35f, 0.35f, 0.7f);
            gfx.blit(icon, x, iconY, 0f, 0f, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
            RenderSystem.setShaderColor(1f, 1f, 1f, 0.8f);
            gfx.blit(ICON_LOCK, x, iconY, 0f, 0f, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

            // Greyed-out name + LOCKED label
            gfx.drawString(mc.font, abilityName, textX, nameY, C_LOCKED_TXT, false);
            gfx.drawString(mc.font, "LOCKED", textX, barY, 0xFF444444, false);
        }
    }

    private static int getSwordStage(Item item) {
        if (item == ElementalToolsModModItems.QONLIQILICH_1.get()) return 1;
        if (item == ElementalToolsModModItems.QONLIQILICH_2.get()) return 2;
        if (item == ElementalToolsModModItems.QONLIQILICH_3.get()) return 3;
        if (item == ElementalToolsModModItems.QONLIQILICH_4.get()) return 4;
        if (item == ElementalToolsModModItems.QONLIQILICH_5.get()) return 5;
        if (item == ElementalToolsModModItems.QONLIQILICH_6.get()) return 6;
        if (item == ElementalToolsModModItems.QONLIQILICH_7.get()) return 7;
        return 0;
    }
}