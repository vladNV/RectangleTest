package study;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class View extends JFrame {

    public View() {
        createGUI();
    }

    private void createGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        initJLabels(panel);
        JTextField lineCoordinates[] = new JTextField[4];
        JTextField rectangleCoordinates[] = new JTextField[4];
        initCoordinateInput(lineCoordinates, panel);
        initRectangleCoordinates(rectangleCoordinates, panel);

        DefaultTableModel model = new DefaultTableModel();
        JTable coordinate = initCoordinatesTable(model);
        JScrollPane scrollPane = new JScrollPane(coordinate);

        DefaultListModel listModel = new DefaultListModel();
        JList list = new JList(listModel);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setBounds(400, 320, 300, 130);

        JButton addLine = new JButton("Добавить отрезок");
        addLine.setBounds(30, 120, 200, 20);
        JButton deleteLine = new JButton("Удалить отрезок");
        deleteLine.setBounds(30, 160, 200, 20);
        JButton setRectangle = new JButton("Установить координаты прямоугольной области");
        setRectangle.setBounds(20, 490, 500, 30);
        JButton show = new JButton("Отобразить отрезки и прямоугольную область");
        show.setBounds(20, 520, 500, 30);
        JButton filter = new JButton("Отобразить координаты отрезков пересекающих прямоугольную область");

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

        deleteLine.addActionListener(e -> {
            if (coordinate.getRowCount() - 1 >= 0) {
                model.removeRow(coordinate.getRowCount() - 1);
            } else {
                JOptionPane.showMessageDialog(this, "Все отрезки удалены!");
            }
        });

        scrollPane.setBounds(400, 50, 300, 250);

        show.addActionListener(e -> {
            int x0, x1, y0, y1;
            PaintLayer paintLayer = new PaintLayer();
            paintLayer.setBounds(50, 580, 500, 300);
            x0 = Integer.parseInt((String) listModel.get(0));
            x1 = Integer.parseInt((String) listModel.get(1));
            y0 = Integer.parseInt((String) listModel.get(2));
            y1 = Integer.parseInt((String) listModel.get(3));
            paintLayer.setRect(new Rect(x0, y0, x1, y1));
            paintLayer.setLines(getLines(model));
            panel.add(paintLayer);
        });

        panel.add(deleteLine);
        panel.add(addLine);
        panel.add(scrollPane);
        panel.add(setRectangle);
        panel.add(show);
        panel.add(listScroller);
        this.add(panel);
    }

    private ArrayList<Line> getLines(DefaultTableModel model) {
        ArrayList<Line> lines = new ArrayList<>();
        int x0, x1, y0, y1;
        for (int i = 0; i < model.getRowCount(); i++) {
            x0 = Integer.parseInt((String) model.getValueAt(i, 0));
            x1 = Integer.parseInt((String) model.getValueAt(i, 1));
            y0 = Integer.parseInt((String) model.getValueAt(i, 2));
            y1 = Integer.parseInt((String) model.getValueAt(i, 3));
            lines.add(new Line(x0, x1, y0, y1));
        }
        return lines;
    }

    private JTable initCoordinatesTable(DefaultTableModel model) {
        model.addColumn("x0");
        model.addColumn("y0");
        model.addColumn("x1");
        model.addColumn("y1");
        return new JTable(model);
    }

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

}

