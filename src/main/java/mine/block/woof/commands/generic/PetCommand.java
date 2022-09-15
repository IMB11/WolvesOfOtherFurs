package mine.block.woof.commands.generic;

import mine.block.woof.commands.DogCommand;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.Random;

public class PetCommand implements DogCommand {
    private final Random random = new Random();

    @Override
    public void run(NbtCompound context, ServerWorld world, PlayerEntity master, WolfEntity target) {
        if(target.getHealth() < (1f/2f * target.getMaxHealth())) {
            world.playSound(target.getX(), target.getY(), target.getZ(), SoundEvents.ENTITY_WOLF_GROWL, SoundCategory.NEUTRAL, 1f, 1f, false);
            showEmoteParticle(false, world, target);
        } else {
            world.playSound(target.getX(), target.getY(), target.getZ(), SoundEvents.ENTITY_WOLF_WHINE, SoundCategory.NEUTRAL, 1f, 1f, false);
            showEmoteParticle(true, world, target);
        }
    }

    protected void showEmoteParticle(boolean positive, ServerWorld world, WolfEntity pos) {
        ParticleEffect particleEffect = ParticleTypes.HEART;

        if (!positive) {
            particleEffect = ParticleTypes.ANGRY_VILLAGER;
        }

        for(int i = 0; i < 7; ++i) {
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
