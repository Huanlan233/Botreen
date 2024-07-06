package top.htext.botreen.event;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import top.htext.botreen.config.BotreenConfigs;
import top.htext.botreen.gui.GuiBotManager;
import top.htext.botreen.gui.GuiGenericConfig;
import top.htext.botreen.network.BotSyncProtocol;

public class KeyCallbacks {
    public static void init(MinecraftClient client) {
        IHotkeyCallback hotkeyCallback = new KeyCallbackHotkeys(client);

        BotreenConfigs.Generic.OPEN_GUI_CONFIGS.getKeybind().setCallback(hotkeyCallback);
        BotreenConfigs.Generic.OPEN_BOT_MANAGER.getKeybind().setCallback(hotkeyCallback);
    }

    private record KeyCallbackHotkeys(MinecraftClient client) implements IHotkeyCallback {

        @Override
        public boolean onKeyAction(KeyAction keyAction, IKeybind key) {

            if (key == BotreenConfigs.Generic.OPEN_GUI_CONFIGS.getKeybind()) {
                GuiBase.openGui(new GuiGenericConfig());
                return true;
            } else if (key == BotreenConfigs.Generic.OPEN_BOT_MANAGER.getKeybind()) {
                ClientPlayNetworking.send(BotSyncProtocol.SYNC_BOTS, PacketByteBufs.empty());
                GuiBase.openGui(new GuiBotManager());
                return true;
            }

            return false;
        }
    }
}
