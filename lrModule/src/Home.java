import javafx.embed.swing.JFXPanel;

import javax.swing.*;

//import javafx.embed.swing.JFXPanel;

import java.awt.*;

public class Home {
    private JFrame frame;
    private JLabel statusBar;
    final JFXPanel fxPanel = new JFXPanel();
    GraphApp graph = new GraphApp();

    final JPopupMenu dropdownMenu;

    public Home() {
        frame = new JFrame("Home");
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setMinimumSize(new Dimension(800, 400));

        Menu radialMenu = new Menu(this, this::addLanguage);

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setPreferredSize(new Dimension(150, 150));
        menuPanel.add(radialMenu, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(800, 150));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(menuPanel, BorderLayout.WEST);
        frame.add(leftPanel, BorderLayout.NORTH);

        dropdownMenu = getDropdownMenu();

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

    private JPopupMenu getDropdownMenu() {
        JPopupMenu dropdownMenu = new JPopupMenu();
        JMenuItem english = new JMenuItem("English");
        JMenuItem kazakh = new JMenuItem("Kazakh");
        JMenuItem french = new JMenuItem("French");
        JMenuItem russian = new JMenuItem("Russian");
        JMenuItem hindi = new JMenuItem("Hindi");
        JMenuItem german = new JMenuItem("German");

        dropdownMenu.add(english);
        dropdownMenu.add(kazakh);
        dropdownMenu.add(french);
        dropdownMenu.add(russian);
        dropdownMenu.add(hindi);
        dropdownMenu.add(german);

        english.addActionListener(e -> addLanguage());
        kazakh.addActionListener(e -> addLanguage());
        french.addActionListener(e -> addLanguage());
        russian.addActionListener(e -> addLanguage());
        hindi.addActionListener(e -> addLanguage());
        german.addActionListener(e -> addLanguage());
        return dropdownMenu;
    }

    public void showDropdownMenu(int x, int y) {
        dropdownMenu.show(frame, x, y);
    }

    public void dropdownHide() {
        if (dropdownMenu.isVisible()) {
            dropdownMenu.setVisible(false);
        }
    }
}
