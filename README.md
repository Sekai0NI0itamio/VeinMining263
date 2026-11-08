# Vein Mining — Forge Port for Minecraft 1.20.1
![Mod loader: Forge](https://img.shields.io/badge/modloader-Forge-orange?style=for-the-badge)
![Minecraft: 1.20.1](https://img.shields.io/badge/Minecraft-1.20.1-green?style=for-the-badge)
![GitHub license](https://img.shields.io/github/license/Sekai0NI0itamio/VeinMining263?style=for-the-badge)

**Adds the Vein Mining enchantment**, which lets the enchanted tool
break matching connected blocks. Highly configurable: users edit
`config/veinmining.json` (created with defaults on first launch).

This is a **Forge port for Minecraft 1.20.1** (Forge 47.4.20).

### Port notes (1.20.1)
- Full original logic ported: code-registered enchantment (still
  possible in 1.20.1, before datapack enchantments in 1.20.5),
  vein-mining BFS logic, block groups datapack listener, tool
  damage / exhaustion / drop relocation mixins, client key
  activation + tooltips, and Forge SimpleChannel networking
  (state + notify packets).
- Java 17 · ForgeGradle [6.0,6.2) · official Mojang mappings
- Built and verified in CI via GitHub Actions, including a headless
  runtime test that launches the client, joins a world, and quits cleanly

### Installation
1. Install Forge 47.4.20 for Minecraft 1.20.1
2. Drop `veinmining-1.20.1-forge-*.jar` into your `mods` folder

### Original mod & credit
Unofficial Forge port by **Itamio** of **Vein Mining** by
**illusivesoulworks** (9.7M downloads, LGPL-3.0-only).
Original: https://modrinth.com/mod/vein-mining ·
https://github.com/illusivesoulworks/veinmining · See `NOTICE`.
