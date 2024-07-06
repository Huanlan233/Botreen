package top.htext.botreen;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import top.htext.botreen.bot.BotConfigCache;
import top.htext.botreen.network.BotSyncProtocol;

import static top.htext.botreen.BotreenReference.LOGGER;

@Environment(EnvType.CLIENT)
public class BotreenClient implements ClientModInitializer {
    public static BotConfigCache CACHE = new BotConfigCache();

    @Override
    public void onInitializeClient() {
        LOGGER.info("Botreen Initialized.");

        InitializationHandler.getInstance().registerInitializationHandler(new BotreenInitHandler());

        BotSyncProtocol.Client.init();
    }
}
