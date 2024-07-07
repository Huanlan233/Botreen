package top.htext.botreen.bot;

import com.google.gson.JsonArray;

public class BotFormCache {
    private JsonArray list = new JsonArray();


    synchronized public JsonArray getList() {
        return list;
    }

    synchronized public void setList(JsonArray list) {
        this.list = list;
    }
}