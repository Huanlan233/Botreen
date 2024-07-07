package top.htext.botreen.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import top.htext.botreen.bot.Bot;
import top.htext.botreen.util.JsonUtils;

import java.io.File;
import java.io.IOException;


public class BotFormManager {
    private final File form;
    private final MinecraftServer server;

    public BotFormManager(MinecraftServer server) {
        this.server = server;
        this.form = new File(this.server.getSavePath(WorldSavePath.ROOT).toAbsolutePath() + "bot.json");
    }

    public void checkFile() {
        try {
            if (!form.exists() || !form.isFile()) form.createNewFile();
            if (0 == form.length()) JsonUtils.jsonWrite2File(new JsonArray(), form);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonArray getForm()  {
        checkFile();
        return (JsonArray) JsonUtils.file2Json(form);
    }

    public void addBot(String name, Vec3d pos, Vec2f rotation, Identifier dimensionId) {
        Bot newBot = new Bot(name, pos, rotation, dimensionId);
        JsonElement element = JsonUtils.obj2Json(newBot); // 搞成JsonObject
        JsonArray array = getForm();
        array.add(element);// 丢进array
        JsonUtils.jsonWrite2File(array, form);
    }

    public void deleteBot(int id) {
        JsonArray array = getForm();

        for (JsonElement element : array) {
            Bot bot = (Bot) JsonUtils.json2Obj(element, Bot.class);
            if (id == bot.getId()) {
                array.remove(element);
                break;
            }
        }

        JsonUtils.jsonWrite2File(array, form);
    }
}
