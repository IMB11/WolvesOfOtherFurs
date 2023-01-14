/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package mine.block.woof.server;

import mine.block.mru.server.CustomS2CPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class SetupScreenS2C extends CustomS2CPacket {
    @Override
    public Identifier getID() {
        return new Identifier("woof", "setup_woof_screen");
    }

    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        NbtCompound nbt = buf.readNbt();
        UUID entity_UUID = buf.readUuid();
    }
}
