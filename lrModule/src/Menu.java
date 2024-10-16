import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import static javax.swing.text.StyleConstants.setIcon;


public class Menu extends JComponent {

    public void setColorHover(Color colorHover) {
        this.colorHover = colorHover;
    }

    public void setIconColor(Color iconColor) {
        this.iconColor = iconColor;
    }

    public void setIconHoverColor(Color iconHoverColor) {
        this.iconHoverColor = iconHoverColor;
    }

    public int getButtonSize() {
        return buttonSize;
    }

    public void setButtonSize(int buttonSize) {
        this.buttonSize = buttonSize;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    private int buttonSize = 50;
    private int itemSize = 35;
    private float animateSize = 0f;

    private Animator animator;
    private boolean isShowing;
    private boolean isMouseOver;
    private boolean isHoverOverPlus;
    private Color colorHover;
    private Color iconColor;
    private Color iconHoverColor;

    private final List<RadialItem> items = new ArrayList<>();

    public Menu(Runnable addLanguageAction) {

        setBackground(new Color(20, 176, 211));
        setForeground(new Color(250, 250, 250));
        iconColor = new Color(20, 15, 11);
        iconHoverColor = new Color(240, 150, 240);
        colorHover = new Color(42,205, 241);

        TimingTarget target = new TimingTargetAdapter() {

            @Override
            public void timingEvent(float fraction) {
                if (isShowing) {
                    animateSize = 1f - fraction;
                } else {
                    animateSize = fraction;
                }
                repaint();
            }

          @Override
            public void end() {
                isShowing = !isShowing;
            }
        };

        animator = new Animator(500, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);

        RadialItem languageItem = new RadialItem(null, new Color(230, 130, 240));

        languageItem.setAction(addLanguageAction);

        addItem(languageItem);
        addItem(new RadialItem(null, new Color(156, 140, 245)));
        addItem(new RadialItem(null, new Color(245, 209, 140)));
        addItem(new RadialItem(null, new Color(140, 245, 146)));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    isMouseOver = isMouseOverMenu(e);
                    if (isMouseOver) {
                        if (!animator.isRunning()) {
                            animator.start();
                        }
                    } else if (isHoverOverPlus) {
                        languageItem.performAction();
                    } else {
                        int index = isMouseOverItem(e);
                        if (index >= 0) {
                            System.out.println("Selected index: " + index);
                        }
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean overMenu = isMouseOverMenu(e);
                int hoverIndex = isMouseOverItem(e);

                isHoverOverPlus = (hoverIndex == 0);

                if (overMenu || hoverIndex >= 0) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                if (overMenu != isMouseOver || (hoverIndex == 0) == isHoverOverPlus) {
                    isMouseOver = overMenu;
                    repaint();
                }
            }
        });

        setDoubleBuffered(true);

    }

    @Override
    public void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (!items.isEmpty()) {
            int width = getWidth();
            int height = getHeight();
            int size = (int) ((Math.min(width, height) / 2) - (itemSize / 1.5f));
            int centerX = width / 2;
            int centerY = height / 2;
            float anglePerItem = 360f / items.size();

            for (int i = 0; i < items.size(); i++) {
                RadialItem item = items.get(i);
                float angle = 90 + i * anglePerItem;
                Point location = toLocation(angle, size * animateSize);

                if (i == 0 && isHoverOverPlus) {
                    g2.setColor(iconHoverColor);
                } else {
                    g2.setColor(item.getColor());
                }

//                g2.setColor(item.getColor());

                int itemX = centerX + location.x - itemSize / 2;
                int itemY = centerY + location.y - itemSize / 2;
                g2.fillOval(itemX, itemY, itemSize, itemSize);
                // Create Icon
//                int iconX = itemX + ((itemSize - item.getIcon().getIconWidth())/2);
//                int iconY = itemY + ((itemSize - item.getIcon().getIconHeight())/2);
//                g2.drawImage(toImage(item.getIcon()), iconX, iconY, null);

                // add "plus"
                if (i == 0) {
                    int plusSize = itemSize / 4;
                    float thickness = 3.0f;

                    g2.setStroke(new BasicStroke(thickness));

                    g2.setColor(iconColor);

                    g2.drawLine(itemX + itemSize / 2 - plusSize, itemY + itemSize / 2,
                            itemX + itemSize / 2 + plusSize, itemY + itemSize / 2);
                    g2.drawLine(itemX + itemSize / 2, itemY + itemSize / 2 - plusSize,
                            itemX + itemSize / 2, itemY + itemSize / 2 + plusSize);
                }
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
        int stroke = 3;
        int lineSize = (int) (buttonSize - (buttonSize * 0.5f)); // 50% of button size
        int lineSpace = lineSize / 3;
        int lineX = (width - lineSize) / 2;
        int lineY = height / 2;
        int startY = lineY - lineSpace;
        int endY = lineY + lineSpace;
        double space = animateSize * (endY - startY);
        float alpha = 1f - animateSize;

        if (isMouseOver) {
            g2.setColor(colorHover);
        } else {
            g2.setColor(getBackground());
        }

        g2.fillOval(x, y, buttonSize, buttonSize);

        g2.setColor(getForeground());

        g2.setStroke(new BasicStroke(stroke));

        // cross lines on show
        startY += space;
        endY -= space;

        // draw menu icon
        g2.drawLine(lineX, lineY - lineSpace, lineX + lineSize, startY);
        g2.drawLine(lineX, lineY + lineSpace, lineX + lineSize, endY);

        // black out middle line on cross
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.drawLine(lineX, lineY, lineX + lineSize, lineY);
    }

    private Point toLocation(float angle, double size) {
        int x = (int) (Math.cos(Math.toRadians(angle)) * size);
        int y = (int) (Math.sin(Math.toRadians(angle)) * size);
        return new Point(x,y);
    }

    private boolean isMouseOverMenu(MouseEvent e) {
        int width = getWidth();
        int height = getHeight();
        int x = (width - buttonSize) / 2;
        int y = (height - buttonSize) / 2;
        Shape s = new Ellipse2D.Double(x, y, buttonSize, buttonSize);
        return s.contains(e.getPoint());
    }

    private int isMouseOverItem(MouseEvent e) {
        int index = 1;
        if (isShowing) {
            int width = getWidth();
            int height = getHeight();
            int centerX = width / 2;
            int centerY = height / 2;
            float anglePerItem = 360f / items.size();
            int size = (int) ((Math.min(width, height) / 2) - (itemSize / 1.5f));

            for (int i = 0; i < items.size(); i++) {
                float angle = 90 + i * anglePerItem;
                Point location = toLocation(angle, size * 1f);
                int itemX = centerX + location.x - itemSize / 2;
                int itemY = centerY + location.y - itemSize / 2;
                Shape shape = new Ellipse2D.Double(itemX, itemY, itemSize, itemSize);
                if (shape.contains(e.getPoint())) {
                    return i;
                }
            }
        }
        return index;
    }

    public void addItem(RadialItem item) {
        items.add(item);
    }

    public Image toImage(Icon icon) {
        return ((ImageIcon) icon).getImage();
    }
}