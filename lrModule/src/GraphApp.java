import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class GraphApp extends Application {

    private double startX;
    private double startY;
    private Pane root;
    private LanguageNode selectedNode;
    private Map<String, ConnectionLine> connectionMap;

    public GraphApp() {
        root = new Pane();
        root.setPrefSize(800, 600);
        connectionMap = new HashMap<>();
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
    	return switch (language) {
        case "English", "German", "Afrikaans" -> "Germanic";
        case "Kazakh" -> "Indo-European";
        case "French", "Spanish", "Portuguese" -> "Italic";
        case "Russian", "Czech" -> "Balto-Slavic";
        case "Hindi", "Bengali", "Persian" -> "Indo-Aryan";
        default -> "";
    	};
    }

    public void addNode(String language, JFXPanel fxPanel) {
        LanguageNode newNode = new LanguageNode(35, Math.random() * (fxPanel.getWidth() / 2), randomColor(), setFamily(language), language);
        System.out.println("+++Add node: " + newNode.getProperties());
        root.getChildren().addAll(newNode, newNode.getLabel());
        makeDraggable(newNode);

        if (fxPanel.getScene() == null) {
            fxPanel.setScene(new Scene(root));
        }
        System.out.println("Number of children: " + root.getChildren().size());

        // Duplicate node
        newNode.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2)
                addNode(language, fxPanel);
        });
        
        // deleting the node with the delete key
        fxPanel.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE && selectedNode != null) {
            	System.out.println("---" + selectedNode.getLanguageName() + " Node Delete");
                deleteNode(selectedNode);
                selectedNode = null;
            }
        });
        
        //click anywhere on the scene to deselect node
        fxPanel.getScene().setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                // Check if the click target is not a LanguageNode
                if (!(e.getTarget() instanceof LanguageNode) && selectedNode != null) {
                    selectedNode.setStroke(Color.BLACK);
                    selectedNode = null; // Clear the reference to the selected node
                }
            }
        });
    }

    private void makeDraggable(LanguageNode node) {
        node.setOnMousePressed(e -> {
            startX = e.getSceneX() - node.getTranslateX();
            startY = e.getSceneY() - node.getTranslateY();
            if (selectedNode != null) {
                selectedNode.setStroke(Color.BLACK);
            }
            selectedNode = node;
            Color highlight = Color.rgb(112, 215, 255);
            node.setStroke(highlight);
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
        double threshold = 200.0;

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
        connectionMap.entrySet().removeIf(entry -> {
            ConnectionLine line = entry.getValue();
            if (line.getFamily().equals(draggedCircle.getFamily())) {
                root.getChildren().removeAll(line, line.getLabel());
                return true;
            }
            return false;
        });

        for (Node node : circles) {
            LanguageNode otherCircle = (LanguageNode) node;

            if (draggedCircle == otherCircle) {
                continue;
            }

            if (draggedCircle.getFamily() != null && draggedCircle.getFamily().equals(otherCircle.getFamily())
                    && !draggedCircle.getLanguageName().equals(otherCircle.getLanguageName())) {
                double centerX1 = draggedCircle.getLayoutX() + draggedCircle.getTranslateX() + draggedCircle.getRadius();
                double centerY1 = draggedCircle.getLayoutY() + draggedCircle.getTranslateY() + draggedCircle.getRadius();
                double centerX2 = otherCircle.getLayoutX() + otherCircle.getTranslateX() + otherCircle.getRadius();
                double centerY2 = otherCircle.getLayoutY() + otherCircle.getTranslateY() + otherCircle.getRadius();

                double dx = centerX2 - centerX1;
                double dy = centerY2 - centerY1;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < threshold) {
                    String connectionKey = draggedCircle.getLanguageName() + "-" + otherCircle.getLanguageName();
                    ConnectionLine line = new ConnectionLine(draggedCircle.getFamily());
                    line.setStartX(centerX1);
                    line.setStartY(centerY1);
                    line.setEndX(centerX2);
                    line.setEndY(centerY2);
                    line.updateLabelPosition();
                    connectionMap.put(connectionKey, line);
                    root.getChildren().addAll(line, line.getLabel());
                }
            }
        }
    }

    private void deleteNode(LanguageNode node) {
        // Remove the node and its label
        root.getChildren().removeAll(node, node.getLabel());

        // Remove all connections related to the node
        connectionMap.entrySet().removeIf(entry -> {
            ConnectionLine line = entry.getValue();
            if (line.getFamily().equals(node.getFamily())) {
                root.getChildren().removeAll(line, line.getLabel());
                return true;
            }
            return false;
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
