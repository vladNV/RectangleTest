package study;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class View extends JFrame {

    public View() {
        createGUI();
    }

    private void createGUI() {
        // инициализация
        JPanel panel = new JPanel();
        JScrollPane scrPane = new JScrollPane(panel);
        panel.setLayout(null);
        initJLabels(panel);
        JTextField lineCoordinates[] = new JTextField[4];
        JTextField rectangleCoordinates[] = new JTextField[4];
        initCoordinateInput(lineCoordinates, panel);
        initRectangleCoordinates(rectangleCoordinates, panel);

        // инициализация таблицы с отрезками
        DefaultTableModel model = new DefaultTableModel();
        JTable coordinate = initCoordinatesTable(model);
        JScrollPane scrollPane = new JScrollPane(coordinate);

        // иниицализация листа
        DefaultListModel listModel = new DefaultListModel();
        JList list = new JList(listModel);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setBounds(400, 320, 300, 130);

        // инициализация кнопок
        JButton addLine = new JButton("Добавить отрезок");
        addLine.setBounds(30, 120, 200, 20);
        JButton deleteLine = new JButton("Удалить отрезок");
        deleteLine.setBounds(30, 160, 200, 20);
        JButton setRectangle = new JButton("Установить координаты прямоугольной области");
        setRectangle.setBounds(20, 490, 400, 20);
        JButton show = new JButton("Отобразить отрезки и прямоугольную область");
        show.setBounds(20, 520, 400, 20);
        JButton filter = new JButton("Отобразить координаты отрезков пересекающих прямоугольную область");
        filter.setBounds(30, 800, 450, 20);

        // ввод координат прямоугольника и вывод их в список
        setRectangle.addActionListener(e -> {
            if (listModel.getSize() == 4) {
                JOptionPane.showMessageDialog(this, "Все координаты введены");
                return;
            }
            String[] inputs = {
                rectangleCoordinates[0].getText(),
                rectangleCoordinates[1].getText(),
                rectangleCoordinates[2].getText(),
                rectangleCoordinates[3].getText()
            };
            for (String i : inputs) {
                if (!i.matches("-?[0-9]{1,4}")) {
                    JOptionPane.showMessageDialog(this, "Ошибка ввода!");
                    return;
                }
                listModel.addElement(i);
            }

        });

        // добавить отрезок
        addLine.addActionListener(e -> {
            String[] inputs = {
                lineCoordinates[0].getText(),
                lineCoordinates[1].getText(),
                lineCoordinates[2].getText(),
                lineCoordinates[3].getText()
            };
            for (String i : inputs) {
                if (!i.matches("-?[0-9]{1,4}")) {
                    JOptionPane.showMessageDialog(this, "Ошибка ввода!");
                    return;
                }
            }
            model.addRow(inputs);
        });

        // удалить отрезок
        deleteLine.addActionListener(e -> {
            if (coordinate.getRowCount() - 1 >= 0) {
                model.removeRow(coordinate.getRowCount() - 1);
            } else {
                JOptionPane.showMessageDialog(this, "Все отрезки удалены!");
            }
        });

        scrollPane.setBounds(400, 50, 300, 250);

        // отрисовать прямоугольник и отрезки
        show.addActionListener(e -> {
            PaintLayer paintLayer = new PaintLayer();
            paintLayer.setBounds(50, 580, 500, 300);
            paintLayer.setRect(getRect(listModel));
            paintLayer.setLines(getLines(model));
            panel.add(paintLayer);
        });

        // определение отрезков, которые пересекают прямоугольник
        // и вывод их координаты, в отдельную таблицу
        DefaultTableModel resultModel = new DefaultTableModel();
        JTable resulted = initCoordinatesTable(resultModel);
        resulted.setBounds(400, 550, 300, 200);
        filter.addActionListener(e -> {
            ArrayList<Line> lines = getLines(model);
            Rect rect = getRect(listModel);
            lines.stream()
                    .filter(l -> ifBelong(l, rect.getX(), rect.getX1(), rect.getY(), rect.getY1()))
                    .collect(Collectors.toList()).forEach(l -> resultModel
                    .addRow(new Object[]{l.getX0(), l.getY0(), l.getX1(), l.getY1()}));
        });

        // Добавляем все на панель
        panel.add(resulted);
        panel.add(deleteLine);
        panel.add(addLine);
        panel.add(scrollPane);
        panel.add(setRectangle);
        panel.add(show);
        panel.add(listScroller);
        panel.add(filter);
        this.add(scrPane);
    }

    // строим прямоугольник по нашим координатам
    private Rect getRect(DefaultListModel model) {
        int x0, y0, x1, y1;
        x0 = Integer.parseInt((String) model.get(0));
        y0 = Integer.parseInt((String) model.get(1));
        x1 = Integer.parseInt((String) model.get(2));
        y1 = Integer.parseInt((String) model.get(3));
        return new Rect(x0, y0, x1, y1);
    }

    //строем линию (отрезок) по нашим координатам
    private ArrayList<Line> getLines(DefaultTableModel model) {
        ArrayList<Line> lines = new ArrayList<>();
        int x0, x1, y0, y1;
        for (int i = 0; i < model.getRowCount(); i++) {
            x0 = Integer.parseInt((String) model.getValueAt(i, 0));
            y0 = Integer.parseInt((String) model.getValueAt(i, 1));
            x1 = Integer.parseInt((String) model.getValueAt(i, 2));
            y1 = Integer.parseInt((String) model.getValueAt(i, 3));
            lines.add(new Line(x0, x1, y0, y1));
        }
        return lines;
    }

    // инициализация таблицы
    private JTable initCoordinatesTable(DefaultTableModel model) {
        model.addColumn("x0");
        model.addColumn("y0");
        model.addColumn("x1");
        model.addColumn("y1");
        return new JTable(model);
    }

    // инициализация для текстовых полей, для ввода координат прямоугольника
    private void initRectangleCoordinates(JTextField[] rectangleCoordinates,
                                          JPanel panel) {
        rectangleCoordinates[0] = new JTextField("x0");
        rectangleCoordinates[1] = new JTextField("y0");
        rectangleCoordinates[2] = new JTextField("x1");
        rectangleCoordinates[3] = new JTextField("y1");
        rectangleCoordinates[0].setBounds(40, 340, 50, 30);
        rectangleCoordinates[1].setBounds(100, 340, 50, 30);
        rectangleCoordinates[2].setBounds(40, 400, 50, 30);
        rectangleCoordinates[3].setBounds(100, 400, 50, 30);
        for (final JTextField field : rectangleCoordinates) {
            panel.add(field);
        }
    }

    // инициализация текстовых полей для ввода координат
    private void initCoordinateInput(JTextField[] lineCoordinates, JPanel panel) {
        lineCoordinates[0] = new JTextField("x0");
        lineCoordinates[1] = new JTextField("y0");
        lineCoordinates[2] = new JTextField("x1");
        lineCoordinates[3] = new JTextField("y1");
        final int xShift = 60;
        final int y = 70;
        int x = 30;
        for (final JTextField field : lineCoordinates) {
            field.setBounds(x, y, 50, 30);
            panel.add(field);
            x += xShift;
        }
    }

    // инициализируем labels
    private void initJLabels(JPanel panel) {
        JLabel label1 = new JLabel("Координаты отрезка");
        label1.setFont(new Font("Sans Serif", Font.BOLD, 16));
        label1.setBounds(20, 10, 200, 30);

        JLabel label2 = new JLabel("Верхний левый угол прямоугольной области:");
        label2.setFont(new Font("Sans Serif", Font.BOLD, 14));
        label2.setBounds(10, 310, 370, 30);

        JLabel label3 = new JLabel("Нижний правый угол прямоугольной области:");
        label3.setFont(new Font("Sans Serif", Font.BOLD, 14));
        label3.setBounds(10, 370, 370, 30);

        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
    }

    // Проверяем пересекает ли отрезок прямоугольник
    private boolean ifBelong(Line line, int x0,
                             int x1, int y0, int y1) {
        // пересекает ли начало отрезка одну из границ прямоугольника
        boolean condition = (line.getX0() >= x0 && line.getX0() <= x1 && line.getY0() >= y0 && line.getY0() <= y1);
        // пересекает ли конец отрезка одну из границ прямоугольника
        boolean condition2 = (line.getX1() >= x0 && line.getX1() <= x1 && line.getY1() >= y0 && line.getY1() <= y1);
        // выполняем операцию ислкючающие ИЛИ, поскольку если мы получим два true
        // то отрезок лежит внутри прямоугольника но не пересекает
        // два false, то отрезок лежит вне области
        // одно true другое false, значит отрезок пересекает
        return condition ^ condition2;
    }

}

