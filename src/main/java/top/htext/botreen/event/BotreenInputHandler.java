package top.htext.botreen.event;

import java.util.List;
import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import top.htext.botreen.BotreenReference;
import top.htext.botreen.config.BotreenConfigs;

public class BotreenInputHandler implements IKeybindProvider
{
    private static final BotreenInputHandler INSTANCE = new BotreenInputHandler();

    private BotreenInputHandler() {
        super();
    }

    public static BotreenInputHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void addKeysToMap(IKeybindManager manager) {
        manager.addKeybindToMap(BotreenConfigs.Generic.OPEN_GUI_CONFIGS.getKeybind());
        manager.addKeybindToMap(BotreenConfigs.Generic.OPEN_BOT_MANAGER.getKeybind());
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        List<? extends IHotkey> hotkeys = ImmutableList.of(
                BotreenConfigs.Generic.OPEN_GUI_CONFIGS,
                BotreenConfigs.Generic.OPEN_BOT_MANAGER
        );
        manager.addHotkeysForCategory(BotreenReference.MOD_ID, "botreen.hotkeys.category.generic_hotkeys", hotkeys);
    }
}