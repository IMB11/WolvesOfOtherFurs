package mine.block.woof.commands.actions;

import mine.block.woof.commands.DogCommand;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public class SitCommand implements DogCommand {

    @Override
    public void runServer(NbtCompound context, ServerWorld world, PlayerEntity master, WolfEntity target) {
        target.clearGoalsAndTasks();
        target.getNavigation().stop();
        boolean status = !target.isSitting();
        target.setSitting(status);
        target.setInSittingPose(status);
        target.setJumping(false);
        target.getNavigation().stop();
        target.setTarget(null);
    }

    @Override
    public void runClient(NbtCompound context, WolfEntity target) {
        boolean status = !target.isSitting();
        target.setInSittingPose(status);
    }

    @Override
    public Identifier getID() {
        return new Identifier("woof", "sit");
    }
}
