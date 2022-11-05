/*
 * Copyright (C) 2022 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof.commands;

import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

public interface DogCommand {
    void runServer(NbtCompound context, ServerWorld world, PlayerEntity master, WolfEntity target);

    default void runClient(NbtCompound context, WolfEntity target) {
    }

    Identifier getID();

    @Nullable
    default Text getTooltip() {
        return null;
    }

    @Nullable
    default Text getText() {
        return Text.of(StringUtils.capitalize(getID().getPath()));
    }
}
