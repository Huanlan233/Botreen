package top.htext.botreen.network;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import top.htext.botreen.config.BotConfigCache;
import top.htext.botreen.config.BotConfigManager;

import static top.htext.botreen.BotreenReference.LOGGER;
import static top.htext.botreen.BotreenReference.identifier;

public class BotConfigsSync {

    public static final Identifier BOT_CONFIG_CHANNEL = identifier("botreen:bot_config");

    public static void clientReceivePullResponse(BotConfigCache cache) {
        if (MinecraftClient.getInstance().getGame() == null) return;

        ClientPlayNetworking.registerGlobalReceiver(BOT_CONFIG_CHANNEL, ((client, handler, buf, responseSender) -> {
            var str = buf.readString();

            client.execute(() -> {
                var gson = new GsonBuilder().create();
                JsonArray json = gson.fromJson(str, JsonArray.class);
                cache.setCache(json);
            });
        }));
    }

    public static void clientSendPullRequest() {
        if (MinecraftClient.getInstance().getGame() == null) return;

        PacketByteBuf buf = PacketByteBufs.empty();
        ClientPlayNetworking.send(BOT_CONFIG_CHANNEL, buf);
    }

    public static void serverReceivePullRequests() {
        ServerPlayNetworking.registerGlobalReceiver(BOT_CONFIG_CHANNEL, ((server, player, handler, buf, responseSender) -> {
            server.execute(() -> { serverSendPullResponse(player, server); });
        }));
    }

    public static void serverSendPullResponse(ServerPlayerEntity player, MinecraftServer server) {
        BotConfigManager botConfigManager = new BotConfigManager(server);
        PacketByteBuf buf = botConfigManager.getBotPacket();

        ServerPlayNetworking.send(player, BOT_CONFIG_CHANNEL, buf);
    }
}
