package mine.block.woof.server;

import mine.block.minelib.server.MinelibPacketManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class WoofPackets extends MinelibPacketManager {
    public static final WoofPackets UPDATE_DOG_NAME = new WoofPackets("update_dog_name", EnvType.SERVER, (server, player, handler, buf, responseSender) -> {
        NbtCompound compound = buf.readNbt();
        String name = compound.getString("name");
        UUID uuid = compound.getUuid("id");
        server.executeSync(() -> {
            server.getWorlds().forEach(world -> {
                var wolf = world.getEntity(uuid);
                if(wolf == null) return;
                wolf.setCustomName(Text.of(name));
            });
        });
    }, null);

    public WoofPackets(String id, @Nullable EnvType envType, ServerPlayNetworking.@Nullable PlayChannelHandler serverAction, ClientPlayNetworking.@Nullable PlayChannelHandler clientAction) {
        super(id, envType, serverAction, clientAction);
    }

    @Override
    public Logger getLogger() {
        return LoggerFactory.getLogger("WOOF-Packets");
    }
}
