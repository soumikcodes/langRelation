import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LanguageNode extends Circle {
    private String languageName;
    private String family;
    private Text label;

    public LanguageNode(double radius, double loc, Color fill, String family, String languageName) {
        super(radius, radius, radius, fill);
        this.family = family;
        this.languageName = languageName;
        this.setStroke(Color.BLACK);
        this.relocate(loc, loc);
        this.setStrokeWidth(2);
        this.getProperties().put("family", family);
        this.getProperties().put("name", languageName);

        // Initialize and set up the language label
        label = new Text(languageName);
        Font font = Font.font("Arial Black", FontWeight.BOLD, FontPosture.REGULAR, 12);
        label.setFont(font);
        label.setFill(Color.BLACK);
        label.setStroke(Color.WHITE);
        label.setStrokeWidth(1);
        label.setTranslateX(this.getLayoutX());
        label.setTranslateY(this.getLayoutY());
    }

    public Text getLabel() {
        return label;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
        this.getProperties().put("family", family);
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String name) {
        this.languageName = name;
        this.getProperties().put("name", name);
        label.setText(name);
    }
}
