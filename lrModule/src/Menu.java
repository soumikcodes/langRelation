import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Menu extends JPanel {
    private int buttonSize = 50;
    private int itemSize = 35;
    private float animateSize = 1f;

    private final List<RadialItem> items = new ArrayList<>();

    public Menu() {
        setBackground(new Color(20, 176, 211));
        setSize(buttonSize, buttonSize);

        addItem(new RadialItem(null, new Color(230, 130, 240)));
        addItem(new RadialItem(null, new Color(230, 130, 240)));
        addItem(new RadialItem(null, new Color(230, 130, 240)));
        addItem(new RadialItem(null, new Color(230, 130, 240)));
        addItem(new RadialItem(null, new Color(230, 130, 240)));

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

        if (!items.isEmpty()) {
            int width = getWidth();
            int height = getHeight();
            int size = (int) ((Math.min(width, height) / 2) - (itemSize/1.5f));
            int centerX = width / 2;
            int centerY = height / 2;
            float anglePerItem = 360f / items.size();
            float currentAnimate = animateSize;

            for (int i = 0; i < items.size(); i++) {
                RadialItem item = items.get(i);
                float angle = 90 + i * anglePerItem;
                Point location = toLocation(angle, size * currentAnimate);
                g2.setColor(item.getColor());
                int itemX = centerX + location.x - itemSize / 2;
                int itemY = centerY + location.y - itemSize / 2;
                g2.fillOval(itemX, itemY, itemSize, itemSize);
            }
        }

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

    private Point toLocation(float angle, double size) {
        int x = (int) (Math.cos(Math.toRadians(angle)) * size);
        int y = (int) (Math.sin(Math.toRadians(angle)) * size);
        return new Point(x,y);
    }

    public void addItem(RadialItem item) {
        items.add(item);
    }
}