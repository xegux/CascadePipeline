package pipeline;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
import com.fxgraph.layout.base.Layout;
import com.fxgraph.layout.random.RandomLayout;

public class Main extends Application {
    Graph graph = new Graph();
    Button button;
    Button button2;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        graph = new Graph();

        root.setCenter(graph.getScrollPane());

        // create button, set text, set action
        button = new Button();
        button.setText("yes");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == button) {
                    System.out.println("You clicked button.");
                }
            }
        });

        // use lambda inner function to set action for button2
        button2 = new Button();
        button2.setText("cancel");
        button2.setOnAction(e -> System.out.println("drink bubble tea"));

        // create labels
        Label label1 = new Label(" Workspace ");
        Label label2 = new Label(" Function 1");
        Label label3 = new Label(" Function 2");

        // manually set buttons instead of using VBox
        button.setLayoutX(100);
        button.setLayoutY(10);
        button2.setLayoutX(200);
        button2.setLayoutY(10);
//            label1.setLayoutX(700);
//            label1.setLayoutY(40);
//            label2.setLayoutX(200);
//            label2.setLayoutY(40);

        // drag and drop items
        final Text source = new Text(50, 100, "Function 1");
        source.setScaleX(2.0);
        source.setScaleY(2.0);

        final Text source2 = new Text(50, 100, "Function 2");
        source2.setScaleX(2.0);
        source2.setScaleY(2.0);

        final Text target = new Text(250, 100, " ADD");
        target.setScaleX(1.5);
        target.setScaleY(1.5);

        // root: left VBox with buttons
        VBox root1 = new VBox();
        root1.getChildren().add(button);
        root1.getChildren().add(button2);
        button.setStyle("-fx-font-size: 1.5em; ");
        button2.setStyle("-fx-font-size: 1.5em; ");
        // set borderline; size; alignment; spacing for VBox
        root1.setStyle("-fx-border-color: black");
        root1.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.25));
        root1.setAlignment(Pos.CENTER);
        root1.setSpacing(30);
//            DoubleProperty fontSize= new SimpleDoubleProperty(5); // font size in pt
//            root.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize));

        // root2: right VBox with drag and drop
        VBox root2 = new VBox();
        root2.getChildren().add(source);
        root2.getChildren().add(source2);
//            root2.getChildren().add(target);
        root2.setStyle("-fx-border-color: black");
        root2.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.25));
        root2.setAlignment(Pos.CENTER);
        root2.setSpacing(30);

        // root3: mid VBox with labels
        VBox root3 = new VBox();
//            root3.getChildren().add(label1);
        root3.getChildren().add(target);
        root3.setSpacing(30);
        root3.setStyle("-fx-border-color: black");
        root3.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.5));
//            Line line1= new Line(600, 0, 600, 600);
//            root3.getChildren().add(line1);
        root3.setAlignment(Pos.CENTER);
        // set font size for label in VBox
        DoubleProperty fontSize3= new SimpleDoubleProperty(18); // font size in pt
        root3.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize3));

        // drag and drop functions
        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");

                /* allow any transfer mode */
                Dragboard db= source.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content= new ClipboardContent();
                content.putString(source.getText());
                db.setContent(content);

                event.consume();
            }
        });

        source2.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");

                /* allow any transfer mode */
                Dragboard db= source.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content= new ClipboardContent();
                content.putString(source2.getText());
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
                    root3.getChildren().add(new Label(" Function 1"));
                }
                if (db.getString().equals("Function 2")) {
                    root3.getChildren().add(new Label(" Function 2"));
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

        source.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    source.setText("");
                }

                event.consume();
            }
        });

        source2.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    source2.setText("");
                }

                event.consume();
            }
        });

        //            root2.getChildren().addAll(label1, label2, label3);

        // HBox
        // / | \
        // root root3 root2
        HBox hbox = new HBox();
        hbox.getChildren().addAll(root, root1, root3, root2);
//            final Separator sepVert1= new Separator();
//            sepVert1.setOrientation(Orientation.VERTICAL);
//            hbox.getChildren().add(sepVert1);



        Scene scene = new Scene(hbox, 900, 600);
        scene.getStylesheets().add(getClass().getResource("pipeline.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

        //addGraphComponents();

        Layout layout = new RandomLayout(graph);
        layout.execute();
    }

    private void addGraphComponents() {
        Model model = graph.getModel();

        graph.beginUpdate();

        model.addCell("Cell A", CellType.RECTANGLE);
        model.addCell("Cell B", CellType.TRIANGLE);
        model.addCell("Cell C", CellType.RECTANGLE);
        model.addCell("Cell D", CellType.CIRCLE);
        model.addCell("Cell E", CellType.CIRCLE);
        model.addCell("Cell F", CellType.IMAGE);
        model.addCell("Cell G", CellType.BUTTON);

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
