/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package mine.block.woof.register.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.stream.Stream;

public class DogBedBlock extends HorizontalFacingBlock {
    private final Identifier parentWoodType;
    private final Identifier parentWoolType;

    public Identifier getParentWoodType() {
        return parentWoodType;
    }

    public Identifier getParentWoolType() {
        return parentWoolType;
    }

    public DogBedBlock(Settings settings, Identifier parentWoodType, Identifier parentWoolType) {
        super(settings);
        this.parentWoodType = parentWoodType;
        this.parentWoolType = parentWoolType;
    }

    public static VoxelShape rotateShape(Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, VoxelShapes.empty()};
        int times = (to.getHorizontal() - Direction.NORTH.getHorizontal() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.union(buffer[1], VoxelShapes.cuboid(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = VoxelShapes.empty();
        }

        return buffer[0];
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getPlayerFacing();
        return this.getDefaultState().with(FACING, direction);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return rotateShape(state.get(FACING).getOpposite(), Stream.of(
                Block.createCuboidShape(0, 0, 0, 2, 4, 16),
                Block.createCuboidShape(14, 0, 0, 16, 4, 16),
                Block.createCuboidShape(2, 0, 2, 14, 0, 14),
                Block.createCuboidShape(2, 0, 14, 14, 4, 16),
                Block.createCuboidShape(2, 0, 0, 14, 2, 2),
                Block.createCuboidShape(2, 0, 2, 14, 0.5, 14)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get());
    }
}
