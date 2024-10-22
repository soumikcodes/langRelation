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

    private JLabel welcomeMessage;
    private JLabel menuHintLabel;

    private JLabel menuNameTag;
    private JLabel languageInstructionLabel;

    public Home() {
        frame = new JFrame("Language Relation");
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(1860, 1080));
//        frame.setPreferredSize(new Dimension(800, 600));
        frame.setMinimumSize(new Dimension(800, 400));

        Menu radialMenu = new Menu(this);

//        languageInstructionLabel = new JLabel("Drag to connect, double-click to duplicate, and click + delete to remove.");
        languageInstructionLabel = new JLabel(
                "<html><h1 style='color:#d9d7d7;'><span style='font-size:12px;'>" +
                        "1. <span style='color:fcd483;'>Drag</span> the nodes close to each other <span style='color:9c9c9c;'>to connect</span> them based on common family<br>" +
                        "2. <span style='color:fcd483;'>Delete</span> the node and its associated connections by <span style='color:#9c9c9c;'>clicking on a node and press on delete</span><br>" +
                        "3. <span style='color:fcd483;'>Duplicate</span> node by <span style='color:#9c9c9c;'>double click</span> on it<br>" +
                        "4. <span style='color:fcd483;'>Deselect</span> a node by <span style='color:#9c9c9c;'>clicking anywhere outside</span>" +
                        "</span></h1></html>",
                JLabel.RIGHT
        );
        languageInstructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 200));
        languageInstructionLabel.setVisible(false);

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setPreferredSize(new Dimension(350, 150));
        menuPanel.add(radialMenu, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(800, 150));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(menuPanel, BorderLayout.WEST);

        menuHintLabel = new JLabel(
                "<html><h1 style='color:#d9d7d7;'><span style='font-size:16px;'>- Click on menu to add language.</span></h1></html>",
                JLabel.RIGHT
        );
        menuHintLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 600));
        leftPanel.add(menuHintLabel, BorderLayout.EAST);

        frame.add(leftPanel, BorderLayout.NORTH);

        dropdownMenu = getDropdownMenu();

        statusBar = new JLabel();
        frame.add(statusBar, BorderLayout.SOUTH);

        // init welcome Message
        welcomeMessage = new JLabel(
                "<html><h1 style='color:#d9d7d7;'><span style='font-size:40px;'>Language Relationships Application</span><br><span style='font-size:16x;'>Find out what your languages have in common!</span></h1></html>",
                JLabel.CENTER
        );
        welcomeMessage.setBorder(BorderFactory.createEmptyBorder(0, 0, 200, 0));
        frame.add(welcomeMessage, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    public void menuClicked() {
        if (menuHintLabel != null) {
            menuHintLabel.setVisible(false);  // Alternatively, you can remove it from the panel
            menuHintLabel = null;  // Optionally set it to null to prevent future references
            frame.revalidate();
            frame.repaint();
        }
    }

    public void addLanguage(String language) {
    	if (fxPanel.getParent() == null) {
            frame.add(fxPanel, BorderLayout.CENTER);
        }
        Platform.runLater(() -> graph.addNode(language, fxPanel));

        if (menuHintLabel != null) {
            menuHintLabel.setVisible(false);  // Hide the old hint
            menuHintLabel = null;  // Optional: Set to null to avoid future references
        }

        if (languageInstructionLabel != null) {
            languageInstructionLabel.setVisible(true);  // Show the instruction for languages
            JPanel menuPanel = (JPanel) frame.getContentPane().getComponent(0);  // Get the menu panel
            menuPanel.add(languageInstructionLabel, BorderLayout.EAST);  // Add the instruction label to the same position
            frame.revalidate();
            frame.repaint();
        }

        statusBar.setText("Graph added.");

        // hide welcome message
        if (welcomeMessage != null) {
            frame.remove(welcomeMessage);
            welcomeMessage = null;
            frame.revalidate();
            frame.repaint();
        }
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
