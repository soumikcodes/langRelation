import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;

public class TemplateGS {
    SingleGraph myGraph;
    public TemplateGS() {
        myGraph = new SingleGraph("template graph");
        Node u = myGraph.addNode("u");
        Node v = myGraph.addNode("v");

//        myGraph.forE
        Edge theedge = myGraph.addEdge("u--v", u.getId(),v.getId(), true);

        myGraph.display(true);
    }

    public JPanel getGraphPanel() {
        System.setProperty("org.graphstream.ui", "swing");
        Viewer viewer = myGraph.display(false);

        ViewPanel viewPanel = (ViewPanel) viewer.addDefaultView(false);

        JPanel graphPanel = new JPanel(new BorderLayout());
        graphPanel.add(viewPanel, BorderLayout.CENTER);

        return graphPanel;
    }
    /** the main just chooses the viewer and instantiates the class */
    public static void main(String[] args) {
//        System.setProperty("org.graphstream.ui.renderer",
//                "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        System.setProperty("org.graphstream.ui", "swing");
//        new TemplateGS();
    }

    public void drawGS(){
        new TemplateGS();
    }
}