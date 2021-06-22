package pipeline;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import com.fxgraph.graph.CellType;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;

public class Main extends Application {
    Graph graph = new Graph();
    Button cnode, mnode, dnode;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        ToolBar toolbar = new ToolBar();
        VBox left = new VBox();
        VBox center = new VBox();
        VBox right = new VBox();
        HBox bottom = new HBox();
        root.setTop(toolbar);
        root.setLeft(left);
        root.setCenter(center);
        root.setRight(right);
        root.setBottom(bottom);

        graph = new Graph();
        root.setCenter(graph.getScrollPane());

        // create the node buttons, set text, set action
        cnode = new Button("Collection Node");
        cnode.setText("C");
        double r = 25;
        cnode.setShape(new Circle(r));
        cnode.setMinSize(2*r, 2*r);
        cnode.setMaxSize(2*r, 2*r);
        cnode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == cnode) {
                    System.out.println("Collection Node");
                }
            }
        });

        mnode = new Button("Model Node");
        mnode.setText("M");
        mnode.setShape(new Rectangle(100,100));
        mnode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == mnode) {
                    System.out.println("Model Node");
                }
            }
        });

        dnode = new Button("Data I/O Node");
        dnode.setText("D");
        double width = 50;
        double height = 50;
        dnode.setShape(new Polygon( width / 2, 0, width, height, 0, height));
        dnode.setText("D");
        dnode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == dnode) {
                    System.out.println("Data I/O Node");
                }
            }
        });

//        // create labels
//        Label label1 = new Label(" Workspace ");
//        Label label2 = new Label(" Function 1");
//        Label label3 = new Label(" Function 2");
//        Label label4 = new Label(" Function 3");
//
//        // manually set buttons instead of using VBox
//        cnode.setLayoutX(100);
//        cnode.setLayoutY(10);
//        mnode.setLayoutX(200);
//        mnode.setLayoutY(10);
//        dnode.setLayoutX(300);
//        dnode.setLayoutY(10);
//        label1.setLayoutX(700);
//        label1.setLayoutY(40);
//        label2.setLayoutX(200);
//        label2.setLayoutY(40);
//        label3.setLayoutX(200);
//        label3.setLayoutY(30);
//        label4.setLayoutX(200);
//        label4.setLayoutY(20);
//
//        center.getChildren().addAll(label1);
//        right.getChildren().addAll(label2, label3, label4);

        // drag and drop functions
        final Text function1 = new Text(50, 100, "Function 1");
        function1.setScaleX(2.0);
        function1.setScaleY(2.0);

        final Text function2 = new Text(50, 100, "Function 2");
        function2.setScaleX(2.0);
        function2.setScaleY(2.0);

        final Text function3 = new Text(50, 100, "Function 3");
        function3.setScaleX(2.0);
        function3.setScaleY(2.0);

        //change target to cnode, mnode, dnode
        final Text target = new Text(250, 100, " ADD");
        target.setScaleX(1.5);
        target.setScaleY(1.5);

        // left VBox with nodes (Edward drag drop)
        left.getChildren().addAll(cnode, mnode, dnode);
        cnode.setStyle("-fx-font-size: 1.5em; ");
        mnode.setStyle("-fx-font-size: 1.5em; ");
        dnode.setStyle("-fx-font-size: 1.5em; ");
        left.setStyle("-fx-border-color: black");
        left.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.25));
        left.setAlignment(Pos.CENTER);
        left.setSpacing(30);
//            DoubleProperty fontSize= new SimpleDoubleProperty(5); // font size in pt
//            root.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize));

        // right VBox with functions (Melinda drag drop)
        right.getChildren().addAll(function1, function2, function3);
        right.setStyle("-fx-border-color: black");
        right.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.25));
        right.setAlignment(Pos.CENTER);
        right.setSpacing(30);

        // mid VBox with labels
        center.getChildren().add(target);
        center.setSpacing(30);
        center.setStyle("-fx-border-color: black");
        center.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.5));
//            Line line1= new Line(600, 0, 600, 600);
//            center.getChildren().add(line1);
        center.setAlignment(Pos.CENTER);
        // set font size for label in VBox
        DoubleProperty fontSize3= new SimpleDoubleProperty(18); // font size in pt
        center.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize3));

        // drag and drop functions
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

        function2.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");

                /* allow any transfer mode */
                Dragboard db= function2.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content= new ClipboardContent();
                content.putString(function2.getText());
                db.setContent(content);

                event.consume();
            }
        });

        function3.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");

                /* allow any transfer mode */
                Dragboard db= function3.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content= new ClipboardContent();
                content.putString(function3.getText());
                db.setContent(content);

                event.consume();
            }
        });

        target.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                System.out.println("onDragOver");

                /* accept it only if it is  not dragged from the same node
                 * and if it has a string data */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        //            target.setOnDragEntered(new EventHandler<DragEvent>() {
//                @Override
//                public void handle(DragEvent event) {
//                    /* the drag-and-drop gesture entered the target */
//                    System.out.println("onDragEntered");
//                    /* show to the user that it is an actual gesture target */
//                    if (event.getGestureSource() != target &&
//                        event.getDragboard().hasString()) {
//                        root3.getChildren().add(label2);
//                    }
//
//                    event.consume();
//                }
//            });

//            target.setOnDragExited(new EventHandler<DragEvent>() {
//                @Override
//                public void handle(DragEvent event) {
//                    /* mouse moved away, remove the graphical cues */
//                    target.setFill(Color.BLACK);
//
//                    event.consume();
//                }
//            });

        target.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* data dropped */
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db= event.getDragboard();
                System.out.println(db.getString());
//                    root3.getChildren().add(label2);
                boolean success= false;
                if (db.getString().equals("Function 1")) {
                    root.getChildren().add(new Label(" Function 1"));
                }
                if (db.getString().equals("Function 2")) {
                    root.getChildren().add(new Label(" Function 2"));
                }
                if (db.hasString()) {
//                        target.setText(db.getString());
                    success= true;
                }

                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);
                event.consume();
            }
        });

        function1.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    function1.setText("");
                }
                event.consume();
            }
        });

        function2.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    function2.setText("");
                } event.consume();
            }
        });

        function3.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    function3.setText("");
                } event.consume();
            }
        });

        HBox hbox = new HBox();
        hbox.getChildren().addAll(root);

        Scene scene = new Scene(hbox, 900, 600);
        scene.getStylesheets().add(getClass().getResource("pipeline.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

        addGraphComponents();
//        Layout layout = new RandomLayout(graph);
//        layout.execute();
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

//        model.addEdge("Cell A", "Cell B");
//        model.addEdge("Cell B", "Cell C");
//        model.addEdge("Cell C", "Cell D");
//        model.addEdge("Cell D", "Cell E");
//        model.addEdge("Cell E", "Cell F");
//        model.addEdge("Cell F", "Cell G");

        graph.endUpdate();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
