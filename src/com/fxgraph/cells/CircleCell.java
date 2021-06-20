package com.fxgraph.cells;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import com.fxgraph.graph.Cell;

public class CircleCell extends Cell {
    public CircleCell(String id) {
        super(id);
        Circle view = new Circle();
        view.setCenterX(50.0f);
        view.setCenterY(50.0f);
        view.setRadius(25.0f);
        view.setStroke(Color.BLACK);
        view.setFill(Color.GREENYELLOW);
        setView(view);
    }
}
