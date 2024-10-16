import javafx.embed.swing.JFXPanel;

import javax.swing.*;

//import javafx.embed.swing.JFXPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    private JFrame frame;
    private JLabel statusBar;
    final JFXPanel fxPanel = new JFXPanel();
    GraphApp graph = new GraphApp();

    public Home() {
        frame = new JFrame("Home");
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setMinimumSize(new Dimension(800, 400));

        Menu radialMenu = new Menu(this::addLanguage);

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setPreferredSize(new Dimension(150, 150));
        menuPanel.add(radialMenu, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(800, 150));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(menuPanel, BorderLayout.WEST);
        frame.add(leftPanel, BorderLayout.NORTH);

        statusBar = new JLabel();
        frame.add(statusBar, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public void addLanguage() {
        frame.add(fxPanel, BorderLayout.CENTER);
        graph.addNode(fxPanel);

        statusBar.setText("Graph added.");
    }
}
