package study;

// сущность прямоугольник
public class Rect {
    private int x;
    private int y;
    private int width;
    private int height;

    public Rect(int x0, int y0, int x1, int y1) {
        this.x = x0;
        this.y = y0;
        // высчитываем ширину и высоту треугольника по его координатам
        this.width = Math.abs(x0 - x1);
        this.height = Math.abs(y0 - y1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // возвращаем координаты второй точки
    public int getX1() {return Math.abs(width + x);}
    public int getY1() {return Math.abs(height + y);}


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
