import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;

//import javafx.embed.swing.JFXPanel;

import java.awt.*;

public class Home {
    private JFrame frame;
    final JFXPanel fxPanel = new JFXPanel();
    final JPopupMenu dropdownMenu;
    private JLabel welcomeMessage;
    private JLabel menuHintLabel;
    private JLabel languageInstructionLabel;
    GraphApp graph = new GraphApp();

    public Home() {
        languageInstructionLabel = getLanguageInstructionLabel();
        menuHintLabel = getMenuHintLabel();
        dropdownMenu = getDropdownMenu();
        welcomeMessage = getWelcomeMessage();
        Menu radialMenu = new Menu(this);

        frame = new JFrame("Language Relation");
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(1860, 1080));
        frame.setMinimumSize(new Dimension(800, 400));

        JPanel menuPanel = getMenuPanel();
        menuPanel.add(radialMenu, BorderLayout.CENTER);

        JPanel leftPanel = getLeftPanel();
        leftPanel.add(menuPanel, BorderLayout.WEST);
        leftPanel.add(menuHintLabel, BorderLayout.EAST);

        frame.add(leftPanel, BorderLayout.NORTH);
        frame.add(welcomeMessage, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    private JLabel getLanguageInstructionLabel() {
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
        return languageInstructionLabel;
    }

    private JLabel getMenuHintLabel() {
        menuHintLabel = new JLabel(
                "<html><h1 style='color:#d9d7d7;'><span style='font-size:16px;'>- Click on menu to add language.</span></h1></html>",
                JLabel.RIGHT
        );
        menuHintLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 600));
        return menuHintLabel;
    }

    private JLabel getWelcomeMessage() {
        welcomeMessage = new JLabel(
                "<html><h1 style='color:#d9d7d7;'><span style='font-size:40px;'>Language Relationships Application</span><br><span style='font-size:16x;'>Find out what your languages have in common!</span></h1></html>",
                JLabel.CENTER
        );
        welcomeMessage.setBorder(BorderFactory.createEmptyBorder(0, 0, 200, 0));
        return welcomeMessage;
    }

    private JPanel getMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(350, 150));
        return panel;
    }

    private JPanel getLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(800, 150));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    public void manageMenuHint() {
        boolean isMenuHintVisible = menuHintLabel != null;
        if (isMenuHintVisible) {
            hideMenuHint();
            frame.revalidate();
            frame.repaint();
        }
    }

    private void hideMenuHint() {
        menuHintLabel.setVisible(false);
        menuHintLabel = null;
    }

    public void addLanguage(String language) {
    	if (fxPanel.getParent() == null) {
            frame.add(fxPanel, BorderLayout.CENTER);
        }
        Platform.runLater(() -> graph.addNode(language, fxPanel));

        manageMenuHint();
        manageLanguageInstructionHint();
        manageWelcomeMessage();
    }

    private void manageLanguageInstructionHint() {
        boolean isLanguageInstructionVisible = languageInstructionLabel != null;
        if (isLanguageInstructionVisible) {
            showLanguageInstruction();
            frame.revalidate();
            frame.repaint();
        }
    }

    private void showLanguageInstruction() {
        languageInstructionLabel.setVisible(true);
        JPanel menuPanel = (JPanel) frame.getContentPane().getComponent(0);
        menuPanel.add(languageInstructionLabel, BorderLayout.EAST);
    }

    private void manageWelcomeMessage() {
        boolean isWelcomeMessageVisible = welcomeMessage != null;
        if (isWelcomeMessageVisible) {
            removeWelcomeMessage();
            frame.revalidate();
            frame.repaint();
        }
    }

    private void removeWelcomeMessage() {
        frame.remove(welcomeMessage);
        welcomeMessage = null;
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
