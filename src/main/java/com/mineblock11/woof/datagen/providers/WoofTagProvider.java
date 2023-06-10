/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package com.mineblock11.woof.datagen.providers;

import com.mineblock11.woof.register.block.WoofBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class WoofTagProvider extends FabricTagProvider.BlockTagProvider {
    public WoofTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        var bedsTag = getOrCreateTagBuilder(WoofBlocks.DOG_BEDS_TAG);
        WoofBlocks.DOG_BEDS.values().forEach(bedsTag::add);
        var bowlsTag = getOrCreateTagBuilder(WoofBlocks.DOG_BOWLS_TAG);
        WoofBlocks.DOG_BOWLS.values().forEach(bowlsTag::add);
    }
}
