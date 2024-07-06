package top.htext.botreen.bot;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

public class BotConfigCache {
    private JsonArray list = new JsonArray();


    synchronized public JsonArray getList() {
        return list;
    }

    synchronized public void setList(JsonArray list) {
        this.list = list;
    }
}