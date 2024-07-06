package top.htext.botreen.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.IConfigValue;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.StringUtils;

import java.io.File;

public class BotreenConfigs implements IConfigHandler {
    public static final String CONFIG_FILE_NAME = "botreen.json";

    public static class Generic {
        public static final ConfigHotkey OPEN_GUI_CONFIGS = new ConfigHotkey("openConfigGui", "B,C", StringUtils.translate("botreen.comment.openGuiConfigs"));
        public static final ConfigHotkey OPEN_BOT_MANAGER = new ConfigHotkey("openBotManager", "B", KeybindSettings.RELEASE, StringUtils.translate("botreen.comment.openBotManager"));

        public static final ImmutableList<IConfigValue> OPTIONS = ImmutableList.of(
                OPEN_GUI_CONFIGS,
                OPEN_BOT_MANAGER
        );
    }

    @Override
    public void load() {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if ( ! (configFile.isFile() && configFile.exists()) ) return;
        JsonElement element = JsonUtils.parseJsonFile(configFile);

        if(element == null || !element.isJsonObject()) return;
        JsonObject root = element.getAsJsonObject();
        ConfigUtils.readConfigBase(root, "Configs", Generic.OPTIONS);
    }

    @Override
    public void save() {
        File dir = FileUtils.getConfigDirectory();
        if (! ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) ) return;

        JsonObject root = new JsonObject();
        ConfigUtils.writeConfigBase(root, "Configs", Generic.OPTIONS);
        JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
    }
}
