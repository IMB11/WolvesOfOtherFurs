/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package com.mineblock11.woof.datagen;

import com.mineblock11.woof.datagen.providers.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class WoofDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();

        pack.addProvider(WoofModelProvider::new);
        pack.addProvider(WoofBlockstateProvider::new);
        pack.addProvider(WoofLanguageProvider::new);
        pack.addProvider(WoofTagProvider::new);
        pack.addProvider(WoofRecipeProvider::new);
        pack.addProvider(WoofBlockLootProvider::new);
    }
}
