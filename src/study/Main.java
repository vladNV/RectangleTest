package study;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // начала работы приложения
        View view = new View();
        view.setSize(750, 1000);
        view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        view.setVisible(true);
    }

}
