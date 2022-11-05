/*
 * Copyright (C) 2022 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof.commands.actions;

import mine.block.woof.commands.DogCommand;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class BarkCommand implements DogCommand {
    @Override
    public void runServer(NbtCompound context, ServerWorld world, PlayerEntity master, WolfEntity target) {
        target.playSound(SoundEvents.ENTITY_WOLF_AMBIENT, 1f, 1f);
    }

    @Override
    public Identifier getID() {
        return new Identifier("woof", "bark");
    }
}
