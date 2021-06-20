package com.fxgraph.cells;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import com.fxgraph.graph.Cell;

public class RectangleCell extends Cell {
    public RectangleCell(String id) {
        super(id);
        Rectangle view = new Rectangle(50,50);
        view.setStroke(Color.BLACK);
        view.setFill(Color.DARKVIOLET);
        setView(view);
    }
}