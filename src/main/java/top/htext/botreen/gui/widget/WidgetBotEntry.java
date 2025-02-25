package top.htext.botreen.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.ButtonOnOff;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetListEntryBase;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import top.htext.botreen.bot.Bot;
import top.htext.botreen.bot.BotListManager;

public class WidgetBotEntry extends WidgetListEntryBase<Bot> {

    private final Bot bot;
    private final boolean isOdd;

    public WidgetBotEntry(int x, int y, int width, int height, Bot entry, int listIndex, boolean isOdd) {
        super(x, y, width, height, entry, listIndex);
        this.bot = entry;
        this.isOdd = isOdd;

        y += 1;
        x += this.width - 2;

        x -= createButtonGeneric(x, y, "botreen.bot.remove");
        x -= createButtonGeneric(x, y, "botreen.bot.configure");
        x -= createButtonOnOff(x, y, "botreen.bot.online");
    }

    private int createButtonGeneric(int x, int y, String translationKey) {
        ButtonGeneric button = new ButtonGeneric(x, y, -1, true, translationKey);
        this.addButton(button, new ButtonListener());

        return button.getWidth() + 2;
    }

    private int createButtonOnOff(int x, int y, String translationKey) {
        ButtonOnOff button = new ButtonOnOff(x, y, -1, true, translationKey, false);
        this.addButton(button, new ButtonListener());

        return button.getWidth() + 2;
    }

    private static class ButtonListener implements IButtonActionListener {
        @Override
        public void actionPerformedWithButton(ButtonBase buttonBase, int i) {

        }
    }

    @Override
    public void render(int mouseX, int mouseY, boolean isSelected, MatrixStack matrixStack) {
        RenderUtils.color(1f, 1f, 1f, 1f);

        boolean isIdenticalSelection = BotListManager.INSTANCE.getSelectedBot() == this.entry;

        if (isSelected || isIdenticalSelection || this.isMouseOver(mouseX, mouseY)) {
            RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0x70FFFFFF);
        } else if (this.isOdd) {
            RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0x20FFFFFF);
        } else {
            RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0x50FFFFFF);
        }

        if (isIdenticalSelection) {
            RenderUtils.drawOutline(this.x, this.y, this.width, this.height, 0xFFE0E0E0);
        }

        String name = this.bot.getName();
        this.drawString(this.x + 4, this.y + 7, 0xFFFFFFFF, name, matrixStack);

        RenderUtils.color(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();

        super.render(mouseX, mouseY, isSelected, matrixStack);

        RenderUtils.disableDiffuseLighting();
    }
}
