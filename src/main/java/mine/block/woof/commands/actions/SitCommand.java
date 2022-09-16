package mine.block.woof.commands.actions;

import mine.block.woof.commands.DogCommand;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public class SitCommand implements DogCommand {

    @Override
    public void run(NbtCompound context, ServerWorld world, PlayerEntity master, WolfEntity target) {
        target.clearGoalsAndTasks();
        target.setSitting(true);
        target.setInSittingPose(true);
    }

    @Override
    public Identifier getID() {
        return new Identifier("woof", "sit");
    }
}
