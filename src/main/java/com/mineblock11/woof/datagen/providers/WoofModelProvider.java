/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package com.mineblock11.woof.datagen.providers;

import com.google.common.hash.HashCode;
import com.mineblock11.woof.register.block.WoofBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;

public class WoofModelProvider implements DataProvider {
    private final FabricDataOutput output;

    public WoofModelProvider(FabricDataOutput output) {
        this.output = output;
    }

//    @Override
//    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
//        WoofBlocks.DOG_BEDS.forEach((identifier, block) -> {
//            TextureMap map = new TextureMap();
//            map.put(TextureKey.of("0"), block.getParentWoodType().withPrefixedPath("block/"));
//            map.put(TextureKey.of("1"), block.getParentWoolType().withPrefixedPath("block/"));
//            map.put(TextureKey.PARTICLE, block.getParentWoodType().withPrefixedPath("block/"));
//
//            Model model = new Model(Optional.of(new Identifier("woof", "block/dog_bed")), Optional.empty(), TextureKey.of("0"), TextureKey.of("1"), TextureKey.of("particle"));
//            Identifier modelID = model.upload(block, map, blockStateModelGenerator.modelCollector);
//
//            blockStateModelGenerator.registerParentedItemModel(block, modelID);
//
//            blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(block)
//                    .with(When.create()
//                            .set(DogBedBlock.FACING, Direction.NORTH),
//                          BlockStateVariant.create()
//                                  .put(VariantSettings.Y, VariantSettings.Rotation.R180)
//                                  .put(VariantSettings.MODEL, modelID)
//                    ).with(When.create()
//                            .set(DogBedBlock.FACING, Direction.EAST),
//                            BlockStateVariant.create()
//                                    .put(VariantSettings.Y, VariantSettings.Rotation.R270)
//                                    .put(VariantSettings.MODEL, modelID)
//                    ).with(When.create()
//                            .set(DogBedBlock.FACING, Direction.SOUTH),
//                            BlockStateVariant.create().put(VariantSettings.MODEL, modelID)
//                    ).with(When.create()
//                            .set(DogBedBlock.FACING, Direction.WEST),
//                            BlockStateVariant.create()
//                                    .put(VariantSettings.Y, VariantSettings.Rotation.R90)
//                                    .put(VariantSettings.MODEL, modelID)
//                    )
//            );
//        });
//    }

//    @Override
//    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
//        return;
//    }

    private String createItemModel(Identifier id) {
        return "{\n" +
                "  \"parent\": \"" + id + "\"\n" +
                "}";
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return CompletableFuture.runAsync(() -> {
            WoofBlocks.DOG_BEDS.forEach((identifier, block) -> {
                var _0 = block.getParentWoodType().withPrefixedPath("block/");
                var _1 = block.getParentWoolType().withPrefixedPath("block/");
                var particle =  block.getParentWoodType().withPrefixedPath("block/");

                String model = "{\n" +
                        "\t\"parent\": \"woof:block/dog_bed\",\n" +
                        "\t\"textures\": {\n" +
                        "\t\t\"0\": \"" + _0 + "\",\n" +
                        "\t\t\"1\": \"" + _1 + "\",\n" +
                        "\t\t\"particle\": \"" + particle + "\"\n" +
                        "\t}\n" +
                        "}";

                String item = createItemModel(identifier.withPrefixedPath("block/"));

                try {
                    writer.write(this.output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "models").resolve(identifier.withPrefixedPath("block/"), "json"), model.getBytes(Charset.defaultCharset()), HashCode.fromInt(model.hashCode()));
                    writer.write(this.output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "models").resolve(identifier.withPrefixedPath("item/"), "json"), item.getBytes(Charset.defaultCharset()), HashCode.fromInt(item.hashCode()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            WoofBlocks.DOG_BOWLS.forEach((identifier, block) -> {
                var _0 = block.getParentWoodType().withPrefixedPath("block/");
                var particle =  block.getParentWoodType().withPrefixedPath("block/");

                String modelEmpty = "{\n" +
                        "\t\"parent\": \"woof:block/dog_bowl_block\",\n" +
                        "\t\"textures\": {\n" +
                        "\t\t\"0\": \"" + _0 + "\",\n" +
                        "\t\t\"particle\": \"" + particle + "\"\n" +
                        "\t}\n" +
                        "}";

                String modelFull =" {\n" +
                        "\t\"parent\": \"woof:block/dog_bowl_block_meat\",\n" +
                        "\t\"textures\": {\n" +
                        "\t\t\"0\": \"" + _0 + "\",\n" +
                        "\t\t\"particle\": \"" + particle + "\"\n" +
                        "\t}\n" +
                        "}";

                String item = createItemModel(identifier.withPrefixedPath("block/"));

                Identifier meatId = new Identifier(identifier.getNamespace(), identifier.withPrefixedPath("block/").getPath() + "_meat");

                try {
                    writer.write(this.output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "models").resolve(identifier.withPrefixedPath("block/"), "json"), modelEmpty.getBytes(Charset.defaultCharset()), HashCode.fromInt(modelEmpty.hashCode()));
                    writer.write(this.output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "models").resolve(meatId, "json"), modelFull.getBytes(Charset.defaultCharset()), HashCode.fromInt(modelFull.hashCode()));
                    writer.write(this.output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "models").resolve(identifier.withPrefixedPath("item/"), "json"), item.getBytes(Charset.defaultCharset()), HashCode.fromInt(item.hashCode()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    @Override
    public String getName() {
        return "Model Provider";
    }
}
