package pipeline;

import com.fxgraph.graph.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.text.Text;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application {
    private Button cnode, mnode, dnode;
    private double sceneX, sceneY, layoutX, layoutY;
    private Graph graph = new Graph();
    private Map<Label,Button> nodMap;
//    private CellLayer cellLayer;
//    private Group canvas;
//    private ZoomableScrollPane scrollPane;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        HBox toolbar = new HBox();
        VBox left = new VBox();
        Pane center = new Pane();
        VBox right = new VBox();
        HBox bottom = new HBox();
        root.setTop(toolbar);
        root.setLeft(left);
        root.setCenter(center);
        root.setRight(right);
        root.setBottom(bottom);

//        canvas = new Group();
//        cellLayer = new CellLayer();
//        canvas.getChildren().add(cellLayer);
//        scrollPane = new ZoomableScrollPane(canvas);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setFitToHeight(true);
//        graph = new Graph();
//        root.setCenter(graph.getScrollPane());

        // create the node buttons
        cnode = new Button("Collection Node");
        cnode.setText("C");
        cnode.setTooltip(new Tooltip("Info tip for c node: Collect output of functions applied to Mode or Data I/O Nodes."));
        double r = 25;
        cnode.setShape(new Circle(r));
        cnode.setMinSize(2*r, 2*r);
        cnode.setMaxSize(2*r, 2*r);

        mnode = new Button("Model Node");
        mnode.setText("M");
        mnode.setTooltip(new Tooltip("Info tip for m node: A saved model or code."));
        mnode.setShape(new Rectangle(100,100));

        dnode = new Button("Data I/O Node");
        dnode.setText("D");
        dnode.setTooltip(new Tooltip("Info tip for d node: Takes in data from external sources (sensors)."));
        double width = 50;
        double height = 50;
        dnode.setShape(new Polygon( width / 2, 0, width, height, 0, height));

        final Text function1 = new Text(50, 100, "Function");
        function1.setScaleX(2.0);
        function1.setScaleY(2.0);

        ObservableList<Label> source_nodes = FXCollections.observableArrayList();
        ComboBox sources_dropdown = new ComboBox(FXCollections.observableArrayList(source_nodes));
        ObservableList<Label> destination_nodes = FXCollections.observableArrayList();
        ComboBox destinations_dropdown = new ComboBox(FXCollections.observableArrayList(destination_nodes));

        final Text target = new Text(250, 100, " Workspace");
        target.setScaleX(1.5);
        target.setScaleY(1.5);

        // left VBox with nodes, conditional connectors, and function
        left.getChildren().addAll(function1, cnode, mnode, dnode, sources_dropdown, destinations_dropdown);
        cnode.setStyle("-fx-font-size: 1.5em; ");
        mnode.setStyle("-fx-font-size: 1.5em; ");
        dnode.setStyle("-fx-font-size: 1.5em; ");
        left.setStyle("-fx-border-color: black");
        left.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.25));
        left.setAlignment(Pos.CENTER);
        left.setSpacing(30);

        // right VBox with Node Properties and Function Properties
        right.setStyle("-fx-border-color: black");
        right.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.25));
        right.setAlignment(Pos.CENTER);
        right.setSpacing(30);

        // mid Pane
        center.setStyle("-fx-border-color: black");
        center.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.5));

        // top Pane with label
        toolbar.getChildren().add(target);
        toolbar.setAlignment(Pos.CENTER);
        DoubleProperty fontSize3= new SimpleDoubleProperty(18); // font size in pt
        center.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize3));

        // drag and drop functions
        cnode.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");

                /* allow any transfer mode */
                Dragboard db= cnode.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content= new ClipboardContent();
                content.putString(cnode.getText());
                db.setContent(content);
                event.consume();
            }
        });

        mnode.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture */
                System.out.println("onDragDetected");

                /* allow any transfer mode */
                Dragboard db= mnode.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content= new ClipboardContent();
                content.putString(mnode.getText());
                db.setContent(content);
                event.consume();
            }
        });

        dnode.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");

                /* allow any transfer mode */
                Dragboard db= mnode.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content= new ClipboardContent();
                content.putString(dnode.getText());
                db.setContent(content);
                event.consume();
            }
        });

        function1.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");

                /* allow any transfer mode */
                Dragboard db= function1.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content= new ClipboardContent();
                content.putString(function1.getText());
                db.setContent(content);
                event.consume();
            }
        });

        // to be dropped(target): center(workspace)in general, C/M/D labels
        center.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                System.out.println("onDragOver");

                /* accept it only if it is  not dragged from the same node
                 * and if it has a string data */
                if (event.getGestureSource() != center &&
                        event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        AtomicInteger nodCount = new AtomicInteger();

        center.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* data dropped */
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db= event.getDragboard();
                System.out.println(db.getString());

                boolean success= false;

                if (db.getString().equals("C")) {
                    Button cnod= new Button("Collection Node");
                    center.getChildren().add(cnod);
                    cnod.setText("C");
                    double r= 25;
                    cnod.setShape(new Circle(r));
                    cnod.setMinSize(2 * r, 2 * r);
                    cnod.setMaxSize(2 * r, 2 * r);
                    //initial position for added node
                    cnod.setLayoutX(150);
                    cnod.setLayoutY(150);

                    Label clbl = new Label("C Node Name");

                    cnod.setOnMousePressed(e -> {
                        if (e.getButton() == MouseButton.SECONDARY) {
                            System.out.println("Want to delete?");
                        }
                        if (e.getButton() == MouseButton.PRIMARY) {
                            sceneX= e.getSceneX();
                            sceneY= e.getSceneY();
                            System.out.println(sceneX);
                            System.out.println(sceneY);

                            layoutX= cnod.getLayoutX();
                            layoutY= cnod.getLayoutY();
                            System.out.println(
                                    cnod.getText() + " Box onStart :: layoutX ::" + layoutX +
                                            ", layoutY::" + layoutY);
                        }
                    });
                    cnod.setOnMouseDragged(e -> {
                        System.out.println(sceneX);
                        System.out.println(sceneY);
                        double offsetX= e.getSceneX() - sceneX;
                        double offsetY= e.getSceneY() - sceneY;
                        cnod.setTranslateX(offsetX);
                        cnod.setTranslateY(offsetY);
                    });
                    cnod.setOnMouseReleased(e -> {
                        if (e.getButton() == MouseButton.PRIMARY) {
                            // Updating the new layout positions
                            cnod.setLayoutX(layoutX + cnod.getTranslateX());
                            cnod.setLayoutY(layoutY + cnod.getTranslateY());
//                                clbl.setLayoutX(layoutX + cnod.getTranslateX());
//                                clbl.setLayoutY(layoutY + cnod.getTranslateY());

                            // Resetting the translate positions
                            cnod.setTranslateX(0);
                            cnod.setTranslateY(0);

                            nodCount.getAndIncrement();
                        }
                        if (e.getButton() == MouseButton.SECONDARY) {
                            // popup window
                            final Stage pop= new Stage();
                            pop.initModality(Modality.APPLICATION_MODAL);
                            pop.initOwner(primaryStage);
                            Pane popbox= new Pane();
//                                VBox popbox= new VBox(20);
                            Label msg= new Label("Want to delete?");
                            msg.setScaleX(1.7);
                            msg.setScaleY(1.7);
                            msg.setLayoutX(110);
                            msg.setLayoutY(60);

                            Button yes= new Button("Yes");
                            yes.setPrefSize(60, 30);
                            yes.setLayoutX(70);
                            yes.setLayoutY(120);
                            yes.setOnAction(e1 -> {
                                pop.close();
                                cnod.setVisible(false);
                                cnod.managedProperty().bind(cnod.visibleProperty());
//                                    clbl.setVisible(false);
//                                    clbl.managedProperty().bind(cnod.visibleProperty());
                            });
                            Button no= new Button("No");
                            no.setPrefSize(60, 30);
                            no.setLayoutX(180);
                            no.setLayoutY(120);
                            no.setOnAction(e2 -> {
                                pop.close();
                            });
                            popbox.getChildren().addAll(msg, yes, no);
                            Scene popScene= new Scene(popbox, 300, 200);
                            pop.setScene(popScene);
                            pop.show();
                        }
                    });
                    cnod.setOnAction(e -> {
                        System.out.println("Button pressed " + ((Button) e.getSource()).getText());
                        // TODO: Showing the properties of the node on the right
                        VBox property = new VBox();
                        root.setRight(property);
                        final Text property_title = new Text(50, 100, "Property for C node");
                        property_title.setScaleX(2.0);
                        property_title.setScaleY(2.0);
                        final Text property1 = new Text(50, 100, "Name:");
                        TextField name = new TextField();
//                        cnod.setText(name.getText());
                        final Text property2 = new Text(50, 100, "Path:");
                        TextField path = new TextField();
                        property.getChildren().addAll(property_title, property1, name, property2, path);
                        property.setStyle("-fx-border-color: black");
                        property.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.25));
                        property.setAlignment(Pos.CENTER);
                        property.setSpacing(30);
                    });

                    source_nodes.add(clbl);
                    sources_dropdown.setItems(source_nodes);

                    destination_nodes.add(clbl);
                    destinations_dropdown.setItems(destination_nodes);

                    nodMap.put(clbl, cnod);
                }

                if (db.getString().equals("M")) {
                    Button mnod = new Button("M");
                    mnod.setText("M");
                    mnod.setShape(new Rectangle(100, 100));
                    center.getChildren().add(mnod);
                    //initial position for added node
                    mnod.setLayoutX(150);
                    mnod.setLayoutY(150);

                    Label mlbl = new Label("M Node Name");

                    mnod.setOnMousePressed(e -> {
                        sceneX= e.getSceneX();
                        sceneY= e.getSceneY();
                        layoutX= mnod.getLayoutX();
                        layoutY= mnod.getLayoutY();
                        System.out
                                .println(mnod.getText() + " Box onStart :: layoutX ::" + layoutX +
                                        ", layoutY::" + layoutY);
                    });
                    mnod.setOnMouseDragged(e -> {
                        double offsetX= e.getSceneX() - sceneX;
                        double offsetY= e.getSceneY() - sceneY;
                        mnod.setTranslateX(offsetX);
                        mnod.setTranslateY(offsetY);
                    });
                    mnod.setOnMouseReleased(e -> {
                        if (e.getButton() == MouseButton.PRIMARY) {
                            // Updating the new layout positions
                            mnod.setLayoutX(layoutX + mnod.getTranslateX());
                            mnod.setLayoutY(layoutY + mnod.getTranslateY());

                            // Resetting the translate positions
                            mnod.setTranslateX(0);
                            mnod.setTranslateY(0);

                            nodCount.getAndIncrement();
                        }
                        if (e.getButton() == MouseButton.SECONDARY) {
                            // popup window
                            final Stage pop= new Stage();
                            pop.initModality(Modality.APPLICATION_MODAL);
                            pop.initOwner(primaryStage);
                            Pane popbox= new Pane();
//                                VBox popbox= new VBox(20);
                            Label msg= new Label("Want to delete?");
                            msg.setScaleX(1.7);
                            msg.setScaleY(1.7);
                            msg.setLayoutX(110);
                            msg.setLayoutY(60);

                            Button yes= new Button("Yes");
                            yes.setPrefSize(60, 30);
                            yes.setLayoutX(70);
                            yes.setLayoutY(120);
                            yes.setOnAction(e1 -> {
                                pop.close();
                                mnod.setVisible(false);
                                mnod.managedProperty().bind(mnod.visibleProperty());
//                                    clbl.setVisible(false);
//                                    clbl.managedProperty().bind(cnod.visibleProperty());
                            });
                            Button no= new Button("No");
                            no.setPrefSize(60, 30);
                            no.setLayoutX(180);
                            no.setLayoutY(120);
                            no.setOnAction(e2 -> {
                                pop.close();
                            });
                            popbox.getChildren().addAll(msg, yes, no);
                            Scene popScene= new Scene(popbox, 300, 200);
                            pop.setScene(popScene);
                            pop.show();
                        }
                    });
                    mnod.setOnAction(e -> {
                        System.out.println("Button pressed " + ((Button) e.getSource()).getText());
                        // TODO: Showing the properties of the node on the right
                        VBox property = new VBox();
                        root.setRight(property);
                        final Text property_title = new Text(50, 100, "Property for M node");
                        property_title.setScaleX(2.0);
                        property_title.setScaleY(2.0);
                        final Text property1 = new Text(50, 100, "Name:");
                        TextField name = new TextField();
//                        mnod.setText(name.getText());
                        final Text property2 = new Text(50, 100, "Path:");
                        TextField path = new TextField();
                        property.getChildren().addAll(property_title, property1, name, property2, path);
                        property.setStyle("-fx-border-color: black");
                        property.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.25));
                        property.setAlignment(Pos.CENTER);
                        property.setSpacing(30);
                    });

                    source_nodes.add(mlbl);
                    sources_dropdown.setItems(source_nodes);

                    destination_nodes.add(mlbl);
                    destinations_dropdown.setItems(destination_nodes);

                    nodMap.put(mlbl, mnod);
                }

                if (db.getString().equals("D")) {
                    Button dnod = new Button("D");
                    dnod.setText("D");
                    double width= 50;
                    double height= 50;
                    dnod.setShape(new Polygon(width / 2, 0, width, height, 0, height));
                    center.getChildren().add(dnod);
                    //initial position for added node
                    dnod.setLayoutX(150);
                    dnod.setLayoutY(150);

                    Label dlbl = new Label("D Node Name");

                    dnod.setOnMousePressed(e -> {
                        sceneX= e.getSceneX();
                        sceneY= e.getSceneY();
                        layoutX= dnod.getLayoutX();
                        layoutY= dnod.getLayoutY();
                        System.out
                                .println(dnod.getText() + " Box onStart :: layoutX ::" + layoutX +
                                        ", layoutY::" + layoutY);
                    });
                    dnod.setOnMouseDragged(e -> {
                        double offsetX= e.getSceneX() - sceneX;
                        double offsetY= e.getSceneY() - sceneY;
                        dnod.setTranslateX(offsetX);
                        dnod.setTranslateY(offsetY);
                    });
                    dnod.setOnMouseReleased(e -> {
                        if (e.getButton() == MouseButton.PRIMARY) {
                            // Updating the new layout positions
                            dnod.setLayoutX(layoutX + dnod.getTranslateX());
                            dnod.setLayoutY(layoutY + dnod.getTranslateY());

                            // Resetting the translate positions
                            dnod.setTranslateX(0);
                            dnod.setTranslateY(0);

                            nodCount.getAndIncrement();
                        }
                        if (e.getButton() == MouseButton.SECONDARY) {
                            // popup window
                            final Stage pop= new Stage();
                            pop.initModality(Modality.APPLICATION_MODAL);
                            pop.initOwner(primaryStage);
                            Pane popbox= new Pane();
//                                VBox popbox= new VBox(20);
                            Label msg= new Label("Want to delete?");
                            msg.setScaleX(1.7);
                            msg.setScaleY(1.7);
                            msg.setLayoutX(110);
                            msg.setLayoutY(60);

                            Button yes= new Button("Yes");
                            yes.setPrefSize(60, 30);
                            yes.setLayoutX(70);
                            yes.setLayoutY(120);
                            yes.setOnAction(e1 -> {
                                pop.close();
                                dnod.setVisible(false);
                                dnod.managedProperty().bind(dnod.visibleProperty());
//                                    clbl.setVisible(false);
//                                    clbl.managedProperty().bind(cnod.visibleProperty());
                            });
                            Button no= new Button("No");
                            no.setPrefSize(60, 30);
                            no.setLayoutX(180);
                            no.setLayoutY(120);
                            no.setOnAction(e2 -> {
                                pop.close();
                            });
                            popbox.getChildren().addAll(msg, yes, no);
                            Scene popScene= new Scene(popbox, 300, 200);
                            pop.setScene(popScene);
                            pop.show();
                        }
                    });
                    dnod.setOnAction(e -> {
                        System.out.println("Button pressed " + ((Button) e.getSource()).getText());
                        // TODO: Showing the properties of the node on the right
                        VBox property = new VBox();
                        root.setRight(property);
                        final Text property_title = new Text(50, 100, "Property for D node");
                        property_title.setScaleX(2.0);
                        property_title.setScaleY(2.0);
                        final Text property1 = new Text(50, 100, "Name:");
                        TextField name = new TextField();
//                        dnod.setText(name.getText());
                        final Text property2 = new Text(50, 100, "Path:");
                        TextField path = new TextField();
                        property.getChildren().addAll(property_title, property1, name, property2, path);
                        property.setStyle("-fx-border-color: black");
                        property.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.25));
                        property.setAlignment(Pos.CENTER);
                        property.setSpacing(30);
                    });

                    source_nodes.add(dlbl);
                    sources_dropdown.setItems(source_nodes);

                    destination_nodes.add(dlbl);
                    destinations_dropdown.setItems(destination_nodes);

                    nodMap.put(dlbl, dnod);
                }

                Label selected_source_label = new Label();
                Label selected_destination_label = new Label();

                if (nodCount.get() >= 2) {
                    EventHandler<ActionEvent> source_selection = new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {
                            selected_source_label.setText(sources_dropdown.getValue() + "");
                            source_nodes.remove(selected_source_label);
                            sources_dropdown.setItems(source_nodes);
                        }
                    };
                    sources_dropdown.setOnAction(source_selection);

                    EventHandler<ActionEvent> destination_selection = new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {
                            selected_destination_label.setText(destinations_dropdown.getValue() + "");
                            destination_nodes.remove(selected_destination_label);
                            destinations_dropdown.setItems(destination_nodes);
                        }
                    };
                    destinations_dropdown.setOnAction(destination_selection);

                    center.getChildren().add(addArrow(selected_source_label, selected_destination_label));
                }

                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);
                event.consume();
            }
        });

        cnode.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    cnode.setText("");
                }
                event.consume();
            }
        });

        mnode.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    mnode.setText("");
                }
                event.consume();
            }
        });

        dnode.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    dnode.setText("");
                }
                event.consume();
            }
        });

        HBox hbox = new HBox();
        hbox.getChildren().addAll(root);

        Scene scene = new Scene(hbox, 900, 600);
        scene.getStylesheets().add(getClass().getResource("pipeline.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

//        addGraphComponents();
    }

    public Arrow addArrow(Label sourceId, Label targetId) {
        try {
            Button sourceCell = nodMap.get(sourceId);
            nodMap.remove(sourceId);
            Button targetCell = nodMap.get(targetId);
            Arrow edge = new Arrow(sourceCell, targetCell);
            return edge;
        } catch (Exception e) {
            return null;
        }
    }

    private void addGraphComponents() {
        Model model = graph.getModel();
        graph.beginUpdate();

        model.addCell("Cell A", CellType.RECTANGLE);
        model.addCell("Cell B", CellType.RECTANGLE);
        model.addCell("Cell C", CellType.TRIANGLE);
        model.addCell("Cell D", CellType.TRIANGLE);
        model.addCell("Cell E", CellType.CIRCLE);
        model.addCell("Cell F", CellType.CIRCLE);
        model.addCell("Cell G", CellType.CIRCLE);

        model.addEdge("Cell A", "Cell B");
        model.addEdge("Cell B", "Cell C");
        model.addEdge("Cell C", "Cell D");
        model.addEdge("Cell D", "Cell E");
        model.addEdge("Cell E", "Cell F");
        model.addEdge("Cell F", "Cell G");

        graph.endUpdate();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
