# Implementation Guide: Bloody Resurrection (Immortality)

This document details the step-by-step implementation of the **Bloody Resurrection** ability for the **Qonli Qilich** (Bloody Sword) in the Elemental Tools Mod.

---

## 1. Core Logic & Resurrection Mechanics
The ability intercepts the player's death event to prevent it and trigger the resurrection sequence.

- **File**: [BloodyResurrectionProcedure.java](file:///c:/Users/dev/MCreatorWorkspaces/elemental_tools_mod/src/main/java/net/mcreator/elementaltoolsmod/procedures/BloodyResurrectionProcedure.java)
- **Logic**:
    - Subscribes to `LivingDeathEvent`.
    - Checks if the player is holding a **Bloody Sword** and has consumed a **Bloody Can**.
    - Cancels the event (`event.setCanceled(true)`).
    - Restores full health and applies buffs (Regeneration, Absorption, Fire Resistance).
    - Triggers the 5-second stun timer.
    - Places **Blood Blocks** in a 10x10 radius.

## 2. Stun & Movement Lock
To create a cinematic "Dark Lord" moment, both the player and nearby entities are paralyzed.

- **File**: [ResurrectionStunHandler.java](file:///c:/Users/dev/MCreatorWorkspaces/elemental_tools_mod/src/main/java/net/mcreator/elementaltoolsmod/procedures/ResurrectionStunHandler.java)
- **Mechanism**:
    - **Client-side**: Uses `MovementInputUpdateEvent` to zero out WASD, Jump, and Sneak inputs.
    - **Server-side**: Ticks down `resurrection_stun_ticks` and forces horizontal velocity to zero.
    - **Entities**: Nearby mobs receive **Slowness 10** via [BloodyResurrectionProcedure.java](file:///c:/Users/dev/MCreatorWorkspaces/elemental_tools_mod/src/main/java/net/mcreator/elementaltoolsmod/procedures/BloodyResurrectionProcedure.java).

## 3. Sinister VFX (Lodestone)
The visuals focus on "liquid" and "fluid" blood rather than smoky clouds.

- **File**: [VFXHelper.java](file:///c:/Users/dev/MCreatorWorkspaces/elemental_tools_mod/src/main/java/net/mcreator/elementaltoolsmod/procedures/custom/VFXHelper.java)
- **Key Components**:
    - **Rising Droplets**: Sharp `SPARKLE` particles with gravity-defying motion.
    - **Blood Swirls**: Dense crimson spirals using `LUMITRANSPARENT` rendering.
    - **Shockwave**: A sharp burst of blood splatters on impact.

## 4. Custom Texture Integration (Ground Crack)
Integrating a custom external texture (`ground_crack.png`) into the Lodestone engine.

- **Registration**: [ElementalToolsModModParticles.java](file:///c:/Users/dev/MCreatorWorkspaces/elemental_tools_mod/src/main/java/net/mcreator/elementaltoolsmod/init/ElementalToolsModModParticles.java) defines the `GROUND_CRACK` particle type.
- **Client Binding**: [ClientParticleRegistry.java](file:///c:/Users/dev/MCreatorWorkspaces/elemental_tools_mod/src/main/java/net/mcreator/elementaltoolsmod/client/ClientParticleRegistry.java) registers the sprite set.
- **VFX Call**: `WorldParticleBuilder.create(ElementalToolsModModParticles.GROUND_CRACK.get())` in `VFXHelper.java` renders the flat, pulsing crack on the floor.

## 5. Sinister SFX
The ability is tied together with a unique soundscape.

- **File**: [sounds.json](file:///c:/Users/dev/MCreatorWorkspaces/elemental_tools_mod/src/main/resources/assets/elemental_tools_mod/sounds.json)
- **Execution**: The server plays `elemental_tools_mod:immortality_sfx` at the resurrection location, synced to all tracking players.

## 6. Persistence & HUD
- **Variables**: Added `has_consumed_bloody_can` and `resurrection_stun_ticks` to [ElementalToolsModModVariables.java](file:///c:/Users/dev/MCreatorWorkspaces/elemental_tools_mod/src/main/java/net/mcreator/elementaltoolsmod/network/ElementalToolsModModVariables.java).
- **HUD**: [SwordHUDRenderer.java](file:///c:/Users/dev/MCreatorWorkspaces/elemental_tools_mod/src/main/java/net/mcreator/elementaltoolsmod/client/SwordHUDRenderer.java) displays the "READY" or "SPENT" status based on these variables.
