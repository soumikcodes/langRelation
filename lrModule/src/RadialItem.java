import javax.swing.*;
import java.awt.*;

public class RadialItem {

    private Color color;
    private String name;

    public RadialItem(Color color, String name) {
        this.color = color;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
