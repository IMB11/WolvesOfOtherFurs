package mine.block.woof.server;

import mine.block.minelib.server.MinelibPacketManager;
import mine.block.woof.commands.DogCommand;
import mine.block.woof.register.WoofRegistries;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class WoofPackets extends MinelibPacketManager {
    public static WoofPackets SEND_DOG_COMMAND = new WoofPackets("dog_command", null, (server, player, handler, buf, responseSender) -> {
        NbtCompound nbt = buf.readNbt();
        assert nbt != null;
        server.executeSync(() -> {
            UUID id = nbt.getUuid("wolfUUID");
            Identifier commandID = new Identifier("woof", nbt.getString("command"));
            for (ServerWorld world : server.getWorlds()) {
                Entity entity = world.getEntity(id);
                if(entity instanceof WolfEntity wolfEntity) {
                    DogCommand command = WoofRegistries.DOG_COMMAND_REGISTRY.get(commandID);
                    if(command != null) {
                        command.runServer(nbt, world, player, wolfEntity);
                        break;
                    }
                }
            }
        });
    }, null);

    public WoofPackets(String id, @Nullable EnvType envType, ServerPlayNetworking.@Nullable PlayChannelHandler serverAction, ClientPlayNetworking.@Nullable PlayChannelHandler clientAction) {
        super(id, envType, serverAction, clientAction);
    }
}
