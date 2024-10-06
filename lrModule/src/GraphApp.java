import javafx.application.*;
import javafx.embed.swing.JFXPanel;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;

public class GraphApp extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
	}
	
    public void addGrpah(JFXPanel fxPanel) {
    	fxPanel.setScene(new Scene(createContent()));
    }

    private Parent createContent() {
        Circle c1 = new Circle(25, 25, 25, Color.YELLOW);
        Circle c2 = new Circle(150, 30, 25, Color.RED);
//        Label control = new Label("Hello World");
//        control.setFont(Font.font(42));

        c1.setTranslateX(50);
        c1.setTranslateY(50);
        c1.setStroke(Color.BLACK);
        c1.setStrokeWidth(5);
        
        c2.setTranslateX(50);
        c2.setTranslateY(150);
        c2.setStroke(Color.BLACK);
        c2.setStrokeWidth(5);

//        control.setTranslateX(250);
//        control.setTranslateY(150);

        var root = new Pane(c1, c2);
        root.setPrefSize(800, 600);

        root.getChildren().forEach(this::makeDraggable);

        return root;
    }

    private double startX;
    private double startY;

    private void makeDraggable(Node node) {

        // a = b - c
        // a + c = b
        // c = b - a

        node.setOnMousePressed(e -> {
            startX = e.getSceneX() - node.getTranslateX();
            startY = e.getSceneY() - node.getTranslateY();
        });

        node.setOnMouseDragged(e -> {
            node.setTranslateX(e.getSceneX() - startX);
            node.setTranslateY(e.getSceneY() - startY);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}