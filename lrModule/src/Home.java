import javafx.application.Platform;
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

        Menu radialMenu = new Menu(this);

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

    public void addLanguage(String language) {
    	if (fxPanel.getParent() == null) {
            frame.add(fxPanel, BorderLayout.CENTER);
        }
        Platform.runLater(() -> graph.addNode(language, fxPanel));

        statusBar.setText("Graph added.");
//        Features:
//        	1. Click on plus icon to add language node
//        	2. Drag the nodes close to each other to connect them based on common family
//        	3. Click on a node and press on delete to delete the node and its associated connections
//        	4. Double click on a node to duplicate it.
//        	5. Click anywhere outside to deselect a node
    }

    private JPopupMenu getDropdownMenu() {
        JPopupMenu dropdownMenu = new JPopupMenu();
        JMenuItem english = new JMenuItem("English");
        JMenuItem kazakh = new JMenuItem("Kazakh");
        JMenuItem french = new JMenuItem("French");
        JMenuItem russian = new JMenuItem("Russian");
        JMenuItem hindi = new JMenuItem("Hindi");
        JMenuItem german = new JMenuItem("German");
        JMenuItem bengali = new JMenuItem("Bengali");
        JMenuItem czech = new JMenuItem("Czech");
        JMenuItem spanish = new JMenuItem("Spanish");
        JMenuItem afrikaans = new JMenuItem("Afrikaans");
        JMenuItem portuguese = new JMenuItem("Portuguese");
        JMenuItem persian = new JMenuItem("Persian");
        

        dropdownMenu.add(english);
        dropdownMenu.add(kazakh);
        dropdownMenu.add(french);
        dropdownMenu.add(russian);
        dropdownMenu.add(hindi);
        dropdownMenu.add(german);
        dropdownMenu.add(bengali);
        dropdownMenu.add(czech);
        dropdownMenu.add(spanish);
        dropdownMenu.add(afrikaans);
        dropdownMenu.add(portuguese);
        dropdownMenu.add(persian);

        english.addActionListener(e -> addLanguage("English"));
        kazakh.addActionListener(e -> addLanguage("Kazakh"));
        french.addActionListener(e -> addLanguage("French"));
        russian.addActionListener(e -> addLanguage("Russian"));
        hindi.addActionListener(e -> addLanguage("Hindi"));
        german.addActionListener(e -> addLanguage("German"));
        bengali.addActionListener(e -> addLanguage("Bengali"));
        czech.addActionListener(e -> addLanguage("Czech"));
        spanish.addActionListener(e -> addLanguage("Spanish"));
        afrikaans.addActionListener(e -> addLanguage("Afrikaans"));
        portuguese.addActionListener(e -> addLanguage("Portuguese"));
        persian.addActionListener(e -> addLanguage("Persian"));
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
