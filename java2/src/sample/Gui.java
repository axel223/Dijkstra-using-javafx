package sample;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.IOException;

class Gui {

    private Graph graph = new Graph();
    private PrintGraph printGraph = new PrintGraph(graph);
    GridPane createUI(){

        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        return gridPane;
    }
    void addNodeUIControls(GridPane gridPane ,String str) {
        // Add Name Label
        Label nameLabel = new Label("Vertex Name : ");
        gridPane.add(nameLabel, 0,2);
        TextField nameField = new TextField();
        nameField.setPromptText("Enter Vertex name");
        nameField.setPrefHeight(30);
        nameField.setPrefWidth(400);
        gridPane.add(nameField, 1,2,3,1);


        // Add X Label
        Label xLabel = new Label("X Coordinate : ");
        gridPane.add(xLabel, 0, 3);
        TextField xField = new TextField();
        xField.setPromptText("Enter X coordinate");
        xField.setPrefHeight(30);
        gridPane.add(xField, 1, 3);

        // Add Y Label
        Label yLabel = new Label("Y Coordinate: ");
        gridPane.add(yLabel, 2, 3);
        TextField yField = new TextField();
        yField.setPrefHeight(30);
        yField.setPromptText("Enter Y coordinate");
        gridPane.add(yField, 3, 3);

        // Add Node Button
        Button addNodeButton = new Button(str);
        addNodeButton.setPrefHeight(40);
        addNodeButton.setDefaultButton(true);
        addNodeButton.setPrefWidth(100);
        gridPane.add(addNodeButton, 0, 4, 4, 2);
        GridPane.setValignment(addNodeButton, VPos.CENTER);
        GridPane.setHalignment(addNodeButton, HPos.CENTER);

        addNodeButton.setOnAction(event -> {
            if(nameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter name");
            } else if(xField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter X coordinate");
            }else if(yField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter Y coordinate");
            }
            else{
                double x,y;
                try {
                    x = Double.parseDouble(xField.getText());
                    y = Double.parseDouble(yField.getText());
                }catch (NumberFormatException e){
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Invalid Input");
                    return;
                }
                if(str.equals("Add Node")) {
                    String output = graph.addNode(x,y,nameField.getText());
                    showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Addition Successful!", output);
                }
                else if(str.equals("Modify Node")) {
                    String output = graph.ModifyNode(nameField.getText(),x,y);
                    showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Modification Successful!", output);
                }
                nameField.setText(null);
                xField.setText(null);
                yField.setText(null);
            }
            //add node here
        });
    }
    void addSearchUI(GridPane gridPane,String str){


        // Add Name Label
        Label nameLabel = new Label("Vertex Name : ");
        gridPane.add(nameLabel, 0,2);
        TextField nameField = new TextField();
        nameField.setPrefHeight(30);
        nameField.setPrefWidth(400);
        gridPane.add(nameField, 1,2);

        Button SearchButton = new Button(str);
        SearchButton.setPrefHeight(30);
        SearchButton.setDefaultButton(true);
        SearchButton.setPrefWidth(100);
        gridPane.add(SearchButton, 0, 3,5,1);
        GridPane.setValignment(SearchButton, VPos.CENTER);
        GridPane.setHalignment(SearchButton, HPos.CENTER);

        SearchButton.setOnAction(event -> {
            if(nameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter name");
            }
            else {
                if (str.equals("Search Node")) {
                    String output = graph.SearchNode(nameField.getText());
                    showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Search", output);
                }
                if (str.equals("Delete Node")) {
                    String output = graph.DeleteNode(nameField.getText());
                    showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Deletion Successful!", output);
                }
                nameField.setText(null);
            }
            //add node here
        });

    }
    void addEdgeUIControls(GridPane gridPane,String str) {

        Label fromLabel = new Label("From Vertex : ");
        gridPane.add(fromLabel, 0,3);
        TextField fromField = new TextField();
        fromField.setPrefHeight(30);
        gridPane.add(fromField, 1,3);

        Label toLabel = new Label("To Vertex : ");
        gridPane.add(toLabel, 2,3);
        TextField toField = new TextField();
        toField.setPrefHeight(30);
        gridPane.add(toField, 3,3);

        Label weightLabel = new Label("Weight: ");
        gridPane.add(weightLabel, 0,2);
        TextField weightField = new TextField();
        weightField.setPrefHeight(30);
        weightField.setPrefWidth(400);
        gridPane.add(weightField, 1,2,3,1);

        // Add Node Button
        Button addEdgeButton = new Button(str);
        addEdgeButton.setPrefHeight(40);
        addEdgeButton.setDefaultButton(true);
        addEdgeButton.setPrefWidth(100);
        gridPane.add(addEdgeButton, 0, 4, 4, 2);
        GridPane.setValignment(addEdgeButton, VPos.CENTER);
        GridPane.setHalignment(addEdgeButton, HPos.CENTER);

        addEdgeButton.setOnAction(event -> {
            if(fromField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter from");
            } else if(toField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter to");
            }else if(weightField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter weight");
            }
            else {
                    double w;
                try {
                     w = Double.parseDouble(weightField.getText());
                }catch (NumberFormatException e){
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Invalid Input");
                    return;
                }

                if (str.equals("Add Edge")) {
                    String output = graph.addEdge(fromField.getText(),toField.getText(),w);
                    showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Addition Successful!", output);
                } else if (str.equals("Modify Edge")) {
                    String output = graph.ModifyEdge(fromField.getText(),toField.getText(),w);
                    showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Modification Successful!", output);
                }
                fromField.setText(null);
                toField.setText(null);
                weightField.setText(null);
            }
            //add node here
        });
    }
    void addSearchEdgeUI(GridPane gridPane,String str){


        // Add Name Label
        Label nameLabel = new Label("From Vertex : ");
        gridPane.add(nameLabel, 0,2);
        TextField nameField = new TextField();
        nameField.setPromptText(" Enter From Vertex");
        nameField.setPrefHeight(30);
        gridPane.add(nameField, 1,2);

        Label toLabel = new Label("To Vertex : ");
        gridPane.add(toLabel, 2,2);
        TextField toField = new TextField();
        toField.setPromptText("Enter to Vertex");
        toField.setPrefHeight(30);
        gridPane.add(toField, 3,2);

        Button SearchButton = new Button(str);
        SearchButton.setPrefHeight(30);
        SearchButton.setDefaultButton(true);
        SearchButton.setPrefWidth(100);
        gridPane.add(SearchButton, 0, 3, 4, 1);
        GridPane.setValignment(SearchButton, VPos.CENTER);
        GridPane.setHalignment(SearchButton, HPos.CENTER);

        SearchButton.setOnAction(event -> {
            if(nameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter name");
            }else if(toField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter name");
            }
            else {
                switch (str) {
                    case "Search Edge": {
                        String output = graph.SearchEdge(nameField.getText(), toField.getText());
                        showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Addition Successful!", output);
                        break;
                    }
                    case "Delete Edge": {
                        String output = graph.DeleteEdge(nameField.getText(), toField.getText());
                        showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Deletion Successful!", output);
                        break;
                    }
                    case "Get Path": {
                        String output = graph.getPath(nameField.getText(), toField.getText());
                        showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), " Path ", output);
                        break;
                    }
                }
                nameField.setText(null);
                toField.setText(null);
            }
            //add node here
        });
    }
    void addImportUI(GridPane gridPane,String str){

        Label nameLabel = new Label("Path : ");
        gridPane.add(nameLabel, 0,2);
        TextField nameField = new TextField();
        nameField.setPromptText(" Enter Path");
        nameField.setPrefHeight(30);
        nameField.setPrefWidth(400);
        gridPane.add(nameField, 2,2,4,1);

        Button SearchButton = new Button(str);
        SearchButton.setPrefHeight(30);
        SearchButton.setDefaultButton(true);
        SearchButton.setPrefWidth(100);
        gridPane.add(SearchButton, 2, 3, 4, 1);
        GridPane.setValignment(SearchButton, VPos.CENTER);
        GridPane.setHalignment(SearchButton, HPos.CENTER);

        SearchButton.setOnAction(event -> {
            if (nameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter Path");
            }
            else {
                String output = null;
                if(str.equals("Import")) {
                    output = graph.importFrom(nameField.getText());
                    showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Import  Successful!", output);
                }
                if(str.equals("Export")){
                    try {
                        output = graph.exportTo(nameField.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Export Successful!", output);
                }
                nameField.setText(null);
            }
            //add node here
        });

    }
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    void GraphGui(Pane pane){
        printGraph.showGraph(pane);
    }
    void animateGui(Pane pane){

        String[] typeOfNodes = {"Circle","Square","Plus","Cross","Triangle"};
        Label nameLabel = new Label("From Vertex : ");
        nameLabel.setLayoutX(10);
        nameLabel.setLayoutY(40);
        TextField nameField = new TextField();
        nameField.setPromptText("From");
        nameField.setPrefHeight(15);
        nameField.setPrefWidth(60);
        nameField.setLayoutX(85);
        nameField.setLayoutY(37);
        pane.getChildren().add(nameLabel);
        pane.getChildren().add(nameField);

        Label toLabel = new Label("To Vertex : ");
        toLabel.setLayoutX(155);
        toLabel.setLayoutY(40);
        TextField toField = new TextField();
        toField.setPromptText("To");
        toField.setPrefHeight(15);
        toField.setPrefWidth(60);
        toField.setLayoutX(215);
        toField.setLayoutY(37);
        pane.getChildren().addAll(toLabel,toField);

        Button SearchButton = new Button("PATH");
        SearchButton.setPrefHeight(15);
        SearchButton.setDefaultButton(true);
        SearchButton.setPrefWidth(60);
        SearchButton.setLayoutX(395);
        SearchButton.setLayoutY(37);
        pane.getChildren().add(SearchButton);

        final String[] str = new String[1];
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(typeOfNodes));
        EventHandler<ActionEvent> newEvent = e -> {
            str[0] = comboBox.getValue()+"";
            SearchButton.setOnAction(event -> printGraph.animatePath(pane,nameField.getText(),toField.getText(),5,str[0]));
        };

        comboBox.setLayoutX(285);
        comboBox.setLayoutY(37);
        comboBox.setOnAction(newEvent);
        pane.getChildren().add(comboBox);

        printGraph.getGraphPane(pane,5,false);
    }
}
