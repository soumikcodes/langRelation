import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    private JFrame frame;
    private JPanel graphPanelContainer;
    private JLabel statusBar;

    public Home() {
        frame = new JFrame("Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(1600, 1200));
        frame.setMinimumSize(new Dimension(800, 400));

        statusBar = new JLabel();
        frame.add(statusBar, BorderLayout.SOUTH);

        graphPanelContainer = new JPanel();
        frame.add(graphPanelContainer, BorderLayout.CENTER);

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
        graphPanelContainer.removeAll();
//        statusBar.setText("Add language");
        TemplateGS gs = new TemplateGS();
//        gs.drawGS();
//        JPanel graphPanel = gs.getGraphPanel();

//        graphPanelContainer.add(graphPanel, BorderLayout.CENTER);
        graphPanelContainer.revalidate();
        graphPanelContainer.repaint();

        statusBar.setText("Graph added.");
    }
}
