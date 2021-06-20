package com.fxgraph.cells;

import javafx.scene.image.ImageView;

import com.fxgraph.graph.Cell;

public class ImageCell extends Cell {
    public ImageCell(String id) {
        super(id);
        ImageView view = new ImageView("https://www.google.com/url?sa=i&url=https%3A%2F%2Flogos-download.com%2F45960-cornell-university-logo-download.html&psig=AOvVaw29iNrdzDrXlDXi4dCrOeBe&ust=1623967302639000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCMis8uiTnfECFQAAAAAdAAAAABAD");
        view.setFitWidth(100);
        view.setFitHeight(80);
        setView(view);
    }
}