/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package mine.block.woof.datagen;

import mine.block.woof.api.WoofAPI;
import mine.block.woof.datagen.providers.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class WoofDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();

        pack.addProvider(WoofModelProvider::new);
        pack.addProvider(WoofBlockstateProvider::new);
        pack.addProvider(WoofLanguageProvider::new);
        pack.addProvider(WoofTagProvider::new);
        pack.addProvider(WoofRecipeProvider::new);
        pack.addProvider(WoofLootProvider::new);
    }
}
