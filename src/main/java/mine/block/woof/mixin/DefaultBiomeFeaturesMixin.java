package mine.block.woof.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {
    @Inject(method = "addDesertMobs", at = @At("TAIL"))
    private static void addDesertMobs(SpawnSettings.@NotNull Builder builder, CallbackInfo ci) {
        builder.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(EntityType.WOLF, 4, 1, 2));
    }

    @Inject(method = "addPlainsMobs", at = @At("TAIL"))
    private static void addPlainsMobs(SpawnSettings.@NotNull Builder builder, CallbackInfo ci) {
        builder.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(EntityType.WOLF, 4, 1, 2));
    }

    @Inject(method = "addSnowyMobs", at = @At("TAIL"))
    private static void addSnowyMobs(SpawnSettings.@NotNull Builder builder, CallbackInfo ci) {
        builder.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(EntityType.WOLF, 4, 1, 2));
    }

    @Inject(method = "addBatsAndMonsters", at = @At("TAIL"))
    private static void addBMMobs(SpawnSettings.@NotNull Builder builder, CallbackInfo ci) {
        builder.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(EntityType.WOLF, 4, 1, 2));
    }
}
