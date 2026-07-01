package net.mcreator.elementaltoolsmod.procedures.custom;

import net.minecraft.world.entity.LivingEntity;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CritSpinHelper {

    private static final Map<UUID, SpinState> ACTIVE = new ConcurrentHashMap<>();
    private static final Path DEBUG_LOG = Path.of("c:/Users/dev/MCreatorWorkspaces/elemental_tools_mod/debug-179cbb.log");

    private static class SpinState {
        int ticksRemaining;
        float degreesPerTick;

        SpinState(int ticksRemaining, float degreesPerTick) {
            this.ticksRemaining = ticksRemaining;
            this.degreesPerTick = degreesPerTick;
        }
    }

    public static void startSpin(LivingEntity entity, int durationTicks, float totalDegrees) {
        if (entity == null || durationTicks <= 0)
            return;
        float perTick = totalDegrees / durationTicks;
        ACTIVE.put(entity.getUUID(), new SpinState(durationTicks, perTick));
        // #region agent log
        debugLog("H1", "CritSpinHelper.startSpin", "crit spin started", Map.of(
                "entityId", entity.getId(),
                "durationTicks", durationTicks,
                "totalDegrees", totalDegrees,
                "degreesPerTick", perTick,
                "startYaw", entity.getYRot()));
        // #endregion
    }

    public static void tick(LivingEntity entity) {
        if (entity == null)
            return;
        SpinState state = ACTIVE.get(entity.getUUID());
        if (state == null)
            return;

        float beforeYaw = entity.getYRot();
        float newYaw = beforeYaw + state.degreesPerTick;
        entity.setYRot(newYaw);
        entity.setYHeadRot(newYaw);
        entity.yBodyRot = newYaw;
        entity.yBodyRotO = newYaw;
        state.ticksRemaining--;

        // #region agent log
        if (state.ticksRemaining % 5 == 0 || state.ticksRemaining <= 0) {
            debugLog("H2", "CritSpinHelper.tick", "crit spin tick", Map.of(
                    "entityId", entity.getId(),
                    "beforeYaw", beforeYaw,
                    "afterYaw", newYaw,
                    "ticksRemaining", state.ticksRemaining));
        }
        // #endregion

        if (state.ticksRemaining <= 0) {
            ACTIVE.remove(entity.getUUID());
            // #region agent log
            debugLog("H2", "CritSpinHelper.tick", "crit spin finished", Map.of(
                    "entityId", entity.getId(),
                    "finalYaw", newYaw));
            // #endregion
        }
    }

    public static boolean isSpinning(LivingEntity entity) {
        return entity != null && ACTIVE.containsKey(entity.getUUID());
    }

    // #region agent log
    private static void debugLog(String hypothesisId, String location, String message, Map<String, Object> data) {
        try {
            StringBuilder dataJson = new StringBuilder("{");
            boolean first = true;
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (!first)
                    dataJson.append(',');
                first = false;
                dataJson.append('"').append(entry.getKey()).append("\":");
                Object value = entry.getValue();
                if (value instanceof String s)
                    dataJson.append('"').append(s.replace("\"", "\\\"")).append('"');
                else
                    dataJson.append(value);
            }
            dataJson.append('}');
            String line = "{\"sessionId\":\"179cbb\",\"hypothesisId\":\"" + hypothesisId
                    + "\",\"location\":\"" + location + "\",\"message\":\"" + message
                    + "\",\"data\":" + dataJson + ",\"timestamp\":" + System.currentTimeMillis() + "}\n";
            Files.writeString(DEBUG_LOG, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception ignored) {
        }
    }
    // #endregion
}
