package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Bookstore Management");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Books", new BookPanel());
        tabbedPane.add("Customers", new CustomerPanel());
        tabbedPane.add("Orders", new OrderPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}