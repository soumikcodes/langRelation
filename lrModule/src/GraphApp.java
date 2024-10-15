import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GraphApp extends Application {
    
    private double startX;
    private double startY;
    private Line connectionLine = new Line();
    private Pane root;

    public GraphApp() {
        root = new Pane();
        root.setPrefSize(800, 600);
        root.getChildren().forEach(this::makeDraggable);
    }

    @Override
    public void start(Stage stage) throws Exception {
    	stage.setTitle("Language Relation");
    }
    
    public void addNode(JFXPanel fxPanel) {
    	// Create a new LanguageNode each time this method is called
        LanguageNode newNode = new LanguageNode(25, Color.YELLOW, "IE");
        LanguageNode newNode1 = new LanguageNode(25, Color.GREEN, "IE");
        
        // Set initial position
        newNode.relocate(Math.random() * 50, Math.random() * 150);
        newNode1.relocate(Math.random() * 50, Math.random() * 150);
        
        // Add the new node to the pane
        root.getChildren().addAll(newNode, newNode1);
        root.getChildren().forEach(this::makeDraggable);
        
        // Add the connection line to the pane if not already added
        if (!root.getChildren().contains(connectionLine)) {
            connectionLine.setStroke(Color.BLUE);
            connectionLine.setStrokeWidth(2);
            root.getChildren().add(connectionLine);
        }

        // Only set the scene if it's not already set in the JFXPanel
        if (fxPanel.getScene() == null) {
            fxPanel.setScene(new Scene(root));
        }
        System.out.println(root.getChildren().size());
//        System.out.println(newNode.getLayoutBounds());
    }

    private void makeDraggable(Node node) {
        node.setOnMousePressed(e -> {
            startX = e.getSceneX() - node.getTranslateX();
            startY = e.getSceneY() - node.getTranslateY();
        });

        node.setOnMouseDragged(e -> {
            node.setTranslateX(e.getSceneX() - startX);
            node.setTranslateY(e.getSceneY() - startY);
            checkAndConnect(node);
        });
    }
    
    private void checkAndConnect(Node selectedNode) {
        double threshold = 100.0;

        if (!(selectedNode instanceof LanguageNode)) {
            return;
        }

        LanguageNode draggedCircle = (LanguageNode) selectedNode;
        ObservableList<Node> nodes = draggedCircle.getParent().getChildrenUnmodifiable();
        ObservableList<Node> circles = FXCollections.observableArrayList();
        
        // Filter only LanguageNode instances
        for (Node item : nodes) {
            if (item instanceof LanguageNode) {
                circles.add(item);
            }
        }

        for (Node node : circles) {
            LanguageNode otherCircle = (LanguageNode) node;

            if (draggedCircle == otherCircle) {
                continue;
            }

            if (draggedCircle.getFamily() != null && draggedCircle.getFamily().equals(otherCircle.getFamily())) {
                double centerX1 = draggedCircle.getLayoutX() + draggedCircle.getTranslateX() + draggedCircle.getRadius();
                double centerY1 = draggedCircle.getLayoutY() + draggedCircle.getTranslateY() + draggedCircle.getRadius();
                double centerX2 = otherCircle.getLayoutX() + otherCircle.getTranslateX() + otherCircle.getRadius();
                double centerY2 = otherCircle.getLayoutY() + otherCircle.getTranslateY() + otherCircle.getRadius();

                double dx = centerX2 - centerX1;
                double dy = centerY2 - centerY1;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < threshold) {
                    connectionLine.setStartX(centerX1);
                    connectionLine.setStartY(centerY1);
                    connectionLine.setEndX(centerX2);
                    connectionLine.setEndY(centerY2);
                    connectionLine.setVisible(true);
                    return;
                }
            }
        }

        connectionLine.setVisible(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
