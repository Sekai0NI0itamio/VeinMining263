# Vein Mining — Modern Port for Minecraft 26.3 (Fabric)
![Mod loader: Fabric](https://img.shields.io/badge/modloader-Fabric-1976d2?style=for-the-badge)
![Minecraft: 26.3](https://img.shields.io/badge/Minecraft-26.3-green?style=for-the-badge)
![GitHub license](https://img.shields.io/github/license/Sekai0NI0itamio/VeinMining263?style=for-the-badge)

**Adds the Vein Mining enchantment**, which lets the enchanted tool
break matching connected blocks. Highly configurable in the original.

This is a **modern port for Minecraft 26.3 (Fabric)**.

> **Honest port status:** in 26.3 enchantments are datapack-driven
> records (`Enchantment` is a record, effects are data components),
> so the original's code-registered enchantment, mining logic, and
> networking need a substantial rewrite and are **not ported** in
> this loadable port. It loads on 26.3 and is verified by GitHub
> Actions including a headless runtime test. Reimplementing the
> enchantment on the datapack system is future work.

### Modern port notes (26.3)
- Java 25 · Fabric Loader 0.20.1+ · Fabric API 0.165.0+26.3
- Built and verified in CI via GitHub Actions, including a headless
  runtime test that launches the client, joins a world, and quits cleanly

### Installation
1. Install Fabric Loader for Minecraft 26.3
2. Install Fabric API
3. Drop `veinmining263-*.jar` into your `mods` folder

### Original mod & credit
Unofficial modern port by **Itamio** of **Vein Mining** by
**illusivesoulworks** (9.7M downloads, max 1.21, LGPL-3.0-only).
Original: https://modrinth.com/mod/vein-mining ·
https://github.com/illusivesoulworks/veinmining · See `NOTICE`.
