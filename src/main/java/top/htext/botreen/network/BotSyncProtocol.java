package top.htext.botreen.network;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import top.htext.botreen.BotreenClient;
import top.htext.botreen.config.BotConfigManager;

import static top.htext.botreen.BotreenReference.identifier;

public class BotSyncProtocol {
    public static final Identifier SYNC_BOTS = identifier("botreen:sync_bots");
    public static final Identifier UPDATE_BOTS = identifier("botreen:update_bots");

    public static class Client {
        public static void init() {
            ClientPlayNetworking.registerGlobalReceiver(UPDATE_BOTS, Client::updateBots);
        }

        public static void updateBots(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            String str = buf.readString();

            Gson gson = new Gson();
            JsonArray array = gson.fromJson(str, JsonArray.class);
            BotreenClient.CACHE.setList(array);
            // 真是搞不懂了，为毛在client.execute里边总是比getAllBot运行得慢，服了
//            client.execute(() -> {
//
//            });
        }
    }

    public static class Server {
        public static void init() {
            ServerPlayNetworking.registerGlobalReceiver(SYNC_BOTS, Server::syncBots);
        }

        public static void syncBots(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            BotConfigManager bcm = new BotConfigManager(server);
            PacketByteBuf newBuf = PacketByteBufs.create().writeString(bcm.getBotConfig().toString());
            ServerPlayNetworking.send(player, UPDATE_BOTS, newBuf);
        }
    }

}
