package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // SHAPE
        // CIRCLE, LINE, RECTANGLE, POLYGONE, POLYLINE, ARC
        // LAYOUT
        // Pane, StackPane - i qet krejt elementet ne mes, FlowPane - i rendit elementet 1 nga 1, VBox, HBox, BorderPane

        // VBox, HBox - vendosja e elementeve ne menyre vertikale dhe horizontale
        // BorderPane

        Pane pane = new Pane();
        Circle circle = new Circle();
        circle.setCenterX(250);
        circle.setCenterY(250);
        circle.setRadius(50);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        circle.setFill(Color.RED);
        circle.setRotate(10);

        pane.getChildren().add(circle);

        Line line = new Line();
        line.setStartX(5);
        line.setStartY(250);
        line.setEndX(495);
        line.setEndY(250);
        line.setStroke(Color.BLACK);

        Rectangle rectangle = new Rectangle(50, 50, 100, 50);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.RED);

        pane.getChildren().add(line);
        pane.getChildren().add(rectangle);

        Polygon polygon = new Polygon();
        polygon.getPoints().add(10.0);
        polygon.getPoints().add(20.0);
        polygon.getPoints().add(40.0);
        polygon.getPoints().add(80.0);
        polygon.getPoints().add(40.0);
        polygon.getPoints().add(20.0);
        polygon.getPoints().add(10.0);


        pane.getChildren().add(polygon);

        Polyline polyline = new Polyline(400.0, 400.0, 500.0, 625.0, 390.0, 620.0);

        pane.getChildren().add(polyline);

        Arc arc = new Arc();
        arc.setCenterX(200);
        arc.setCenterY(100);
        arc.setRadiusY(50);
        arc.setRadiusX(50);
        arc.setStartAngle(0);

        arc.setLength(70);
        arc.setStroke(Color.GREENYELLOW);
        arc.setType(ArcType.ROUND);

        pane.getChildren().add(arc);

        Triangle triangle = new Triangle(100.0, 300.0, 50.0, 400.0, 150.0,400.0);
        triangle.setFill(null);
        triangle.setStroke(Color.BLACK);
        pane.getChildren().add(triangle);

        Triangle triangle1 = new Triangle();




        Scene scene = new Scene(pane, 700, 700);

        stage.setScene(scene);
        stage.show();
    }

}

class Triangle extends Polygon {
    Triangle(double x1, double x2, double x3, double x4, double x5, double x6){
        super(x1, x2, x3, x4, x5, x6);
    }
}

class Custom extends Pane{
    Custom(){
        
    }
}