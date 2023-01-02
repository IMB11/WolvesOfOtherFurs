/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof.commands.generic;

import mine.block.woof.commands.DogCommand;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.Random;

public class PetCommand implements DogCommand {
    private final Random random = new Random();

    @Override
    public void runServer(NbtCompound context, ServerWorld world, PlayerEntity master, WolfEntity target) {
        if (target.getHealth() < (1f / 2f * target.getMaxHealth())) {
            target.playSound(SoundEvents.ENTITY_WOLF_GROWL, 1f, 1f);
            showEmoteParticle(false, world, target);
        } else {
            target.playSound(SoundEvents.ENTITY_WOLF_WHINE, 1f, 1f);
            showEmoteParticle(true, world, target);
        }
    }

    protected void showEmoteParticle(boolean positive, ServerWorld world, WolfEntity pos) {
        ParticleEffect particleEffect = ParticleTypes.HEART;

        if (!positive) {
            particleEffect = ParticleTypes.ANGRY_VILLAGER;
        }

        for (int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            world.spawnParticles(particleEffect, pos.getParticleX(1.0), pos.getRandomBodyY() + 0.5, pos.getParticleZ(1.0), 1, d, e, f, 1);
        }

    }

    @Override
    public Identifier getID() {
        return new Identifier("woof", "pet");
    }
}
