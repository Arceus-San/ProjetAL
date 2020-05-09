package appli.ui;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.web.PromptData;

import java.io.InputStream;
import java.util.HashMap;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WindowBuilder extends Application {
    
    private HashMap<Shape,appli.core.Shape> shapes_toolbar;
    private Shape shape_dragged;
    private double drag_x,drag_y;



    @Override
    public void start(Stage primaryStage) throws Exception {

        shapes_toolbar = new HashMap<Shape,appli.core.Shape>();

        Button save = new Button("Save");
        Button load = new Button("Load");
        Button undo = new Button("Undo");
        Button redo = new Button("Redo");

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Load");
            }
        });

        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Save");
            }
        });

        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Undo");
            }
        });

        redo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Redo");
            }
        });

        ToolBar toolbar = new ToolBar(save,load,undo,redo);

        ToolBar shapes = new ToolBar();

        Rectangle rectangle = new Rectangle(50, 20);
        appli.core.Rectangle rec = new appli.core.Rectangle(0, 0, 50, 20);
        rectangle.setOnMousePressed(e -> {
            System.out.println("Rectangle");
            shape_dragged=rectangle;
        });
        shapes.getItems().addAll(rectangle);
        shapes_toolbar.put(rectangle, rec);

        shapes.setOrientation(Orientation.VERTICAL);

        //shapes.getItems().add(new Separator());

        InputStream input = getClass().getResourceAsStream("iconfinder_trash_115789.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(50.0);
        imageView.setFitWidth(50.0);
        imageView.setPreserveRatio(true);
        imageView.setOnMouseClicked(e -> {
            System.out.println("Trash");
        });

        

        StackPane holder = new StackPane();
        Canvas canvas = new Canvas(300,300);

        holder.getChildren().add(canvas);

        holder.setStyle("-fx-background-color: red");

        AnchorPane anchorPane = new AnchorPane();
        
        anchorPane.setBottomAnchor(imageView, 5.0);
        anchorPane.setLeftAnchor(imageView, 10.0);
        anchorPane.setTopAnchor(shapes, 0.0);
        anchorPane.setLeftAnchor(shapes, 0.0);
        anchorPane.setBottomAnchor(shapes, 60.0);

        anchorPane.getChildren().addAll(shapes,imageView);

        

        

        VBox vbox = new VBox(toolbar,anchorPane);

        VBox vbox2 = new VBox(shapes,imageView);
        

        vbox.setVgrow(anchorPane, Priority.ALWAYS);
        vbox2.setVgrow(shapes, Priority.ALWAYS);

        HBox hbox = new HBox(vbox2,holder);
        hbox.setHgrow(holder, Priority.ALWAYS);

        VBox vbox3 = new VBox(toolbar,hbox);
        vbox3.setVgrow(hbox, Priority.ALWAYS);

        GridPane grid = new GridPane();

        /*grid.add(toolbar, 0, 0);
        GridPane.setHgrow(toolbar, Priority.ALWAYS);
        GridPane.setVgrow(toolbar, Priority.ALWAYS);
        grid.add(anchorPane, 0, 1);
        GridPane.setHgrow(anchorPane, Priority.ALWAYS);
        GridPane.setVgrow(anchorPane, Priority.ALWAYS);
        grid.add(holder,1,1);
        GridPane.setHgrow(holder, Priority.ALWAYS);
        GridPane.setVgrow(holder, Priority.ALWAYS);*/


        Scene scene = new Scene(vbox3,640,400);
        scene.setOnMouseClicked(e -> {
            //System.out.println("x: "+e.getX()+" y: "+e.getY());
            if(e.getX()==drag_x && e.getY()==drag_y){
                System.out.println("C'était juste un click");
            }else if(e.getX()>=0 && e.getX()<=shapes.getWidth() && e.getY()>toolbar.getHeight() && e.getY()<=shapes.getHeight()){
                System.out.println("Forme ajoutée à la toolbar");
            }
        });

        primaryStage.widthProperty().addListener(e-> {
            System.out.println("Width :"+primaryStage.getWidth());
        });
       
        primaryStage.heightProperty().addListener(e -> {
            System.out.println("Height :"+primaryStage.getHeight());
        });

        /*shapes.setOnMouseClicked(e -> {
            System.out.println("x: "+e.getX()+" y: "+e.getY());
        });*/

        scene.setOnMousePressed(e -> {
            System.out.println("Forme draggée depuis le Canvas");
            drag_x=e.getX();
            drag_y=e.getY();
        });
        scene.setOnMouseReleased(e -> {
            if(e.getX()==drag_x && e.getY()==drag_y){
                System.out.println("C'était juste un click");
            }else{
                if(e.getX()>=0 && e.getX()<=shapes.getWidth() && e.getY()>shapes.getHeight() && e.getY()<=scene.getHeight()){
                    System.out.println("Forme supprimée"); 
                }if(e.getX()>shapes.getWidth() && e.getX()<=scene.getWidth() && e.getY()>toolbar.getHeight() && e.getY()<=scene.getHeight()){
                    System.out.println("Forme droppée");
                }
            }
        });

        shapes.setOnMousePressed(e -> {
            System.out.println("Forme draggée depuis la toolbar");
            drag_x=e.getX();
            drag_y=e.getY();
        });
        shapes.setOnMouseReleased(e -> {
            if(e.getX()==drag_x && e.getY()==drag_y){
                System.out.println("C'était juste un click");
            }else{
                /*System.out.println("x: "+e.getX());
                System.out.println("y: "+e.getY());
                */
                
                if(e.getX()>=0 && e.getX()<=shapes.getWidth() && e.getY()>shapes.getHeight() && e.getY()<=scene.getHeight()){
                    System.out.println("Forme supprimée"); 
                }
                if(e.getX()>=0 && e.getX()<=shapes.getWidth() && e.getY()>toolbar.getHeight() && e.getY()<=shapes.getHeight()){
                    System.out.println("Forme ajoutée à la toolbar");
                }
                if(e.getX()>shapes.getWidth() && e.getX()<=scene.getWidth() && e.getY()>toolbar.getHeight() && e.getY()<=scene.getHeight()){
                    System.out.println("Forme droppée");
                    if(shape_dragged!=null){
                        /*scene.getGra
                        shapes_toolbar.get(shape_dragged).draw(o);*/
                    }
                }
            }
            
        });
        

        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println(toolbar.getHeight());
        System.out.println("Toolbar x: "+shapes.getLayoutX()+" y: "+shapes.getLayoutY()+" width: "+shapes.getWidth()+" height: "+shapes.getHeight());


    }

    public static void main(String[] args) {
        launch(args);
    }

}