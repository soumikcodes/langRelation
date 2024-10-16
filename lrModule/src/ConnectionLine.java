import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class ConnectionLine extends Line {
    private String family;

    public ConnectionLine(String family) {
        this.family = family;
        setStroke(Color.BLUE);
        setStrokeWidth(2);
    }

    public String getFamily() {
        return family;
    }
}
