package top.htext.botreen.gui;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.gui.screen.Screen;
import top.htext.botreen.BotreenReference;
import top.htext.botreen.config.BotreenConfigs;

import java.util.List;

public class GuiGenericConfig extends GuiConfigsBase {

    public GuiGenericConfig() {
        super(10, 50, BotreenReference.MOD_ID, null, "botreen.gui.title.configs");
    }

    public GuiGenericConfig(Screen screen) {
        super(10, 50, BotreenReference.MOD_ID, screen, "botreen.gui.title.configs");
    }

    public enum ConfigGuiTab {
        GENERIC("botreen.gui.tab.generic");

        private final String translationKey;
        ConfigGuiTab(String translationKey) {
            this.translationKey = translationKey;
        }
        public String getDisplayName(){
            return StringUtils.translate(this.translationKey);
        }
    }

    public static class TabManager {
        private static ConfigGuiTab configGuiTab = ConfigGuiTab.GENERIC;

        public static void setConfigGuiTab(ConfigGuiTab tab) {
            configGuiTab = tab;
        }

        public static ConfigGuiTab getConfigGuiTab() {
            return configGuiTab;
        }
    }


    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        List<? extends IConfigBase> configs = BotreenConfigs.Generic.OPTIONS;

        return ConfigOptionWrapper.createFor(configs);
    }

    private record TopTabButtonListener(ConfigGuiTab tab, GuiGenericConfig parent) implements IButtonActionListener {
        @Override
        public void actionPerformedWithButton(ButtonBase buttonBase, int i) {
            TabManager.setConfigGuiTab(this.tab);
            this.parent.reCreateListWidget();
            if (this.parent.getListWidget() != null) this.parent.getListWidget().resetScrollbarPosition();
        }
    }

    private void createTopTab() {
        int x = 10;
        int y = 26;

        for ( ConfigGuiTab tab : ConfigGuiTab.values() ){
            ButtonGeneric button = new ButtonGeneric(x, y, -1, 20, tab.getDisplayName());
            button.setEnabled(TabManager.getConfigGuiTab() != tab);
            this.addButton(button, new TopTabButtonListener(tab, this));

            x += button.getWidth() + 2;
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();
        this.createTopTab();
    }
}
