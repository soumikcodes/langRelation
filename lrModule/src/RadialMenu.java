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


public class RadialMenu extends JComponent {
    private Home home;
    private int buttonSize = 50;
    private int itemSize = 35;
    private float animateSize = 0f;
    private Animator animator;
    private boolean isShowing;
    private boolean isMenuButtonHovered;
    private boolean isHoverOverPlus;
    private Color colorHover;
    private Color iconColor;
    private Color iconHoverColor;
    private int plusIndex;
    private int quitIndex;
    private int infoIndex;
    private float menuAngle = -150f;
    private int startingAngle = 60;
    private final List<RadialItem> menuItems = new ArrayList<>();
    private Timer closeTimer;
    private final int delay = 100;

    public RadialMenu(Home home) {
        this.home = home;

        setBackground(new Color(20, 176, 211));
        setForeground(new Color(250, 250, 250));
        iconColor = new Color(20, 15, 11);
        iconHoverColor = new Color(240, 150, 240);
        colorHover = new Color(42,205, 241);

        TimingTarget target = getAnimationTiming();
        animator = getAnimator(target);

        createRadialItems();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                boolean ifLeftMouseClick = SwingUtilities.isLeftMouseButton(e);
                if (ifLeftMouseClick) {
                    home.manageMenuHint();
                    isMenuButtonHovered = isMouseOverMenu(e);
                    int hoveredItemIndex = isMouseOverItem(e);
                    boolean isHoverOverQuit = hoveredItemIndex == quitIndex;
                    if (isMenuButtonHovered) {
                        openMenuItems();
                    } else if (isHoverOverPlus) {
                        openLanguagesPicker();
                    } else if (isHoverOverQuit) {
                        int userExitResponse = getExitConfirmationResponse();
                        boolean isExitResponsePositive = userExitResponse == JOptionPane.YES_OPTION;
                        if (isExitResponsePositive) {
                            exitApp();
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
                boolean wasHoverOverPlus = isHoverOverPlus;
                boolean isHoverOverMenuItems = overMenu || hoverIndex >= 0;

                isHoverOverPlus = (hoverIndex == plusIndex);

                if (isHoverOverPlus && !wasHoverOverPlus) {
                    Point point = calculatePlusButtonLocation();
                    home.showDropdownMenu(point.x, point.y);
                } else if (!isHoverOverPlus && wasHoverOverPlus) {
                    home.dropdownHide();
                }

                if (isHoverOverMenuItems) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    preventMenuFromClosing();
                    if (!isShowing) {
                        openMenuItems();
                    }
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    closeMenu();
                }
                if (overMenu != isMenuButtonHovered || (hoverIndex == plusIndex) == isHoverOverPlus) {
                    isMenuButtonHovered = overMenu;
                    repaint();
                }
            }
        });
        setDoubleBuffered(true);
    }

    private TimingTarget getAnimationTiming() {
        return new TimingTargetAdapter() {
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
                if (!isShowing) {
                    home.dropdownHide();
                }
                repaint();
            }
        };
    }

    private Animator getAnimator(TimingTarget target) {
        Animator animator = new Animator(500, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        return animator;
    }

    private void createRadialItems() {
        RadialItem languageItem = new RadialItem(new Color(230, 130, 240), "Add Language");
        RadialItem quitItem = new RadialItem(new Color(140, 245, 146), "Quit");
        RadialItem infoItem = new RadialItem(new Color(245, 209, 140), "Information");

        addItem(quitItem);
        addItem(languageItem);
        addItem(infoItem);

        plusIndex = menuItems.indexOf(languageItem);
        quitIndex = menuItems.indexOf(quitItem);
        infoIndex = menuItems.indexOf(infoItem);
    }

    private void openMenuItems() {
        if (!animator.isRunning()) {
            animator.start();
        }
    }

    private void openLanguagesPicker() {
        int width = getWidth();
        int height = getHeight();
        int size = (int) ((Math.min(width, height) / 2) - (itemSize / 1.5f));
        float anglePerItem = menuAngle / menuItems.size();
        float angle = startingAngle + plusIndex * anglePerItem;
        Point location = toLocation(angle, size * animateSize);
        int screenX = getLocationOnScreen().x + (width / 2) + location.x;
        int screenY = getLocationOnScreen().y + (height / 2) + location.y;
        home.showDropdownMenu(screenX, screenY);
    }

    private void exitApp() {
        System.exit(0);
    }

    private int getExitConfirmationResponse() {
        return JOptionPane.showConfirmDialog(null,
                "Are you sure you want to quit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
    }

    private void preventMenuFromClosing() {
        boolean isTimerRunning = closeTimer != null && closeTimer.isRunning();
        if (isTimerRunning) {
            closeTimer.stop();
        }
    }

    private void closeMenu() {
        boolean isAnimatorRunning = !animator.isRunning() && isShowing;
        boolean isCloseTimerRunning = closeTimer != null;
        if (isAnimatorRunning) {
            if (!isCloseTimerRunning) {
                startTimerAfterDelay();
                closeTimer.setRepeats(false);
            }
            closeTimer.restart();
        }
    }

    private void startTimerAfterDelay() {
        closeTimer = new Timer(delay, event -> {
            if (isShowing) {
                animator.start();
            }
        });
    }

    @Override
    public void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (!menuItems.isEmpty()) {
            int width = getWidth();
            int height = getHeight();
            int size = (int) ((Math.min(width, height) / 2) - (itemSize / 1.5f));
            int centerX = width / 2;
            int centerY = height / 2;
            float anglePerItem = menuAngle / menuItems.size();

            for (int menuItemIndex = 0; menuItemIndex < menuItems.size(); menuItemIndex++) {
                RadialItem menuItem = menuItems.get(menuItemIndex);
                float angle = startingAngle + menuItemIndex * anglePerItem;
                Point location = toLocation(angle, size * animateSize);
                int itemX = centerX + location.x - itemSize / 2;
                int itemY = centerY + location.y - itemSize / 2;
                boolean isItemPlus = menuItemIndex == plusIndex;
                boolean isItemQuit = menuItemIndex == quitIndex;
                boolean isItemInfo = menuItemIndex == infoIndex;


                if (isItemPlus && isHoverOverPlus) {
                    g2.setColor(iconHoverColor);
                } else {
                    g2.setColor(menuItem.getColor());
                }

                g2.fillOval(itemX, itemY, itemSize, itemSize);

                if (isShowing) {
                    String itemName = menuItem.getName();
                    showItemName(g2, itemName, itemX, itemY);
                }

                if (isItemPlus) {
                    drawPlus(g2, itemX, itemY);
                } else if (isItemQuit) {
                    drawCross(g2, itemX, itemY);
                } else if (isItemInfo) {
                    drawQuestion(g2, itemX, itemY);
                }
            }
        }
        createMenuButtons(g2);
        g2.dispose();
    }

    private void drawPlus(Graphics2D g2, int x, int y) {
        int plusSize = itemSize / 4;
        float thickness = 3.0f;
        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(iconColor);
        g2.drawLine(x + itemSize / 2 - plusSize, y + itemSize / 2,
                x + itemSize / 2 + plusSize, y + itemSize / 2);
        g2.drawLine(x + itemSize / 2, y + itemSize / 2 - plusSize,
                x + itemSize / 2, y + itemSize / 2 + plusSize);
    }

    private void showItemName(Graphics2D g2, String itemName, int x, int y) {
        g2.setFont(new Font("Georgia", Font.BOLD, 14));
        g2.setColor(Color.decode("#d9d7d7"));
        g2.drawString(itemName, x + 40, y + 22);
    }

    private void drawCross(Graphics2D g2, int x, int y) {
        int crossSize  = itemSize / 6;
        float thickness = 3.0f;
        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(iconColor);
        g2.drawLine(x + itemSize / 2 - crossSize, y + itemSize / 2 - crossSize,
                x + itemSize / 2 + crossSize, y + itemSize / 2 + crossSize);
        g2.drawLine(x + itemSize / 2 - crossSize, y + itemSize / 2 + crossSize,
                x + itemSize / 2 + crossSize, y + itemSize / 2 - crossSize);
    }

    private void drawQuestion(Graphics2D g2, int x, int y) {
        Font font = new Font("Georgia", Font.BOLD, itemSize / 2);
        g2.setFont(font);
        g2.setColor(iconColor);
        FontMetrics metrics = g2.getFontMetrics(font);
        int stringWidth = metrics.stringWidth("?");
        int stringHeight = metrics.getAscent();
        int questionX = x + (itemSize - stringWidth) / 2;
        int questionY = y + ((itemSize - stringHeight) / 2) + stringHeight;
        g2.drawString("?", questionX, questionY);
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

        if (isMenuButtonHovered) {
            g2.setColor(colorHover);
        } else {
            g2.setColor(getBackground());
        }

        startY += space;
        endY -= space;

        g2.fillOval(x, y, buttonSize, buttonSize);
        g2.setColor(getForeground());
        g2.setStroke(new BasicStroke(stroke));
        g2.drawLine(lineX, lineY - lineSpace, lineX + lineSize, startY);
        g2.drawLine(lineX, lineY + lineSpace, lineX + lineSize, endY);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.drawLine(lineX, lineY, lineX + lineSize, lineY);
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
        int index = -1;
        if (isShowing) {
            int width = getWidth();
            int height = getHeight();
            int centerX = width / 2;
            int centerY = height / 2;
            float anglePerItem = menuAngle / menuItems.size();
            int size = (int) ((Math.min(width, height) / 2) - (itemSize / 1.5f));

            for (int i = 0; i < menuItems.size(); i++) {
                float angle = startingAngle + i * anglePerItem;
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

    private Point toLocation(float angle, double size) {
        int x = (int) (Math.cos(Math.toRadians(angle)) * size);
        int y = (int) (Math.sin(Math.toRadians(angle)) * size);
        return new Point(x,y);
    }

    public void addItem(RadialItem item) {
        menuItems.add(item);
    }

    private Point calculatePlusButtonLocation() {
        int width = getWidth();
        int height = getHeight();
        int size = (int) ((Math.min(width, height) / 2) - (itemSize / 1.5f));
        float anglePerItem = menuAngle / menuItems.size();
        float angle = startingAngle + plusIndex * anglePerItem;
        Point location = toLocation(angle, size * animateSize);
        int screenX = (width / 2) + location.x;
        int screenY = (height / 2) + location.y + itemSize; // Adjust y to position below the "plus" button
        return new Point(screenX, screenY);
    }

}