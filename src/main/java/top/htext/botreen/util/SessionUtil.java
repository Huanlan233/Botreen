package top.htext.botreen.util;

import net.minecraft.client.MinecraftClient;

public class SessionUtil {
    public static boolean isPlaying(MinecraftClient client) {
        boolean playing = false;
        if (client.getGame().getCurrentSession() != null)
            playing = client.getGame().getCurrentSession().isRemoteServer() || client.isInSingleplayer();
        return playing;
    }

    public static boolean isPlaying() {
        MinecraftClient client = MinecraftClient.getInstance();
        return isPlaying(client);
    }
}
