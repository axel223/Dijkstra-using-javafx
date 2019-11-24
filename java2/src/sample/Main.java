package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Gui gui = new Gui();
        Pane root = new Pane();
        MenuBar mb = new MenuBar();
        mb.setPrefWidth(2160);

        GridPane EdgeUI = gui.createUI();

        Menu Vertex = new Menu("Vertex");
        Menu Edge = new Menu("Edge");
        Menu getPath = new Menu("Graph");
        Menu File = new Menu("File");

        MenuItem m1 = new MenuItem("Add Vertex");
        MenuItem m2 = new MenuItem("Search Vertex");
        MenuItem m3 = new MenuItem("Delete Vertex");
        MenuItem m4 = new MenuItem("Modify Vertex");

        MenuItem m5 = new MenuItem("Add Edge");
        MenuItem m6 = new MenuItem("Search Edge");
        MenuItem m7 = new MenuItem("Delete Edge");
        MenuItem m8 = new MenuItem("Modify Edge");

        MenuItem m9 = new MenuItem("Get Path");
        MenuItem m10 = new MenuItem("Import");
        MenuItem m11 = new MenuItem("Export");
        MenuItem m12 = new MenuItem("Show Graph");
        MenuItem m13 = new MenuItem("Animate path");

        // add menu items to menu
        Vertex.getItems().addAll(m1,m2,m3,m4);
        Edge.getItems().addAll(m5,m6,m7,m8);
        File.getItems().addAll(m10,m11);
        getPath.getItems().addAll(m9,m12,m13);

        // add menu to menu Bar
        mb.getMenus().add(Vertex);
        mb.getMenus().add(Edge);
        mb.getMenus().add(getPath);
        mb.getMenus().add(File);

        VBox vb = new VBox(mb);

        EventHandler<ActionEvent> event = e -> {
            Pane NodeUI = new Pane();
            if(e.getSource() == m1)
            {
                NodeUI = gui.createUI();
                gui.addNodeUIControls((GridPane) NodeUI,"Add Node");
            }
            if(e.getSource() == m2){
                NodeUI = gui.createUI();
                gui.addSearchUI((GridPane) NodeUI,"Search Node");
            }
            if(e.getSource() == m3){
                NodeUI = gui.createUI();
                gui.addSearchUI((GridPane) NodeUI,"Delete Node");
            }
            if(e.getSource() == m4){
                NodeUI = gui.createUI();
                gui.addNodeUIControls((GridPane) NodeUI,"Modify Node");
            }
            if(e.getSource() == m5){
                NodeUI = gui.createUI();
                gui.addEdgeUIControls((GridPane) NodeUI,"Add Edge");
            }
            if(e.getSource() == m6){
                NodeUI = gui.createUI();
                gui.addSearchEdgeUI((GridPane) NodeUI,"Search Edge");
            }
            if(e.getSource() == m7){
                NodeUI = gui.createUI();
                gui.addSearchEdgeUI((GridPane) NodeUI,"Delete Edge");
            }
            if(e.getSource() == m8){
                NodeUI = gui.createUI();
                gui.addEdgeUIControls((GridPane) NodeUI,"Modify Edge");
            }
            if(e.getSource() == m9){
                NodeUI = gui.createUI();
                gui.addSearchEdgeUI((GridPane) NodeUI,"Get Path");
            }
            if(e.getSource() == m10){
                NodeUI = gui.createUI();
                gui.addImportUI((GridPane) NodeUI,"Import");
            }
            if(e.getSource() == m11){
                NodeUI = gui.createUI();
                gui.addImportUI((GridPane) NodeUI,"Export");
            }
            if(e.getSource() == m12){
                gui.GraphGui(NodeUI);
            }
            if(e.getSource() == m13){
                gui.animateGui(NodeUI);
            }


            Group newRoot = new Group();
            newRoot.getChildren().addAll(NodeUI,vb);

            primaryStage.setTitle("GRAPH");
            primaryStage.setScene(new Scene(newRoot, 620, 500));
            primaryStage.show();

        };

        m1.setOnAction(event);
        m2.setOnAction(event);
        m3.setOnAction(event);
        m4.setOnAction(event);
        m5.setOnAction(event);
        m6.setOnAction(event);
        m7.setOnAction(event);
        m8.setOnAction(event);
        m9.setOnAction(event);
        m10.setOnAction(event);
        m11.setOnAction(event);
        m12.setOnAction(event);
        m13.setOnAction(event);

        root.getChildren().addAll(EdgeUI,vb);


        primaryStage.setTitle("GRAPH");
        primaryStage.setScene(new Scene(root, 720, 480));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
