/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package mine.block.woof.datagen.providers;

import com.google.common.hash.HashCode;
import mine.block.woof.register.block.WoofBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;

public class WoofBlockstateProvider implements DataProvider {
    private final FabricDataOutput output;

    public WoofBlockstateProvider(FabricDataOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return CompletableFuture.runAsync(() -> {
            WoofBlocks.DOG_BEDS.forEach((identifier, block) -> {
                String blockstateData = "{\n" +
                        "  \"variants\": {\n" +
                        "    \"facing=north\": {\n" +
                        "      \"model\": \"" + identifier + "\",\n" +
                        "      \"y\": 180\n" +
                        "    },\n" +
                        "    \"facing=east\": {\n" +
                        "      \"model\": \"" + identifier + "\",\n" +
                        "      \"y\": 270\n" +
                        "    },\n" +
                        "    \"facing=south\": {\n" +
                        "      \"model\": \"" + identifier + "\"\n" +
                        "    },\n" +
                        "    \"facing=west\": {\n" +
                        "      \"model\": \"" + identifier + "\",\n" +
                        "      \"y\": 90\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
                try {
                    writer.write(output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "blockstates").resolve(identifier, "json"), blockstateData.getBytes(Charset.defaultCharset()), HashCode.fromInt(blockstateData.hashCode()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            WoofBlocks.DOG_BOWLS.forEach((identifier, block) -> {
                String blockstateData = "{\n" +
                        "  \"variants\": {\n" +
                        "    \"filled=false\": { \"model\": \"" + identifier + "\" },\n" +
                        "    \"filled=true\": { \"model\": \"" + identifier + "_meat\" }\n" +
                        "  }\n" +
                        "}";
                try {
                    writer.write(output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "blockstates").resolve(identifier, "json"), blockstateData.getBytes(Charset.defaultCharset()), HashCode.fromInt(blockstateData.hashCode()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    @Override
    public String getName() {
        return "Blockstate Provider";
    }
}
