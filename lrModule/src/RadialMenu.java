import java.awt.*;
import javax.swing.*;

public class RadialMenu extends JPanel {
    private int buttonSize = 1000;

    public RadialMenu() {
        setBackground(new Color(20, 176, 211));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        createMenuButtons(g2);
    }

    private void createMenuButtons(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();
        int x = (width - buttonSize) / 2;
        int y = (height - buttonSize) / 2;
        g2.setColor(Color.WHITE);
        g2.fillOval(x, y, buttonSize, buttonSize);
    }
}