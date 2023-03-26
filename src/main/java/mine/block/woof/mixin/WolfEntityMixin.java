/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package mine.block.woof.mixin;

import mine.block.woof.api.Variant;
import mine.block.woof.api.WoofAPI;
import mine.block.woof.api.WoofDogGoalCallback;
import mine.block.woof.entity.WolfVariantTracker;
import mine.block.woof.entity.WoofWolf;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin extends TameableEntity implements WoofWolf {
    Identifier variant;
    @Unique
    private int eatTick;
    @Unique
    private int hungerTick;

    @Unique
    private FollowMode followMode = FollowMode.FOLLOW;

    protected WolfEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void $woof_init(EntityType entityType, World world, CallbackInfo ci) {
        this.setCanPickUpLoot(true);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void $woof_tickMovement(CallbackInfo ci) {
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

    private void $woof_spitOutItem(ItemStack stack) {
        if (!stack.isEmpty() && !this.world.isClient) {
            ItemEntity itementity = new ItemEntity(this.world, this.getX() + this.getRotationVector().x, this.getY() + 1.0D, this.getZ() + this.getRotationVector().z, stack);
            itementity.setPickupDelay(40);
            itementity.setThrower(this.getUuid());
            this.playSound(SoundEvents.ENTITY_FOX_SPIT, 1.0F, 1.0F);
            this.world.spawnEntity(itementity);
        }
    }

    private void $woof_dropItemStack(ItemStack stack) {
        ItemEntity itementity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), stack);
        this.world.spawnEntity(itementity);
    }

    public boolean $woof_canHoldItem(ItemStack stack) {
        Item item = stack.getItem();
        ItemStack itemstack = this.getMainHandStack();
        return itemstack.isEmpty() && item.isFood() && item.getFoodComponent().isMeat();
    }

    @Override
    protected void loot(ItemEntity stack) {
        if (!this.isTamed()) {
            ItemStack itemstack = stack.getStack();
            if (this.$woof_canHoldItem(itemstack)) {
                int i = itemstack.getCount();
                if (i > 1) {
                    this.$woof_dropItemStack(itemstack.split(i - 1));
                }

                this.$woof_spitOutItem(this.getMainHandStack());
                this.triggerItemPickedUpByEntityCriteria(stack);
                this.equipStack(EquipmentSlot.MAINHAND, itemstack.split(1));
                this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 2.0F;
                this.sendPickup(stack, itemstack.getCount());
                stack.discard();
                this.eatTick = 0;
            }
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        BlockPos pos = this.getBlockPos();
        RegistryEntry<Biome> biome = this.getEntityWorld().getBiome(pos);

        for (Map.Entry<RegistryKey<Variant>, Variant> variantEntry : WoofAPI.VARIANT_REGISTRY.getEntrySet()) {
            if (variantEntry.getValue().identifier().getPath().equals("default")) continue;
            if (variantEntry.getValue().biomePredicate().apply(biome)) {
                this.dataTracker.set(WolfVariantTracker.VARIANT_TYPE, variantEntry.getValue().identifier());
                this.variant = variantEntry.getValue().identifier();
                return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
            }
        }

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void $woof_writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("hungerTick", hungerTick);
        nbt.putInt("eatTick", eatTick);

        if (this.variant == null) {
            this.variant = new Identifier("woof", "default");
        }

        nbt.putString("variant", this.variant.toString());
        nbt.putInt("followMode", this.followMode.ordinal());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void $woof_readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.hungerTick = nbt.getInt("hungerTick");
        this.eatTick = nbt.getInt("eatTick");
        if (nbt.contains("variant")) {
            this.variant = Identifier.tryParse(nbt.getString("variant"));
            this.dataTracker.set(WolfVariantTracker.VARIANT_TYPE, this.variant);
        } else {
            this.variant = new Identifier("woof", "default");
            this.dataTracker.set(WolfVariantTracker.VARIANT_TYPE, this.variant);
        }
        this.followMode = FollowMode.values()[nbt.getInt("followMode")];
        this.setCanPickUpLoot(true);
    }

    @Inject(method = "canSpawn", at = @At("HEAD"), cancellable = true)
    private static void $woof_canSpawn(EntityType<WolfEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    public void $woof_initDataTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(WolfVariantTracker.VARIANT_TYPE, new Identifier("woof", "null"));
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    public void $woof_initGoals(CallbackInfo ci) {
        WoofDogGoalCallback.EVENT.invoker().registerGoal(this.goalSelector, this.targetSelector, (WolfEntity) (Object) this);
    }

    @Override
    public FollowMode getFollowMode() {
        return this.followMode;
    }
}
