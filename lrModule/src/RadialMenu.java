import java.awt.*;

public class MenuForm {
    private int buttonSize = 50;

    public MenuForm() {
        setBackground(new Color(20, 176, 211));
        

    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setReminderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
    }

    private void createMenuButtons(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();
        int x = (width - buttonSize) / 2;
        int y = (height - buttinZise) / 2;
        g2.setColor(getBackground());
        g2.fillOval(x, y, buttonSize, buttonSize);
    }
}
