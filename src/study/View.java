package study;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class View extends JFrame {

    public View() {
        createGUI();
    }

    private void createGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel label1 = new JLabel("Координаты отрезка");
        label1.setFont(new Font("Sans Serif", Font.BOLD, 16));
        label1.setBounds(20, 10, 200, 30);
        JTextField lineCoordinates[] = new JTextField[4];
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
        String column[] = {"x0","y0","x1","y1"};
        String data[][] = {{"1","-3","0","5"}};
        JTable jTable = new JTable(data, column);
        JScrollPane scrollPane = new JScrollPane(jTable);
        JButton jButton = new JButton();
        scrollPane.setBounds(400, 50, 300, 350);
        panel.add(scrollPane);
        panel.add(label1);
        this.add(panel);
    }

}
