<div align="center">

<img src="https://cdn.modrinth.com/data/cached_images/878f02d73c5caa5506ec2486457b65d1eb199978.png" style="width: 50%;"><br>

-----
<h2>Middle-earth — Unofficial 26.2 Port</h2>
<p><b>This is an unofficial, community-maintained fork</b> of the <a href="https://github.com/Jukoz/middle-earth">Middle-earth mod</a>,
back-ported / migrated to <b>Minecraft 26.2 (Fabric)</b>.</p>
<p>It is <b>not</b> affiliated with, endorsed by, or supported by the original Middle-earth development team.
All credit for the original content goes to them — see <a href="#credits">Credits</a>.</p>

<a href="https://github.com/GaoXanSheng/middle-earth"><img src="https://img.shields.io/badge/unofficial%20fork-GaoXanSheng%2Fmiddle--earth-blue" alt="Unofficial fork"></a>
<img src="https://img.shields.io/badge/Minecraft-26.2%20only-62cd5c" alt="Minecraft 26.2">
<img src="https://img.shields.io/badge/Loader-Fabric-db7093" alt="Fabric">
<img src="https://img.shields.io/badge/status-beta-orange" alt="Beta">

</div>

-----

> [!WARNING]
> **This fork only supports Minecraft 26.2.** No other Minecraft version is or will be supported here.
> If you are looking for the official mod and its supported versions, visit the
> [official repository](https://github.com/Jukoz/middle-earth), [Modrinth](https://modrinth.com/mod/middle-earth)
> or [CurseForge](https://www.curseforge.com/minecraft/mc-mods/middle-earth).

## About

This mod brings the famous universe of Middle-earth, from J. R. R. Tolkien's work, into Minecraft.
You'll find a brand new dimension with custom blocks, items, entities, generation, and more.

This repository is a fork of the official source-available project, kept alive on the **26.2** version
of the game. Expect bugs, missing content, and differences compared to official releases — this is a
migration branch (`26.2-migration`), currently in **beta** (`1.0.2-26.2-beta`).

## Requirements & Installation

| | |
|---|---|
| Minecraft | **26.2** (only) |
| Mod loader | Fabric Loader **0.19.5** or newer |
| Dependencies | Fabric API **0.159.0+26.2** or newer |
| Java | Java 25 |

1. Install the [Fabric Loader](https://fabricmc.net/use/) for Minecraft 26.2.
2. Download this fork's jar from the [Releases](https://github.com/GaoXanSheng/middle-earth/releases) page (or build it yourself, see below).
3. Drop the jar, together with a matching Fabric API jar, into your `mods` folder.

## Building from source

Prerequisites: **JDK 25**.

```bash
git clone https://github.com/GaoXanSheng/middle-earth.git
cd middle-earth
./gradlew build
```

The project contains three modules:

| Module | Content |
|---|---|
| `middle-earth` | The main Middle-earth mod |
| `sevenstars-api` | Shared API/library code |
| `of-beasts-and-wild-things` | Companion creatures module |

Built jars end up in `<module>/build/libs/`. The main playable jar is
`middle-earth/build/libs/Middle-earth-<version>.jar`.

## Issues

Bugs related to the 26.2 migration (crashes, broken rendering, missing content) should be reported on
[this fork's issue tracker](https://github.com/GaoXanSheng/middle-earth/issues).
Bugs that also exist in the official mod are better reported upstream to the original team.

-----

## Credits

All original content of this mod was created by the official Middle-earth team.
This fork would not exist without their work.

### Developers
> - Jukoz
> - ObliviousCrab
> - Slooshyboi
> - TomSchlom

### Artists (Models/Textures)
> - Boenndal
> - Jooble
> - Jukoz
> - ObliviousCrab
> - Sindavar
> - Thijs
> - R3tt0

### Builders
> - Angmarzku
> - Arwaeneth
> - Boenndal
> - Jooble
> - Jukoz
> - ObliviousCrab
> - Slooshyboi
> - Thijs

### Contributors
> - Ag3ntCrab
> - Froosty11
> - Grandison
> - JB3
> - Khuz
> - nullBlade
> - Number_Sir
> - Python_200
> - Thorin_The_III
> - WorseNotePad

### Special Thanks
dylanhugh and Angmarzku for their ideas & arts for Gundabad and more.

### 26.2 port
Migration to Minecraft 26.2 is maintained by [GaoXanSheng](https://github.com/GaoXanSheng) and contributors in this repository.

-----

## License

The original mod content is under the **ARR** license (**All Rights Reserved**) — see [LICENSE](./LICENSE).
This unofficial fork does not change that: the code may not be reused without the original team's
written consent. Permission requests should go through the
[request template in the official repository](https://github.com/Jukoz/middle-earth/issues/new?assignees=&labels=request&projects=&template=code_use_permission_request.yml).

> **Please be aware that this project is a Minecraft Parody set in the Middle-earth universe and all rights are reserved under Tolkien domain.**
