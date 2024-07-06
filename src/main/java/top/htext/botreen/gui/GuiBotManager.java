package top.htext.botreen.gui;

import fi.dy.masa.malilib.gui.GuiBase;
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

    public static class TabManager {
        private static SideBotTab sideBotTab = SideBotTab.LOCAL;

        public static void setSideBotTab(SideBotTab tab) {
            sideBotTab = tab;
        }

        public static SideBotTab getSideBotTab() {
            return sideBotTab;
        }
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

    private record TopTabButtonListener(SideBotTab tab, GuiBotManager parent) implements IButtonActionListener {
        @Override
        public void actionPerformedWithButton(ButtonBase button, int i) {
            TabManager.setSideBotTab(this.tab);
            this.parent.reCreateListWidget();
            if (this.parent.getListWidget() != null) this.parent.getListWidget().resetScrollbarPosition();
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

    public enum ToolBar {
        ADD("botreen.gui.tab.add"),
        CONFIGURE("botreen.gui.tab.configure");

        private final String translationKey;
        ToolBar(String translationKey) {
            this.translationKey = translationKey;
        }
        public String getDisplayName(){
            return StringUtils.translate(this.translationKey);
        }
    }

    private void createToolTab() {
        ButtonGeneric buttonAdd = new ButtonGeneric(10, this.height - 30, -1, 20, ToolBar.ADD.getDisplayName());
        ButtonGeneric buttonConfigure = new ButtonGeneric(this.width - 10, this.height - 30, -1, true, ToolBar.CONFIGURE.getDisplayName());

        this.addButton(buttonAdd, new IButtonActionListener() {
            @Override
            public void actionPerformedWithButton(ButtonBase buttonBase, int i) {

            }
        });

        this.addButton(buttonConfigure, new IButtonActionListener() {
            @Override
            public void actionPerformedWithButton(ButtonBase buttonBase, int i) {
                GuiBase.openGui(new GuiGenericConfig(new GuiBotManager()));
            }
        });
    }

    @Override
    public void initGui() {
        super.initGui();
        createTopTab();
        createToolTab();
    }
}
