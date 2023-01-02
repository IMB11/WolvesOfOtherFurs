/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof.server;

import mine.block.mru.server.CustomC2SPacket;
import mine.block.woof.commands.DogCommand;
import mine.block.woof.register.WoofRegistries;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class SendDogCommandC2S extends CustomC2SPacket {
    @Override
    public Identifier getID() {
        return new Identifier("woof", "dog_command");
    }

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        NbtCompound nbt = buf.readNbt();
        assert nbt != null;
        server.executeSync(() -> {
            UUID id = nbt.getUuid("wolfUUID");
            Identifier commandID = new Identifier("woof", nbt.getString("command"));
            for (ServerWorld world : server.getWorlds()) {
                Entity entity = world.getEntity(id);
                if (entity instanceof WolfEntity wolfEntity) {
                    DogCommand command = WoofRegistries.DOG_COMMAND_REGISTRY.get(commandID);
                    if (command != null) {
                        command.runServer(nbt, world, player, wolfEntity);
                        break;
                    }
                }
            }
        });
    }
}
