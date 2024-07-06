package top.htext.botreen.config;

import com.google.gson.JsonArray;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class BotConfigCache {
    private JsonArray cache = new JsonArray();

    public JsonArray getCache() {
        return cache;
    }

    public void setCache(JsonArray cache) {
        this.cache = cache;
    }
}
