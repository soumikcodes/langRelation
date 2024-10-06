import javax.swing.*;

import javafx.embed.swing.JFXPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    private JFrame frame;
    private JLabel statusBar;

    public Home() {
        frame = new JFrame("Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(1600, 1200));
        frame.setMinimumSize(new Dimension(800, 400));

        Menu radialMenu = new Menu();
        frame.add(radialMenu, BorderLayout.CENTER);

        statusBar = new JLabel();
        frame.add(statusBar, BorderLayout.SOUTH);

        createMenuBar();

        frame.pack();
        frame.setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu fileMenu = getAddNodeMenu();
        menuBar.add(fileMenu);
    }

    private JMenu getAddNodeMenu() {
        JMenu fileMenu = new JMenu("Menu");
        JMenuItem addItem = new JMenuItem("Add Language");

        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLanguage();
            }
        });

        fileMenu.add(addItem);

        return fileMenu;
    }

    private void addLanguage() {
//        TemplateGS gs = new TemplateGS();
//        gs.drawGS(frame);
        
        final JFXPanel fxPanel = new JFXPanel();
        fxPanel.setBackground(Color.GRAY);
        frame.add(fxPanel, BorderLayout.CENTER);
        GraphApp graph = new GraphApp();
        graph.addGrpah(fxPanel);

        statusBar.setText("Graph added.");
    }
}
