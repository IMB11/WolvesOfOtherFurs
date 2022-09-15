package mine.block.woof.register.block;

import mine.block.woof.Woof;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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

    public DogBowlBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FILLED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FILLED);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(player.getStackInHand(hand).isIn(Woof.MEATS)) {
            world.setBlockState(pos, state.with(FILLED, true));
            player.getStackInHand(hand).decrement(1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
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
