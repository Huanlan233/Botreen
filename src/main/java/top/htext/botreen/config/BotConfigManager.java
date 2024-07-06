package top.htext.botreen.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class BotConfigManager {
    public final File botFile;
    private final MinecraftServer server;

    public BotConfigManager(MinecraftServer server) {
        this.server = server;
        this.botFile = new File(this.server.getSavePath(WorldSavePath.ROOT).toAbsolutePath() + "bot.json");
    }

    public JsonArray getBotConfig()  {
        try {
            FileReader reader = new FileReader(botFile);
            JsonParser parser = new JsonParser();
            JsonArray array = (JsonArray) parser.parse(reader);
            reader.close();

            return array;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
