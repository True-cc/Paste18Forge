package uwu.smsgamer.pasteclient.gui.clickgui.block.blocks.values;

import uwu.smsgamer.pasteclient.gui.clickgui.block.*;
import uwu.smsgamer.pasteclient.gui.clickgui.block.blocks.ValueBlock;
import uwu.smsgamer.pasteclient.gui.clickgui.utils.IRenderer;
import uwu.smsgamer.pasteclient.values.RangeValue;

import java.awt.*;

public class RangeBlock extends ValueBlock {
    RangeValue value;

    public RangeBlock(BlockGUI gui, RangeValue value) {
        super(gui, value);
        this.value = value;
    }

    @Override
    public void draw(int x, int y, int mouseX, int mouseY) {

        IRenderer rdr = BlockClickGUI.renderer;

        rdr.drawRect(x - getWidth() / 2F, y + (HEIGHT.getValue() / 4F),
          getWidth(), HEIGHT.getValue() / 4F, isSelected() ? BlockClickGUI.NUM_BG_SELECT.getColor() :
            canSelect() && isHovering(mouseX, mouseY) ? BlockClickGUI.NUM_BG_HOVER.getColor() : BlockClickGUI.NUM_BG.getColor());

        rdr.drawRectR(x - getWidth() / 2F + getWidth() * value.getMinScaled(), y + (HEIGHT.getValue() / 4F),
          x - getWidth() / 2F + getWidth() * value.getMaxScaled(),
          y + (HEIGHT.getValue() / 4F) + HEIGHT.getValue() / 4F, isSelected() ? BlockClickGUI.NUM_SLIDER_SELECT.getColor() :
            canSelect() && isHovering(mouseX, mouseY) ? BlockClickGUI.NUM_SLIDER_HOVER.getColor() :
              BlockClickGUI.NUM_SLIDER.getColor());

        rdr.drawOutline(x - getWidth() / 2F - 2, y - (getHeight()) / 2F,
          getWidth() + 4, getHeight(), 2, Color.BLACK);
        rdr.drawString(x - getWidth() / 2, y - HEIGHT.getInt() / 4 * 3 + 4, value.getName(), Color.BLACK);

        String owo = value.getValStr();
        boolean hasMin = value.getMin() != 0;
        switch (value.getType()) {
            case INTEGER:
                owo = value.getValStr() + (hasMin ? ("  " + value.getMin().intValue() + "-" + value.getMax().intValue()) : "/" + value.getMax().intValue());
                break;
            case DECIMAL:
                owo = value.getValStr() + (hasMin ? ("  " + value.getMin() + "-" + value.getMax()) : "/" + value.getMax());
                break;
//            case TIME:
//            case PERCENT:
//                owo = value.getValStr();
//                break;
        }

        rdr.drawString(x + getWidth() / 2 - rdr.getStringWidth(owo) - 2, y - HEIGHT.getInt() / 4 * 3 + 4, owo, Color.BLACK);
        setDescription(mouseX, mouseY);
    }

    boolean closest;

    @Override
    public int getHeight() {
        return (int) (HEIGHT.getValue() * 1.25);
    }

    @Override
    public void click(int mouseX, int mouseY, int mouseButton, int pressType) {
        super.click(mouseX, mouseY, mouseButton, pressType);
        if (mouseButton == 0 && pressType == 0 && canSelect() && isHovering(mouseX, mouseY)) {
            setSelected();
            closest = value.getClosest(Math.max(0, Math.min(1, (mouseX * 2 - lastX + getWidth() / 2D) / getWidth())));
        }
        if (mouseButton == 0 && pressType == 2) setUnselected();
        if (isSelected()) {
            double d = Math.max(0, Math.min(1, (mouseX * 2 - lastX + getWidth() / 2D) / getWidth()));
            if ((closest && value.getMinScaled() < d) ||
              (!closest && value.getMaxScaled() > d)) {
                closest = !closest;
                double a = value.getMaxValue();
                double b = value.getMinValue();
                value.setMax(b);
                value.setMin(a);
            }
            if (closest) value.setMaxScaled(d);
            else value.setMinScaled(d);
            if (value.getMinValue() > value.getMaxValue()) {
                closest = !closest;
                value.setProperly();
            }
        }
    }
}
