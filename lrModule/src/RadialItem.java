import javax.swing.*;
import java.awt.*;

public class RadialItem {
    private Icon icon;
    private Color color;

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
}
