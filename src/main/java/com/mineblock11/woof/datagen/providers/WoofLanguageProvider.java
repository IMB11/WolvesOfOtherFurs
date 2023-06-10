/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package com.mineblock11.woof.datagen.providers;

import com.mineblock11.woof.register.block.WoofBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import org.apache.commons.lang3.text.WordUtils;

public class WoofLanguageProvider extends FabricLanguageProvider {
    public WoofLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        WoofBlocks.DOG_BOWLS.forEach(((identifier, dogBowlBlock) -> {
            var value = WordUtils.capitalizeFully(identifier.getPath().replace("_", " "));
            translationBuilder.add(dogBowlBlock, value);
        }));

        WoofBlocks.DOG_BEDS.forEach(((identifier, dogBedBlock) -> {
            var value = WordUtils.capitalizeFully(identifier.getPath().replace("_", " "));
            translationBuilder.add(dogBedBlock, value);
        }));
    }
}
