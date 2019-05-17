package com.rong.gt.api.gui.widgets;

import com.rong.gt.api.gui.IPositionedRectangularWidget;

public class AbstractPositionedRectangleWidget extends AbstractPositionedWidget implements IPositionedRectangularWidget {

    protected int width;
    protected int height;

    public AbstractPositionedRectangleWidget(int xPosition, int yPosition, int width, int height) {
        super(xPosition, yPosition);
        this.width = width;
        this.height = height;
    }

    @Override
    public int getXPosition() {
        return xPosition;
    }

    @Override
    public int getYPosition() {
        return yPosition;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
