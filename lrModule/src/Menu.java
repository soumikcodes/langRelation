import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Menu extends JPanel {
    private int buttonSize = 50;

    public Menu() {
        setBackground(new Color(20, 176, 211));
        setSize(buttonSize, buttonSize);

//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println("click");
//            }
//        });
    }

    @Override
    public void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();

        createMenuButtons(g2);
        g2.dispose();
    }

    private void createMenuButtons(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();
        int x = (width - buttonSize) / 2;
        int y = (height - buttonSize) / 2;
        g2.setColor(getBackground());
        g2.fillOval(x, y, buttonSize, buttonSize);
    }
}