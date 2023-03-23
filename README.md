[![](https://github.com/mineblock11/mineblock11/blob/master/requires-mine11lib.png?raw=true)](https://www.curseforge.com/minecraft/mc-mods/mru)![](https://github.com/mineblock11/mineblock11/blob/master/fabric-api_64h.png?raw=true)[![](https://github.com/intergrav/devins-badges/blob/v2/assets/cozy/social/discord-plural_64h.png?raw=true)](https://discord.gg/UzHtJKqHny)

## Information

**Wolves Of Other Furs** is a mod that reworks the interaction between you - the player -and wolves.

The mod adds **variants** of the vanilla wolves - found in their corresponding biomes. There are currently 10 variants in Wolves Of Other Furs currently, with more planned for future updates.

W.O.O.F also adds **dog beds**, and **dog bowls**. Crafting recipes can be found in the recipe book or Just Enough Items (or whatever you use). **Leads are dyeable as well.**

Here are some common variants, you should explore to find the others!

![](https://github.com/mineblock11/mineblock11/blob/master/variants.png?raw=true)

## Screenshots

![](https://i.imgur.com/rdUz332.png)
![](https://i.imgur.com/u08M4Vw.png)

## Compatability

Wolves Of Other Furs supports the following mods:

- Oh The Biomes You'll Go!
- Wolves With Armor
- iChuns' Let Sleeping Dogs Lie

## Building From Source

Clone the repo and run the following:

```shell
gradlew build
```

Output jars should be in `./build/libs/`

## Using The API

You can use Wolves Of Other Furs in your projects by adding the following to `build.gradle`

```groovy
repositories {
    maven "https://maven.mineblock11.dev/releases"
}

dependencies {
    modImplementation "mine.block:woof:${woof_version}"
}
```

You can find the current `woof_version` [on the maven.](https://maven.mineblock11.dev/#/releases/mine/block/woof)