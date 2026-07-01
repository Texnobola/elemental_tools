# Elemental Tools Mod: Animation & VFX Development Guide

This manual details how to correctly set up, execute, and refine Player Animations and Lodestone VFX for the **Elemental Tools Mod** (Minecraft Forge 1.20.1).

---

## 1. Player Animations (playerAnimator)

We use the `player-animation-lib` for high-quality player model transformations.

### **File Setup**
- **Location**: `src/main/resources/assets/elemental_tools_mod/player_animation/`
- **Format**: `.json` (Blockbench exported for playerAnimator)
- **Key Bone**: Use the **`torso`** bone for full-body rotations or translations.

### **Technical Implementation**
Animations are handled via the [PlayerAnimationHelper.java](file:///src/main/java/net/mcreator/elementaltoolsmod/procedures/custom/PlayerAnimationHelper.java).

1. **Registration**: Layers are registered with a priority of `1000` to override vanilla movement.
2. **Execution**: Use the `getPlayerAssociatedData` system for stable, per-player playback.

**To play an animation in a procedure:**
```java
if (entity instanceof AbstractClientPlayer clientPlayer) {
    PlayerAnimationHelper.playAnimation(clientPlayer, "animation_file_name");
}
```

---

## 2. VFX Systems (Lodestone)

We use the **Lodestone VFX Engine** for high-performance, stationary world particles.

### **VFX Best Practices**
- **Stationary Rendering**: Ensure particles do not follow the player or camera rotation for a realistic world effect.
- **No More Clouds**: Avoid large `WISP` scales. Use thin, sharp particles for a "liquid" or "blade" look.
- **Physics**: Use `addMotion` with a downward Y-vector (e.g., `-0.05f`) to simulate dripping blood.

### **Adding New Effects**
New effects are defined in [VFXHelper.java](file:///src/main/java/net/mcreator/elementaltoolsmod/procedures/custom/VFXHelper.java).

1. **Define the Effect**: Create a private static method (e.g., `spawnMyNewEffect`).
2. **Choose Render Type**:
   - `LUMITRANSPARENT`: For thick, clumpy blood.
   - `ADDITIVE`: For bright, glistening highlights/streaks.
   - `TRANSPARENT`: For subtle mist or depth.
3. **Trigger**: Add a case to the `spawnVFX` method.

---

## 3. Networking (Server to Client)

Since VFX and Animations are **Client-Side only**, they must be triggered from the server using packets.

- **Animation Packet**: [PlayPlayerAnimationMessage.java](file:///src/main/java/net/mcreator/elementaltoolsmod/network/PlayPlayerAnimationMessage.java)
- **VFX Packet**: [SpawnVFXMessage.java](file:///src/main/java/net/mcreator/elementaltoolsmod/network/SpawnVFXMessage.java)

**Standard Procedure Flow:**
1. Server checks logic (cooldowns, sword stage).
2. Server sends packet to all tracking players:
   ```java
   ElementalToolsModMod.PACKET_HANDLER.send(
       PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> serverPlayer), 
       new PlayPlayerAnimationMessage(serverPlayer.getId(), "my_anim")
   );
   ```

---

## 4. Troubleshooting Checklist
- [ ] **Animation not playing?** Check if the `.json` is in `player_animation/` (NOT `animations/`).
- [ ] **Wrong bone moving?** Verify Blockbench bone names match the JSON (usually `torso`).
- [ ] **VFX covering screen?** Reduce particle `ScaleData` and lower the spawn `Y` coordinate.
- [ ] **Build failing?** Ensure `gradle.properties` has `org.gradle.jvmargs=-Xmx3G`.
