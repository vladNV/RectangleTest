package study;

import java.awt.*;
import java.util.ArrayList;

public class PaintLayer extends Canvas {

    private Rect rect;
    private ArrayList<Line> lines;

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    @Override
    public void paint(Graphics g) {
        g.drawRect(rect.getX(), rect.getY(),
                rect.getWidth(), rect.getHeight());
        for (Line line : lines) {
            g.drawLine(line.getX0(), line.getY0(),
                    line.getX1(), line.getY1());
        }
    }
}
