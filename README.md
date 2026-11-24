# Sakura Lib

Sakura Lib is a Fabric library mod built around **developer convenience**: mod authors should be able to define their content once in code and get broad compatibility with other mods, datapacks, and Minecraft versions out of the box—without having to maintain repetitive boilerplate or per-mod compatibility layers themselves.

## Goals

1. **Everything in Code (Code-First Workflow)**
   - Mod developers should not have to juggle a mix of Java code and hand-maintained JSON asset/data files.
   - Blocks, items, models, blockstates, tags, recipes, loot tables, worldgen structures, and documentation should be defined declaratively in code alongside the gameplay logic.
2. **Maximum Compatibility Without Per-Mod Effort**
   - Dependent mods should automatically integrate with popular third-party mods (e.g. *Numismatic Overhaul* for villager trades, *Patchouli* for in-game documentation) without needing direct compile dependencies or custom compat code in every mod.
   - Content modifications (such as loot table injections or shared documentation books) should compose cleanly with other mods and user datapacks instead of overwriting files.
   - Version-stability abstractions should shield dependent mods from breaking Vanilla API changes across Minecraft versions wherever practical.
3. **Minimal Friction & Clean Architecture**
   - Keep dependent mods decoupled from underlying implementation details.
   - Prefer build-time generation (e.g. Fabric Data Generation API + Fabric Resource Conditions) for static and conditionally loaded resources, reserving runtime generation for genuinely dynamic, config-driven, or cross-mod data.

## Current Features

- **Code-First Data & Asset Generation (`datagen`, `arrp`):** Automatic generation of blockstates, models, tags, recipes, block loot tables, wood sets, worldgen structures, and dynamic `owo` rich translations.
- **Automatic Mod Integrations (`villager`, `datagen/v2/patchouli`):**
  - Villager trade helpers that automatically switch between *Numismatic Overhaul* currency and Vanilla emeralds/emerald blocks.
  - Shared dynamic *Patchouli* book API so mods can contribute documentation categories and entries without each shipping a separate book or hard-depending on Patchouli.
- **Conflict-Free Loot Injection (`loot/v2`):** Deferred, pool-targeted loot table injections with weight distribution helpers.
- **Version Stability (`version_stability`):** Thin compatibility wrappers bridging breaking Vanilla constructor/method changes across supported Minecraft versions.

## AI Usage Disclosure

This project is designed, directed, and maintained by a human developer. Generative AI tools have been used as a development aid **solely for writing and refactoring code** (reviewed and tested by the author).

Generative AI was **not** used to create any **images or textures, videos, or structures** (nor any icons, gallery media, or other visual/in-game assets).
