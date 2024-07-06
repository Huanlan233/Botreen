package top.htext.botreen.gui.widget;

import fi.dy.masa.malilib.gui.LeftRight;
import fi.dy.masa.malilib.gui.MaLiLibIcons;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetListBase;
import fi.dy.masa.malilib.gui.widgets.WidgetSearchBar;
import top.htext.botreen.bot.Bot;
import top.htext.botreen.bot.BotListManager;

import java.util.Collection;

public class WidgetBotEntryList extends WidgetListBase<Bot, WidgetBotEntry> {

    public WidgetBotEntryList(int x, int y, int width, int height, ISelectionListener<Bot> selectionListener) {
        super(x, y, width, height, selectionListener);
        this.widgetSearchBar = new WidgetSearchBar(x + 2, y + 4, width - 14, 14, 0, MaLiLibIcons.SEARCH, LeftRight.LEFT);
        this.browserEntriesOffsetY = 23;
        this.browserEntryHeight = 22;
    }

    @Override
    protected WidgetBotEntry createListEntryWidget(int x, int y, int listIndex, boolean isOdd, Bot bot) {
        return new WidgetBotEntry(x, y, this.browserEntryWidth, this.getBrowserEntryHeightFor(bot), bot, listIndex, isOdd);
    }

    @Override
    protected Collection<Bot> getAllEntries() {
        return BotListManager.INSTANCE.getAllBot();
    }
}
