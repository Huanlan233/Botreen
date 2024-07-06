package top.htext.botreen;

import net.fabricmc.api.ModInitializer;
import top.htext.botreen.network.BotSyncProtocol;

public class Botreen implements ModInitializer {
	@Override
	public void onInitialize() {
		BotSyncProtocol.Server.init();
	}
}