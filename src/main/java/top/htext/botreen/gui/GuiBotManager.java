package top.htext.botreen.gui;

import fi.dy.masa.malilib.gui.GuiListBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.util.StringUtils;
import org.jetbrains.annotations.Nullable;
import top.htext.botreen.bot.Bot;
import top.htext.botreen.bot.BotListManager;
import top.htext.botreen.gui.widget.WidgetBotEntry;
import top.htext.botreen.gui.widget.WidgetBotEntryList;

public class GuiBotManager extends GuiListBase<Bot, WidgetBotEntry, WidgetBotEntryList> implements ISelectionListener<Bot> {

    public GuiBotManager() {
        super(10, 50);
        this.title = StringUtils.translate("botreen.gui.title.bot_manager");
    }

    @Override
    public void onSelectionChange(@Nullable Bot bot) {
        Bot old = BotListManager.INSTANCE.getSelectedBot();
        BotListManager.INSTANCE.setSelectedBot(old == bot? null : bot);
    }


    @Override
    protected WidgetBotEntryList createListWidget(int listX, int listY) {
        return new WidgetBotEntryList(listX, listY, getBrowserWidth(), getBrowserHeight(), this);
    }

    @Override
    protected int getBrowserWidth() {
        return this.width - 20;
    }

    @Override
    protected int getBrowserHeight() {
        return this.height - 80;
    }

    public enum SideBotTab {
        LOCAL("botreen.gui.tab.local");
//        REMOTE("botreen.gui.tab.remote");

        private final String translationKey;
        SideBotTab(String translationKey) {
            this.translationKey = translationKey;
        }
        public String getDisplayName(){
            return StringUtils.translate(this.translationKey);
        }
    }

    public static class TabManager {
        private static SideBotTab sideBotTab = SideBotTab.LOCAL;

        public static void setSideBotTab(SideBotTab tab) {
            sideBotTab = tab;
        }

        public static SideBotTab getSideBotTab() {
            return sideBotTab;
        }
    }

    private record TopTabButtonListener(SideBotTab tab, GuiBotManager parent) implements IButtonActionListener {
        @Override
        public void actionPerformedWithButton(ButtonBase buttonBase, int i) {
            TabManager.setSideBotTab(this.tab);
            this.parent.reCreateListWidget();
            this.parent.getListWidget().resetScrollbarPosition();
        }
    }

    private void createTopTab() {
        int x = 10;
        int y = 26;

        for ( SideBotTab tab : SideBotTab.values() ){
            ButtonGeneric button = new ButtonGeneric(x, y, -1, 20, tab.getDisplayName());
            button.setEnabled(TabManager.getSideBotTab() != tab);
            this.addButton(button, new TopTabButtonListener(tab, this));

            x += button.getWidth() + 2;
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        createTopTab();
    }
}
