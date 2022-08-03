package mine.block.woof.mixin;

import mine.block.woof.SkinType;
import mine.block.woof.Woof;
import mine.block.woof.entity.DogEatOutBowlGoal;
import mine.block.woof.entity.DogSitOnBlockGoal;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.CatSitOnBlockGoal;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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

    @Unique
    private int eatTick;

    @Unique
    private int hungerTick;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick_InjectHead(CallbackInfo ci) {
        if(tickedAlreadyList.contains(getId())) return;
        tickedAlreadyList.add(getId());
        BlockPos pos = this.getBlockPos();
        Biome biome = this.getEntityWorld().getBiome(pos).value();

        if(biome.isCold(pos)) {
            this.dataTracker.set(Woof.WOLF_SKIN_TYPE, SkinType.SNOWY);
        } else if(biome.getTemperature() < 0f) {
            this.dataTracker.set(Woof.WOLF_SKIN_TYPE, SkinType.TAIGA);
        } else if(biome.getTemperature() >= 0 && biome.getTemperature() <= 0.5f) {
            this.dataTracker.set(Woof.WOLF_SKIN_TYPE, SkinType.MOUNTAIN);
        } else if (biome.getTemperature() >= 1.5f) {
            this.dataTracker.set(Woof.WOLF_SKIN_TYPE, SkinType.DESERT);
        } else {
            this.dataTracker.set(Woof.WOLF_SKIN_TYPE, SkinType.DEFAULT);
        }
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void tickMovement_InjectHead(CallbackInfo ci) {
        if (!this.world.isClient && this.isAlive()) {
            if (this.hungerTick > 0) {
                this.hungerTick--;
            }

            ItemStack mainhand = this.getMainHandStack();

            if (!this.isUsingItem() && this.getMainHandStack().getItem().getFoodComponent() != null && this.getMainHandStack().getItem().getFoodComponent().isMeat()) {
                this.eatTick++;
                if (this.eatTick > 300) {
                    if (!mainhand.isEmpty()) {
                        this.setCurrentHand(Hand.MAIN_HAND);
                    }
                }
            } else {
                this.eatTick = 0;
            }
        }
    }

    @Override
    protected void consumeItem() {
        var hand = this.getActiveHand();
        if (this.activeItemStack.equals(this.getStackInHand(hand))) {
            if (!this.activeItemStack.isEmpty() && this.isUsingItem()) {
                ItemStack copy = this.activeItemStack.copy();

                if (copy.getItem().getFoodComponent() != null) {
                    this.heal(copy.getItem().getFoodComponent().getHunger());
                }
            }
        }
        super.consumeItem();
    }

    private void spitOutItem(ItemStack stack) {
        if (!stack.isEmpty() && !this.world.isClient) {
            ItemEntity itementity = new ItemEntity(this.world, this.getX() + this.getRotationVector().x, this.getY() + 1.0D, this.getZ() + this.getRotationVector().z, stack);
            itementity.setPickupDelay(40);
            itementity.setThrower(this.getUuid());
            this.playSound(SoundEvents.ENTITY_FOX_SPIT, 1.0F, 1.0F);
            this.world.spawnEntity(itementity);
        }
    }

    private void dropItemStack(ItemStack stack) {
        ItemEntity itementity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), stack);
        this.world.spawnEntity(itementity);
    }

    public boolean canHoldItem(ItemStack stack) {
        Item item = stack.getItem();
        ItemStack itemstack = this.getMainHandStack();
        return itemstack.isEmpty() && item.isFood() && item.getFoodComponent().isMeat();
    }

    @Override
    protected void loot(ItemEntity stack) {
        if (!this.isTamed()) {
            ItemStack itemstack = stack.getStack();
            if (this.canHoldItem(itemstack)) {
                int i = itemstack.getCount();
                if (i > 1) {
                    this.dropItemStack(itemstack.split(i - 1));
                }

                this.spitOutItem(this.getMainHandStack());
                this.triggerItemPickedUpByEntityCriteria(stack);
                this.equipStack(EquipmentSlot.MAINHAND, itemstack.split(1));
                this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 2.0F;
                this.sendPickup(stack, itemstack.getCount());
                stack.discard();
                this.eatTick = 0;
            }
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeCustomDataToNbt_InjectTail(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("hungerTick", hungerTick);
        nbt.putInt("eatTick", eatTick);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCustomDataFromNbt_InjectTail(NbtCompound nbt, CallbackInfo ci) {
        this.hungerTick = nbt.getInt("hungerTick");
        this.eatTick = nbt.getInt("eatTick");
        this.setCanPickUpLoot(true);
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
}
