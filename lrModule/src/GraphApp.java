import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GraphApp extends Application {

    private double startX;
    private double startY;
    private Pane root;

    public GraphApp() {
        root = new Pane();
        root.setPrefSize(800, 600);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Language Relation");
    }

    private Color randomColor() {
        double red = Math.random();
        double green = Math.random();
        double blue = Math.random();
        return new Color(red, green, blue, 1.0);
    }
    
    private String setFamily(String language) {
    	String fam = "";
    	if(language == "English")
    		fam = "Germanic";
    	if(language == "Kazakh")
    		fam = "Indo-European";
    	if(language == "French")
    		fam = "Italic";
    	if(language == "Russian")
    		fam = "Balto-Slavic";
    	if(language == "Hindi")
    		fam = "Indo-Aryan";
    	if(language == "German")
    		fam = "Germanic";
    	if(language == "Bengali")
    		fam = "Indo-Aryan";
    	if(language == "Czech")
    		fam = "Balto-Slavic";
    	if(language == "Spanish")
    		fam = "Italic";
    	if(language == "Afrikaans")
    		fam = "Germanic";
    	if(language == "Portuguese")
    		fam = "Italic";
    	
    	return fam;
    }

    public void addNode(String language, JFXPanel fxPanel) {
        LanguageNode newNode = new LanguageNode(35, Math.random() * (fxPanel.getWidth()/2), randomColor(), setFamily(language), language);
        System.out.println(newNode.getProperties());
        root.getChildren().addAll(newNode, newNode.getLabel());
        makeDraggable(newNode);

        if (fxPanel.getScene() == null) {
            fxPanel.setScene(new Scene(root));
        }
        System.out.println("Number of children: " + root.getChildren().size());

        newNode.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2)
                addNode(language, fxPanel);
        });
    }

    private void makeDraggable(LanguageNode node) {
        node.setOnMousePressed(e -> {
            startX = e.getSceneX() - node.getTranslateX();
            startY = e.getSceneY() - node.getTranslateY();
        });

        node.setOnMouseDragged(e -> {
            node.setTranslateX(e.getSceneX() - startX);
            node.setTranslateY(e.getSceneY() - startY);
            node.getLabel().setLayoutX(node.getTranslateX() + node.getRadius());
            node.getLabel().setLayoutY(node.getTranslateY() + node.getRadius());
            
            checkAndConnect(node);
        });
    }

    private void checkAndConnect(Node selectedNode) {
        double threshold = 500.0;

        if (!(selectedNode instanceof LanguageNode)) {
            return;
        }

        LanguageNode draggedCircle = (LanguageNode) selectedNode;
        ObservableList<Node> nodes = draggedCircle.getParent().getChildrenUnmodifiable();
        ObservableList<Node> circles = FXCollections.observableArrayList();

        for (Node item : nodes) {
            if (item instanceof LanguageNode) {
                circles.add(item);
            }
        }

        // Remove any existing connection lines and their labels associated with this node
        root.getChildren().removeIf(node -> node instanceof ConnectionLine 
            && ((ConnectionLine) node).getFamily().equals(draggedCircle.getFamily()));

        for (Node node : circles) {
            LanguageNode otherCircle = (LanguageNode) node;

            if (draggedCircle == otherCircle) {
                continue;
            }

            if (draggedCircle.getFamily() != null && draggedCircle.getFamily().equals(otherCircle.getFamily())
            		&& draggedCircle.getLanguageName() != otherCircle.getLanguageName()) {
                double centerX1 = draggedCircle.getLayoutX() + draggedCircle.getTranslateX() + draggedCircle.getRadius();
                double centerY1 = draggedCircle.getLayoutY() + draggedCircle.getTranslateY() + draggedCircle.getRadius();
                double centerX2 = otherCircle.getLayoutX() + otherCircle.getTranslateX() + otherCircle.getRadius();
                double centerY2 = otherCircle.getLayoutY() + otherCircle.getTranslateY() + otherCircle.getRadius();

                double dx = centerX2 - centerX1;
                double dy = centerY2 - centerY1;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < threshold) {
                    ConnectionLine line = new ConnectionLine(draggedCircle.getFamily());
                    line.setStartX(centerX1);
                    line.setStartY(centerY1);
                    line.setEndX(centerX2);
                    line.setEndY(centerY2);
                    line.updateLabelPosition();
                    root.getChildren().addAll(line, line.getLabel());
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
