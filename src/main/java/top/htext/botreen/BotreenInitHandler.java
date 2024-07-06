package top.htext.botreen;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import top.htext.botreen.config.BotreenConfigs;
import top.htext.botreen.event.BotreenInputHandler;
import top.htext.botreen.event.KeyCallbacks;

@Environment(EnvType.CLIENT)
public class BotreenInitHandler implements IInitializationHandler {
    public BotreenInitHandler() {
    }

    public void registerModHandlers() {
        ConfigManager.getInstance().registerConfigHandler("botreen", new BotreenConfigs());
        InputEventHandler.getKeybindManager().registerKeybindProvider(BotreenInputHandler.getInstance());

        KeyCallbacks.init(MinecraftClient.getInstance());
    }
}