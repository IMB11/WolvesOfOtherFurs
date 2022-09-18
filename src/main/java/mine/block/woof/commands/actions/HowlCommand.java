package mine.block.woof.commands.actions;

import mine.block.woof.commands.DogCommand;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class HowlCommand implements DogCommand {
    @Override
    public void runServer(NbtCompound context, ServerWorld world, PlayerEntity master, WolfEntity target) {
        target.playSound(SoundEvents.ENTITY_WOLF_HOWL, 1f, 1f);
    }

    @Override
    public Identifier getID() {
        return new Identifier("woof", "howl");
    }
}
