import javax.swing.*;
import java.awt.*;

public class RadialItem {

    private Icon icon;
    private Color color;
    private Runnable action;

    public RadialItem(Icon icon, Color color) {
        this.icon = icon;
        this.color = color;
    }

    public Icon getIcon() {
        return icon;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }

    public void performAction() {
        if (action != null) {
            action.run();
        }
    }
}
