package top.htext.botreen.bot;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.Nullable;
import top.htext.botreen.BotreenClient;

import java.util.ArrayList;
import java.util.Collection;

public class BotListManager {

    public static final BotListManager INSTANCE = new BotListManager();

    @Nullable
    private Bot selectedBot;

    public BotListManager() {
    }

    public Collection<Bot> getAllBot() {
        JsonArray array = BotreenClient.CACHE.getList();

        ArrayList<Bot> list = new ArrayList<>();

        for ( JsonElement element : array ) {
            Gson gson = new Gson();
            var bot = gson.fromJson(element, Bot.class);
            list.add(bot);
        }

        return list;
    }

    public void setSelectedBot(@Nullable Bot bot) {
        this.selectedBot = bot;
    }

    public @Nullable Bot getSelectedBot() {
        return this.selectedBot;
    }
}