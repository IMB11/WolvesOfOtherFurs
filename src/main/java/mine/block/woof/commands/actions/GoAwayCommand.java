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
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class GoAwayCommand implements DogCommand {
    @Override
    public void runServer(NbtCompound context, ServerWorld world, PlayerEntity master, WolfEntity target) {
        target.playSound(SoundEvents.ENTITY_WOLF_WHINE, 1f, 1f);
        target.setTamed(false);
        target.setOwnerUuid(null);
        target.getNavigation().stop();
        target.clearGoalsAndTasks();
    }

    @Override
    public void runClient(NbtCompound context, WolfEntity target) {
        boolean status = !target.isSitting();
    }

    @Override
    public Identifier getID() {
        return new Identifier("woof", "abandon");
    }

    @Override
    public @Nullable Text getText() {
        return Text.literal("Abandon").formatted(Formatting.BOLD, Formatting.RED);
    }

    @Override
    public @Nullable Text getTooltip() {
        return Text.literal("This dog will no longer be yours, and will return to the wild!").formatted(Formatting.RED, Formatting.BOLD);
    }
}
