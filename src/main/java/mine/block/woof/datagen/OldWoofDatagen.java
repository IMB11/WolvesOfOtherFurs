/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

///*
// * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
// *
// * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
// */
//
//package mine.block.woof.datagen;
//
//import mine.block.woof.register.block.WoofBlocks;
//import net.devtech.arrp.api.RRPCallback;
//import net.devtech.arrp.api.RuntimeResourcePack;
//import net.devtech.arrp.json.loot.JLootTable;
//import net.devtech.arrp.json.models.JModel;
//import net.devtech.arrp.json.recipe.*;
//import net.fabricmc.api.ModInitializer;
//import net.minecraft.block.Block;
//import net.minecraft.util.Identifier;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Map;
//
//
//public class OldWoofDatagen implements ModInitializer {
//    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create("woof:arrp");
//
//    @Override
//    public void onInitialize() {
//        for (Map.Entry<String, Block> value : WoofBlocks.DOG_BEDS.entrySet()) {
//            if (!value.getKey().equals("White")) {
//                RESOURCE_PACK.addModel(JModel.model("woof:block/dog_bed")
//                                .textures(JModel.textures().var("1", "block/" + value.getKey() + "_wool")),
//                        new Identifier("woof:block/" + value.getKey() + "_dog_bed")
//                );
//
//                RESOURCE_PACK.addAsset(new Identifier("woof:blockstates/" + value.getKey() + "_dog_bed.json"), String.format("""
//                        {
//                          "variants": {
//                            "facing=north": {
//                              "model": "woof:block/%s_dog_bed",
//                              "y": 180
//                            },
//                            "facing=east": {
//                              "model": "woof:block/%s_dog_bed",
//                              "y": 270
//                            },
//                            "facing=south": {
//                              "model": "woof:block/%s_dog_bed"
//                            },
//                            "facing=west": {
//                              "model": "woof:block/%s_dog_bed",
//                              "y": 90
//                            }
//                          }
//                        }
//                        """, value.getKey(), value.getKey(), value.getKey(), value.getKey()).getBytes(StandardCharsets.UTF_8));
//            }
//
//            RESOURCE_PACK.addLootTable(new Identifier("woof:block/" + value.getKey() + "_dog_bed"), JLootTable.loot("minecraft:block")
//                    .pool(JLootTable.pool()
//                            .rolls(1)
//                            .entry(JLootTable.entry()
//                                    .type("minecraft:item")
//                                    .name("woof:" + value.getKey() + "_dog_bed"))
//                            .condition(JLootTable.condition("minecraft:survives_explosion"))));
//
//            RESOURCE_PACK.addModel(JModel.model().parent("woof:block/" + value.getKey() + "_dog_bed"), new Identifier("woof:item/" + value.getKey() + "_dog_bed"));
//
//            RESOURCE_PACK.addRecipe(new Identifier("woof:" + value.getKey() + "_dog_bed"), JRecipe.shaped(JPattern.pattern(
//                                    "   ",
//                                    "S_S",
//                                    "BBB"
//                            ), JKeys.keys()
//                                    .key("S", JIngredient.ingredient().tag("minecraft:wooden_slabs"))
//                                    .key("B", JIngredient.ingredient().tag("minecraft:planks"))
//                                    .key("_", JIngredient.ingredient().item("minecraft:" + value.getKey() + "_carpet")),
//                            JResult.result("woof:" + value.getKey() + "_dog_bed"))
//            );
//        }
//
//        RRPCallback.AFTER_VANILLA.register((re) -> {
//            re.add(RESOURCE_PACK);
//        });
//    }
//}
