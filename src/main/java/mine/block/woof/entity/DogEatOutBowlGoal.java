/*
 * Copyright (C) 2022 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof.entity;

import mine.block.woof.api.WoofDogGoal;
import mine.block.woof.register.block.DogBowlBlock;
import mine.block.woof.register.block.WoofBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class DogEatOutBowlGoal extends MoveToTargetPosGoal implements WoofDogGoal {
    private final TameableEntity wolf;

    public DogEatOutBowlGoal(WolfEntity mob, double speed) {
        super(mob, speed, 16);
        this.wolf = mob;
    }

    @Override
    public boolean canStart() {
        return !this.wolf.isSitting() && super.canStart();
    }

    @Override
    public void tick() {
        super.tick();
        if (hasReached()) {
            if (!wolf.getWorld().getBlockState(targetPos).isOf(WoofBlocks.DOG_BOWL_BLOCK)) return;
            BlockState state = wolf.getWorld().getBlockState(targetPos).with(DogBowlBlock.FILLED, false);
            wolf.getWorld().setBlockState(targetPos, state);
            wolf.getWorld().playSound(wolf.getX(), wolf.getY(), wolf.getZ(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1f, 1f, true);
        }
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return world.getBlockState(pos).isOf(WoofBlocks.DOG_BOWL_BLOCK) && world.getBlockState(pos).get(DogBowlBlock.FILLED);
    }

    @Override
    public int getPriority() {
        return 7;
    }
}
