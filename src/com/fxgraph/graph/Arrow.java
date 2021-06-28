package com.fxgraph.graph;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class Arrow extends Group {
    protected Button source;
    protected Button target;
    Line line;

    public Arrow(Button source, Button target) {
        this.source = source;
        this.target = target;
        line = new Line();
        line.startXProperty().bind(source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0));
        line.startYProperty().bind(source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0));
        line.endXProperty().bind(target.layoutXProperty().add( target.getBoundsInParent().getWidth() / 2.0));
        line.endYProperty().bind(target.layoutYProperty().add( target.getBoundsInParent().getHeight() / 2.0));
        getChildren().add(line);
    }

}