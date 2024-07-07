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
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import top.htext.botreen.BotreenClient;
import top.htext.botreen.config.BotFormManager;

import static top.htext.botreen.BotreenReference.identifier;

public class FormSyncProtocol {
    public static final Identifier SYNC_FORM = identifier("botreen:sync_form");
    public static final Identifier UPDATE_FORM = identifier("botreen:update_form");
    public static final Identifier ADD_BOT = identifier("botreen:add_bot");
    public static final Identifier DELETE_BOT = identifier("botreen:delete_bot");

    public static class Client {
        public static void init() {
            ClientPlayNetworking.registerGlobalReceiver(UPDATE_FORM, Client::updateForm);
        }

        public static void updateForm(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
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
            ServerPlayNetworking.registerGlobalReceiver(SYNC_FORM, Server::syncForm);
            ServerPlayNetworking.registerGlobalReceiver(ADD_BOT, Server::addBot);
            ServerPlayNetworking.registerGlobalReceiver(DELETE_BOT, Server::deleteBot);
        }

        public static void syncForm(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            server.execute(() -> {
                BotFormManager bcm = new BotFormManager(server);
                PacketByteBuf form = PacketByteBufs.create().writeString(bcm.getForm().toString());
                ServerPlayNetworking.send(player, UPDATE_FORM, form);
            });
        }

        public static void addBot(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            String str = buf.readString();
            server.execute(() -> {
                BotFormManager bcm = new BotFormManager(server);

                Vec3d pos = player.getPos();
                Vec2f rotation = player.getRotationClient();
                Identifier dimensionId = player.getServerWorld().getRegistryKey().getValue();

                bcm.addBot(str, pos, rotation, dimensionId);
            });
        }

        public static void deleteBot(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            int id = buf.readInt();

            server.execute(() ->{
                BotFormManager bcm = new BotFormManager(server);
                bcm.deleteBot(id);
            });
        }
    }

}
