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

    public void addNode(JFXPanel fxPanel) {
        LanguageNode newNode = new LanguageNode(25, Math.random() * (fxPanel.getWidth()/2), randomColor(), "IE");
        root.getChildren().addAll(newNode);
        makeDraggable(newNode);

        if (fxPanel.getScene() == null) {
            fxPanel.setScene(new Scene(root));
        }
        System.out.println("Number of children: " + root.getChildren().size());

        newNode.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1)
                addNode(fxPanel);
        });
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

        for (Node item : nodes) {
            if (item instanceof LanguageNode) {
                circles.add(item);
            }
        }

        // Remove any existing connection lines associated with this node
        root.getChildren().removeIf(node -> node instanceof ConnectionLine && ((ConnectionLine) node).getFamily().equals(draggedCircle.getFamily()));

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
                    ConnectionLine line = new ConnectionLine(draggedCircle.getFamily());
                    line.setStartX(centerX1);
                    line.setStartY(centerY1);
                    line.setEndX(centerX2);
                    line.setEndY(centerY2);
                    root.getChildren().add(line);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
