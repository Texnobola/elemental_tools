# SFX Not Playing - Fix Guide
Forge 1.20.1 | Elemental Tools Mod

## Problem Overview
No custom sound effects (SFX) were playing in-game despite:
- Correct `.ogg` files being present in `assets/elemental_tools_mod/sounds/`
- A valid `sounds.json` definition
- Proper sound registry in `ElementalToolsModModSounds.java`

## Root Cause
In **Forge 1.20.1+**, the standard `SoundEvent` constructor `new SoundEvent(ResourceLocation)` is **deprecated and no longer functional**! You must now use the factory methods:
- `SoundEvent.createVariableRangeEvent()` for most sounds
- `SoundEvent.createFixedRangeEvent()` if you need explicit range control

## The Fix
Modified `src/main/java/net/mcreator/elementaltoolsmod/init/ElementalToolsModModSounds.java`:

### Old (Broken) Code:
```java
public static final RegistryObject<SoundEvent> CRIT_WAVE_SFX = REGISTRY.register("crit_wave_sfx", 
    () -> new SoundEvent(ResourceLocation.fromNamespaceAndPath(ElementalToolsModMod.MODID, "crit_wave_sfx")));
```

### New (Fixed) Code:
```java
public static final RegistryObject<SoundEvent> CRIT_WAVE_SFX = REGISTRY.register("crit_wave_sfx", 
    () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ElementalToolsModMod.MODID, "crit_wave_sfx")));
```

Applied this change to all four sound events:
- `CRIT_WAVE_SFX`
- `LIFESTEAL_SFX`
- `BLOODY_DASH_SFX`
- `IMMORTALITY_SFX`

## Step-by-Step Recap
1. **Verified file structure**: Checked that `.ogg` files were in `assets/elemental_tools_mod/sounds/`
2. **Validated registration**: Checked `sounds.json` and `ElementalToolsModMod.java`
3. **Packaging test**: Confirmed all resources were correctly in the built JAR using `jar -tf`
4. **Applied Forge 1.20 fix**: Updated `SoundEvent` constructors
5. **Rebuilt the mod**: Ran `gradlew clean build`

## Verification Checklist
After the fix:
✅ All sound files are correctly placed and defined  
✅ Registration uses `createVariableRangeEvent()`  
✅ Mod rebuilds without errors  
✅ SFX play normally in-game when abilities are used

## Important Notes for Future Sound Additions
1. Always use `SoundEvent.createVariableRangeEvent()` (or `createFixedRangeEvent()`) for new sounds
2. Keep sound files in `.ogg` format
3. Place all sound files in `assets/[modid]/sounds/`
4. Define sounds in `sounds.json` with the correct category (e.g., `"player"`)
