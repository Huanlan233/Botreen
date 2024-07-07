package top.htext.botreen;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import top.htext.botreen.bot.BotFormCache;
import top.htext.botreen.network.FormSyncProtocol;

import static top.htext.botreen.BotreenReference.LOGGER;

@Environment(EnvType.CLIENT)
public class BotreenClient implements ClientModInitializer {
    public static BotFormCache CACHE = new BotFormCache();

    @Override
    public void onInitializeClient() {
        LOGGER.info("Botreen Initialized.");

        InitializationHandler.getInstance().registerInitializationHandler(new BotreenInitHandler());

        FormSyncProtocol.Client.init();
    }
}
