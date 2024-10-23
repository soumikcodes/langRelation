import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.view.*;
import org.graphstream.ui.view.camera.Camera;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swing_viewer.ViewPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TemplateGS {
    SingleGraph myGraph;
    ViewPanel viewPanel;
    // Coordinates to track mouse dragging
    private Point3 lastMousePosition;
    private Node selectedNode = null;
    
    public TemplateGS() {
        myGraph = new SingleGraph("template graph");
        
        Node u = myGraph.addNode("u");
        Node v = myGraph.addNode("v");
        Viewer viewer = myGraph.display(false);
        View view = viewer.getDefaultView();

        viewer.getDefaultView().enableMouseOptions();
        
        u.setAttribute("ui.label", "u");
        v.setAttribute("ui.label", "v");

        myGraph.setAttribute("ui.stylesheet", "node { size: 50px; fill-color: blue; text-color: white; }");
        myGraph.setAttribute("ui.quality");
        myGraph.setAttribute("ui.antialias");
        myGraph.display(true);
    }

    
    public static void main(String[] args) {
    	System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        System.setProperty("org.graphstream.ui", "swing");
    }
    
    private void addMouseListeners(ViewPanel panel) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePressed(e);
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDragged(e);
            }
        });
    }
    
    private void handleMousePressed(MouseEvent e) {
        // Convert mouse position to graph coordinates
        Point mousePosition = e.getPoint();
        Camera camera = viewPanel.getCamera();
        Point3 xyz = camera.transformPxToGu(mousePosition.x, mousePosition.y);

        // Find closest node to the click
        selectedNode = findNodeAt(xyz);
        if (selectedNode != null) {
            lastMousePosition = xyz;
        }
    }
    
    private void handleMouseDragged(MouseEvent e) {
        if (selectedNode == null) {
            return;
        }

        // Convert the current mouse position to graph coordinates
        Point mousePosition = e.getPoint();
        Camera camera = viewPanel.getCamera();
        Point3 xyz = camera.transformPxToGu(mousePosition.x, mousePosition.y);
        
        // Calculate the difference in mouse movement and update node position
        double dx = xyz.x - lastMousePosition.x;
        double dy = xyz.y - lastMousePosition.y;

        double[] nodePos = (double[]) selectedNode.getAttribute("xyz");
        selectedNode.setAttribute("xyz", nodePos[0] + dx, nodePos[1] + dy, 0);

        lastMousePosition = xyz; // Update the last mouse position
    }
    
    private Node findNodeAt(Point3 position) {
        for (Node node : myGraph) {
            double[] nodePos = (double[]) node.getAttribute("xyz");
            double distance = Math.sqrt(Math.pow(nodePos[0] - position.x, 2) + Math.pow(nodePos[1] - position.y, 2));

            if (distance < 0.1) { // A threshold to consider a "hit"
                return node;
            }
        }
        return null;
    }

    public void drawGS(JFrame frame){

        JPanel panel = new JPanel(new GridLayout()) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(1300, 600);
            }
        };
        panel.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
        
        new TemplateGS();
        
        panel.add(viewPanel);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}