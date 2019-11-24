package sample;

import javafx.animation.PathTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Stack;

class PrintGraph {
    private Graph graph;
    private ToggleButton addNode = new ToggleButton("Add Node");
    private ToggleButton deleteNode = new ToggleButton("Delete Node");
    private ToggleButton addEdge = new ToggleButton("Add Edge");
    private ToggleButton deleteEdge = new ToggleButton("Delete Edge");
    private DoubleProperty endX;
    private DoubleProperty endY;
    private Line newEdge ;

    private Slider slider = new Slider();
    private HBox buttonBox = new HBox();


    PrintGraph(Graph graph){
        this.graph = graph;
        ToggleGroup toggleButtons = new ToggleGroup();
        toggleButtons.getToggles().addAll(addNode,deleteNode,addEdge,deleteEdge);
        buttonBox.getChildren().addAll(addNode,deleteNode,addEdge,deleteEdge);
    }

    void showGraph(Pane pane){
        slider.setMin(0);
        slider.setMax(50);
        slider.setValue(5);
        slider.setLayoutX(10);
        slider.setLayoutY(30);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setBlockIncrement(5);
        buttonBox.setSpacing(10);
        buttonBox.setLayoutX(180);
        buttonBox.setLayoutY(30);

        Pane tempPane = new Pane();
        tempPane.setPrefWidth(2160);
        tempPane.setPrefHeight(1080);
        getGraphPane(tempPane,5,true);

        // Adding Listener to value property.
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            tempPane.getChildren().clear();
            getGraphPane(tempPane,(Double)newValue,true);
        });

        TextInputDialog td = new TextInputDialog("");
        td.setHeaderText("Enter Vertex name");

        tempPane.setOnMouseClicked(mouseEvent ->{
            if(addNode.isSelected()) {
                td.showAndWait();
                tempPane.getChildren().clear();
                graph.addNode(mouseEvent.getX() / slider.getValue(),mouseEvent.getY() / slider.getValue(),td.getEditor().getText());
                getGraphPane(tempPane,slider.getValue(),true);
//                tempPane.getChildren().add(newEdge);
            }
        });

        pane.getChildren().add(tempPane);
        pane.getChildren().add(slider);
        pane.getChildren().add(buttonBox);

    }

    private void enableDrag(Pane tempPane,final Circle circle,Node node,double scale) {
        final Delta dragDelta = new Delta();
        final String[] source = new String[1];
        circle.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            if(!addNode.isSelected() && !addEdge.isSelected() && !deleteNode.isSelected() && !deleteEdge.isSelected()) {
                dragDelta.x = circle.getCenterX() - mouseEvent.getX();
                dragDelta.y = circle.getCenterY() - mouseEvent.getY();
                circle.getScene().setCursor(Cursor.MOVE);
            }
            else if(addEdge.isSelected()){
                source[0] = node.name;
                newEdge = new Line();
                newEdge.setStartX(mouseEvent.getX());
                newEdge.setStartY(mouseEvent.getY());
                endX = new SimpleDoubleProperty(mouseEvent.getX());
                endY = new SimpleDoubleProperty(mouseEvent.getY());
                newEdge.endXProperty().bind(endX);
                newEdge.endYProperty().bind(endY);
                tempPane.getChildren().add(newEdge);
            }
            else if(deleteNode.isSelected()){
                graph.DeleteNode(node.name);
                tempPane.getChildren().clear();
                getGraphPane(tempPane,scale,true);
            }
        });
        circle.setOnMouseReleased(mouseEvent -> {
            if(addEdge.isSelected()) {
                endX.unbind();
                endY.unbind();
                TextInputDialog td = new TextInputDialog("");
                for (Node node1 : graph.getNodes()) {
                    if (isInside(mouseEvent.getX(), mouseEvent.getY(), node1,scale)) {
                        td.setHeaderText("Enter Weight");
                        td.showAndWait();
                        double weight = Double.parseDouble(td.getEditor().getText());
                        graph.addEdge(source[0], node1.name, weight);
                    }
                }
                tempPane.getChildren().clear();
                getGraphPane(tempPane,scale,true);

            }
            else if(!deleteNode.isSelected()){
                circle.getScene().setCursor(Cursor.HAND);
            }
        });
        circle.setOnMouseDragged(mouseEvent -> {
            if(!addNode.isSelected() && !addEdge.isSelected() && !deleteNode.isSelected() && !deleteEdge.isSelected()) {
                circle.setCenterX(mouseEvent.getX() + dragDelta.x);
                circle.setCenterY(mouseEvent.getY() + dragDelta.y);
                node.x = (mouseEvent.getX() + dragDelta.x) / scale;
                node.y = (mouseEvent.getY() + dragDelta.y) / scale;
            }
            if(addEdge.isSelected()){
                endX.set(mouseEvent.getX());
                endY.set(mouseEvent.getY());
            }

        });
        circle.setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                circle.getScene().setCursor(Cursor.HAND);
            }
        });
        circle.setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                circle.getScene().setCursor(Cursor.DEFAULT);
            }
        });

    }
    private void enableEdgeDelete(Pane pane,final Line line,Edge edge,double scale){
        line.setOnMouseReleased(event -> {
            line.getScene().setCursor(Cursor.HAND);
            if(deleteEdge.isSelected()){
                graph.DeleteEdge(edge.source.name,edge.destination.name);
                pane.getChildren().clear();
                getGraphPane(pane,scale,true);
            }
        });
        line.setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                line.getScene().setCursor(Cursor.HAND);
            }
        });
        line.setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                line.getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }

    private boolean isInside(double x1, double y1, Node node,double scale){
        double distance;
            distance = Math.sqrt((x1-(node.x*scale))*(x1-(node.x*scale))+(y1-(node.y*scale))*(y1-(node.y*scale)));
        return distance < 10;
    }

    private static class Delta { double x, y; }

    void getGraphPane(Pane pane, double scale,boolean enableTouch){

        ArrayList<Edge> edgeArrayList = new ArrayList<>();
        graph.getAdj().copyEdge(edgeArrayList);

        for(Edge e : edgeArrayList){
            pane.getChildren().addAll(e.getLine(),e.getText());
            if(enableTouch) {
                enableEdgeDelete(pane, e.getLine(), e, scale);
            }
        }
        for(int i=0;i<graph.getNodes().size();i++){
            pane.getChildren().add(graph.getNodes().get(i).getCircle(scale));
            pane.getChildren().add(graph.getNodes().get(i).getText());

            if(enableTouch) {
                enableDrag(pane, graph.getNodes().get(i).getCircle(scale), graph.getNodes().get(i), scale);
            }
        }
    }
    void animatePath(Pane pane,String from,String to,double scale,String shape){
        Node fromNode = graph.getNode(from);
        Path path = new Path();
        path.getElements().add(new MoveTo(fromNode.x*scale,fromNode.y*scale));
        Stack<Node> nodeStack = graph.getNodePath(from, to);
        if(nodeStack.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setContentText("No Path Exist");
            alert.initOwner(pane.getScene().getWindow());
            alert.show();
        }
        else {
            while (!nodeStack.isEmpty()) {
                Node node = nodeStack.pop();
                path.getElements().add(new LineTo(node.x * scale, node.y * scale));
            }
            graph.getAdj().resetNodesVisited();
            Shape shape1, shape2, area;
            PathTransition pathTransition = new PathTransition();
            switch (shape) {
                case "Circle":
                    shape1 = new Circle(fromNode.x * scale, fromNode.y * scale, 6, Color.BEIGE);
                    pane.getChildren().add(shape1);
                    pathTransition.setNode(shape1);
                    break;
                case "Square":
                    shape1 = new Rectangle(0, 0, 10, 10);
                    shape1.setFill(Color.DARKVIOLET);
                    pane.getChildren().add(shape1);
                    pathTransition.setNode(shape1);
                    break;
                case "Plus":
                    shape1 = new Line(5, 10, 15, 10);
                    shape2 = new Line(10, 5, 10, 15);
                    shape1.setStrokeWidth(5);
                    shape2.setStrokeWidth(5);
                    area = Shape.union(shape1, shape2);
                    area.setFill(Color.ROYALBLUE);
                    pane.getChildren().add(area);
                    pathTransition.setNode(area);
                    break;
                case "Cross":
                    shape1 = new Line(5, 5, 15, 15);
                    shape2 = new Line(5, 15, 15, 5);
                    shape1.setStrokeWidth(5);
                    shape2.setStrokeWidth(5);
                    area = Shape.union(shape1, shape2);
                    area.setFill(Color.DARKCYAN);
                    pane.getChildren().add(area);
                    pathTransition.setNode(area);
                    break;
                case "Triangle":
                    shape1 = new Polygon();
                    ((Polygon) shape1).getPoints().addAll(0.0, 10.0, 20.0, 10.0, 10.0, 20.0);
                    shape1.setFill(Color.GOLD);
                    pane.getChildren().add(shape1);
                    pathTransition.setNode(shape1);
                    break;
            }
            pathTransition.setDuration(Duration.seconds(2));
            pathTransition.setPath(path);
            pathTransition.setCycleCount(PathTransition.INDEFINITE);
            pathTransition.play();
        }
    }

}
