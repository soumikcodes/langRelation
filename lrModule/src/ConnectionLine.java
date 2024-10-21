import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ConnectionLine extends Line {
    private String family;
    private Text label;

    public ConnectionLine(String family) {
        this.family = family;
        setStroke(Color.BLUE);
        setStrokeWidth(2);

        label = new Text(family);
        Font font = Font.font("Arial Black", FontWeight.BOLD, FontPosture.REGULAR, 12);
        label.setFont(font);
        label.setFill(Color.BLACK);
        label.setTranslateX(getStartX());
        label.setTranslateY(getStartY());
    }

    public String getFamily() {
        return family;
    }

    public Text getLabel() {
        return label;
    }

    // Method to update the label position based on the line's midpoint
    public void updateLabelPosition() {
        double midX = (getStartX() + getEndX()) / 2;
        double midY = (getStartY() + getEndY()) / 2;
        label.setTranslateX(midX);
        label.setTranslateY(midY);
    }
}
