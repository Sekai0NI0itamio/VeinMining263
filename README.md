# Vein Mining — Fabric port for Minecraft 1.21.11
![Mod loader: Fabric](https://img.shields.io/badge/modloader-Fabric-1976d2?style=for-the-badge)
![Minecraft: 1.21.11](https://img.shields.io/badge/Minecraft-1.21.11-green?style=for-the-badge)
![GitHub license](https://img.shields.io/github/license/Sekai0NI0itamio/VeinMining263?style=for-the-badge)

**Adds the Vein Mining enchantment**, which lets the enchanted tool
break matching connected blocks. Highly configurable in the original.

This is a **full Fabric port for Minecraft 1.21.11** of
[Vein Mining](https://github.com/illusivesoulworks/veinmining) by
**illusivesoulworks**. The original vein-mining logic
(`VeinMiningLogic`, `BlockProcessor`, `BlockGroups`), block-break
events, mixins (drop relocation, tool damage), activation keybinding,
enchantment tooltips/warnings, and client/server state packets are
ported. Forge/NeoForge platform code and the SpectreLib dependency are
dropped: configuration is plain JSON at `config/veinmining.json` with
the same options and defaults as the original (the original's
SpectreLib GUI config is not included).

> **Enchantment:** in this version enchantments are datapack-driven, so the original `vein_mining` enchantment is provided as the original datapack JSON (`data/veinmining/enchantment/vein_mining.json` plus tags), exactly as upstream ships it.

### Port notes (1.21.11)
- Java 21 · Fabric Loader 0.19.3+ · Fabric API 0.141.4+1.21.11
- Built and verified in CI via GitHub Actions, including a headless
  runtime test that launches the client, joins a world, and quits cleanly

### Installation
1. Install Fabric Loader for Minecraft 1.21.11
2. Install Fabric API
3. Drop `veinmining263-*.jar` into your `mods` folder

### Original mod & credit
Unofficial port by **Itamio** of **Vein Mining** by
**illusivesoulworks** (9.7M downloads, max 1.21, LGPL-3.0-only).
Original: https://modrinth.com/mod/vein-mining ·
https://github.com/illusivesoulworks/veinmining · See `NOTICE`.
