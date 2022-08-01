package mine.block.woof.mixin;

import mine.block.woof.SkinType;
import mine.block.woof.Woof;
import mine.block.woof.entity.DogEatOutBowlGoal;
import mine.block.woof.entity.DogSitOnBlockGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.CatSitOnBlockGoal;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin extends TameableEntity {
    protected WolfEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    private static final ArrayList<Integer> tickedAlreadyList = new ArrayList<>();

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick_InjectHead(CallbackInfo ci) {
        if(tickedAlreadyList.contains(getId())) return;
        tickedAlreadyList.add(getId());
        BlockPos pos = this.getBlockPos();
        RegistryEntry<Biome> biome = this.getEntityWorld().getBiome(pos);
        Identifier id = biome.getKey().get().getValue();

        if(id.toString().contains("taiga")) {
            this.dataTracker.set(Woof.WOLF_SKIN_TYPE, SkinType.TAIGA);
        } else if(id.toString().contains("snow") || id.toString().contains("frozen")) {
            this.dataTracker.set(Woof.WOLF_SKIN_TYPE, SkinType.SNOWY);
        } else if(id.toString().contains("grove") || id.toString().contains("stone")) {
            this.dataTracker.set(Woof.WOLF_SKIN_TYPE, SkinType.MOUNTAIN);
        } else if (id.toString().contains("desert") || id.toString().contains("badlands") || id.toString().contains("mesa")) {
            this.dataTracker.set(Woof.WOLF_SKIN_TYPE, SkinType.DESERT);
        } else {
            this.dataTracker.set(Woof.WOLF_SKIN_TYPE, SkinType.DEFAULT);
        }
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    public void initDataTracker_InjectTail(CallbackInfo ci) {
        this.dataTracker.startTracking(Woof.WOLF_SKIN_TYPE, SkinType.DEFAULT);
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    public void initGoals_InjectTail(CallbackInfo ci) {
        this.goalSelector.add(7, new DogSitOnBlockGoal((WolfEntity)(Object)this, 0.8));
        this.goalSelector.add(4, new DogEatOutBowlGoal((WolfEntity)(Object) this, 0.9));
    }

    @Shadow
    public abstract PassiveEntity createChild(ServerWorld world, PassiveEntity entity);
}
