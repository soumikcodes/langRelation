import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class LanguageNode extends Circle {
    private String family;

    public LanguageNode(double radius, double loc, Color fill, String family) {
    	super(radius, radius, radius, fill);
        this.family = family;
        this.setStroke(Color.BLACK);
        this.relocate(loc, loc);
        this.setStrokeWidth(5);
        this.getProperties().put("family", family);
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
        this.getProperties().put("family", family);
    }
}
