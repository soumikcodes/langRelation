import javafx.embed.swing.JFXPanel;

import javax.swing.*;

//import javafx.embed.swing.JFXPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    private JFrame frame;
    private JLabel statusBar;

    public Home() {
        frame = new JFrame("Home");
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setMinimumSize(new Dimension(800, 400));

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setPreferredSize(new Dimension(800, 150)); // about 30% of window height

        Menu radialMenu = new Menu(this::addLanguage);
        menuPanel.add(radialMenu, BorderLayout.CENTER);

        frame.add(menuPanel, BorderLayout.NORTH);


        statusBar = new JLabel();
        frame.add(statusBar, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public void addLanguage() {
//        TemplateGS gs = new TemplateGS();
//        gs.drawGS(frame);

        final JFXPanel fxPanel = new JFXPanel();
//        fxPanel.setBackground(Color.GRAY);
        frame.add(fxPanel, BorderLayout.CENTER);
        GraphApp graph = new GraphApp();
        graph.addGrpah(fxPanel);

        statusBar.setText("Graph added.");
    }
}
