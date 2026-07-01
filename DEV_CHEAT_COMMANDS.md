# Dev/Cheat Commands Guide
Elemental Tools Mod | Forge 1.20.1

## Overview
A new command `/etools` has been added to assist with development, testing, and player convenience. These commands require **Level 2 (Cheat) Permissions** to execute.

## Command Syntax

### 1. Cooldown Bypass
Toggles the ability to ignore all ability cooldowns (Dash, Crit Wave, Lifesteal).
- **Command**: `/etools cooldown <true|false>`
- **Example**: `/etools cooldown true` (Enables instant ability spam)
- **Effect**:
  - Removes the 15s delay on **Crit Wave**.
  - Removes the 10s delay on **Dash**.
  - Removes the 30s delay on **Heart Steal (Lifesteal)**.

### 2. Sword XP Management
Allows direct manipulation of the `sword_xp` variable for testing progression and sword stages.
- **Set XP**: `/etools xp set <amount>`
  - **Example**: `/etools xp set 5000` (Sets XP to exactly 5000)
- **Add XP**: `/etools xp add <amount>`
  - **Example**: `/etools xp add 100` (Adds 100 XP to the current total)

## Technical Implementation Details

### Files Involved
- [CheatCapability.java](file:///c:/Users/dev/MCreatorWorkspaces/elemental_tools_mod/src/main/java/net/mcreator/elementaltoolsmod/procedures/custom/CheatCapability.java): Stores the `cooldownsDisabled` persistent flag.
- [ElementalToolsCheatCommand.java](file:///c:/Users/dev/MCreatorWorkspaces/elemental_tools_mod/src/main/java/net/mcreator/elementaltoolsmod/procedures/custom/ElementalToolsCheatCommand.java): Defines the command logic and subcommands.
- [SyncCustomDataMessage.java](file:///c:/Users/dev/MCreatorWorkspaces/elemental_tools_mod/src/main/java/net/mcreator/elementaltoolsmod/network/SyncCustomDataMessage.java): Handles syncing the cheat flag between Server and Client.

### How it works
1. **Capability**: A new `CheatCapability` is attached to every player. It survives death and dimension changes.
2. **Procedure Injection**:
   - `CritWaveProcedure`, `DashProcedure`, and `HeartStealHandler` now check `CheatCapability.areCooldownsDisabled(player)` before enforcing their respective timers.
   - If `true`, they execute the logic regardless of the current cooldown value.
3. **Syncing**: When the command is run on the server, the new value is immediately synced to the client's version of the capability to ensure consistency (e.g., for future HUD indicators).

## Permission Level
- The command uses `.requires(s -> s.hasPermission(2))`, meaning it is only available to Operators (OPs) or players with cheats enabled in Single Player.
