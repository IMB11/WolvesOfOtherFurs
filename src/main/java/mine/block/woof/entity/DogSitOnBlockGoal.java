/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof.entity;

import mine.block.woof.register.block.DogBedBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class DogSitOnBlockGoal extends MoveToTargetPosGoal {
    private final WolfEntity wolf;

    public DogSitOnBlockGoal(WolfEntity wolf, double speed) {
        super(wolf, speed, 8);
        this.wolf = wolf;
    }

    public boolean canStart() {
        return this.wolf.isTamed() && !this.wolf.isSitting() && super.canStart();
    }

    public boolean shouldContinue() {
        return this.wolf.isSitting();
    }

    public void stop() {
        super.stop();
        this.wolf.setInSittingPose(false);
    }

    public void tick() {
        super.tick();
        if (hasReached()) {
            this.wolf.getNavigation().stop();
            this.wolf.setInSittingPose(true);
        }
    }

    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        if (!world.isAir(pos.up())) {
            return false;
        } else {
            BlockState blockState = world.getBlockState(pos);
            return blockState.isOf(Blocks.FURNACE) && blockState.get(FurnaceBlock.LIT)
                    || blockState.isIn(BlockTags.WOOL_CARPETS)
                    || blockState.isIn(BlockTags.BEDS, (state) -> state.getOrEmpty(BedBlock.PART).map((part) -> part != BedPart.HEAD).orElse(true))
                    || blockState.getBlock() instanceof DogBedBlock;
        }
    }
}
