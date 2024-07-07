package top.htext.botreen;

import net.fabricmc.api.ModInitializer;
import top.htext.botreen.network.FormSyncProtocol;

public class Botreen implements ModInitializer {
	@Override
	public void onInitialize() {
		FormSyncProtocol.Server.init();
	}
}