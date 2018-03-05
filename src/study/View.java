package study;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class View extends JFrame {

    public View() {
        createGUI();
    }

    private void createGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        initJLabels(panel);
        JTextField lineCoordinates[] = new JTextField[4];
        initCoordinateInput(lineCoordinates, panel);
        DefaultTableModel model = new DefaultTableModel();
        JTable coordinate = initCoordinatesTable(model);
        JScrollPane scrollPane = new JScrollPane();

        JButton addLine = new JButton("Добавить отрезок");
        addLine.setBounds(30, 120, 200, 20);
        JButton deleteLine = new JButton("Удалить отрезок");
        deleteLine.setBounds(30, 160, 200, 20);
        JButton setRectangle = new JButton("Установить координаты прямоугольной области");

        JButton show = new JButton("Отобразить отрезки и прямоугольную область");

        JButton filter = new JButton("Отобразить координаты отрезков пересекающих прямоугольную область");


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

        scrollPane.setBounds(400, 50, 300, 350);
        panel.add(deleteLine);
        panel.add(addLine);
        panel.add(scrollPane);
        this.add(panel);
    }


    private JTable initCoordinatesTable(DefaultTableModel model) {
        model.addColumn("x0");
        model.addColumn("y0");
        model.addColumn("x1");
        model.addColumn("y1");
        return new JTable(model);
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
        label2.setBounds(10, 200, 350, 30);

        JLabel label3 = new JLabel("Нижний правый угол прямоугольной области:");
        label3.setFont(new Font("Sans Serif", Font.BOLD, 14));
        label3.setBounds(10, 230, 350, 30);

        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
    }

}
