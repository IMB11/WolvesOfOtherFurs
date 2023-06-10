/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package com.mineblock11.woof.register.block;

import com.mineblock11.woof.Woof;
import com.mineblock11.woof.entity.DogEatOutBowlGoal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class DogBowlBlock extends Block {
    public static Property<Boolean> FILLED = BooleanProperty.of("filled");

    private final Identifier parentWoodType;

    public Identifier getParentWoodType() {
        return parentWoodType;
    }

    public DogBowlBlock(Settings settings, Identifier parentWoodType) {
        super(settings);
        this.parentWoodType = parentWoodType;
        setDefaultState(getDefaultState().with(FILLED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FILLED);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).isIn(Woof.MEATS)) {
            world.setBlockState(pos, state.with(FILLED, true));
            player.getStackInHand(hand).decrement(1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(world.isClient) return;
        if (entity instanceof WolfEntity wolf) {
            wolf.clearGoals(goal -> goal instanceof DogEatOutBowlGoal);
            world.setBlockState(pos, state.with(FILLED, false));
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1f, 1.4f, false);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return Stream.of(
                Block.createCuboidShape(3, 1, 3, 4, 3, 13),
                Block.createCuboidShape(3, 0, 3, 13, 1, 13),
                Block.createCuboidShape(12, 1, 3, 13, 3, 13),
                Block.createCuboidShape(4, 1, 3, 12, 3, 4),
                Block.createCuboidShape(4, 1, 12, 12, 3, 13)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }
}
