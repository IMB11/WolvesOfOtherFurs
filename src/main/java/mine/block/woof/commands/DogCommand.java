package mine.block.woof.commands;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public interface DogCommand {
    @Environment(EnvType.SERVER)
    void run(NbtCompound context, ServerWorld world, PlayerEntity master, WolfEntity target);
    Identifier getID();
}
