package appli.ui;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import appli.core.Drawer;
import appli.core.DrawerJavaFX;
import appli.core.Point;
import appli.core.ShapeI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class WindowBuilder extends Application {
    
    private HashMap<Shape,ShapeI> shapes_toolbar;
    private Shape shape_dragged;
    private double drag_x,drag_y;
    private ArrayList<ShapeI> shapes_canvas;
    private ArrayList<int[]> couleurs;
    private ShapeI shape_moved;
    private Drawer drawer;



    @Override
    public void start(Stage primaryStage) throws Exception {

        this.drawer= new DrawerJavaFX();
        shapes_toolbar = new HashMap<Shape,ShapeI>();
        shapes_canvas = new ArrayList<ShapeI>();
        couleurs = new ArrayList<int[]>();
        int[] black = {0, 0, 0};
        int[] red = {255, 0, 0};
        int[] green = {0, 255, 0};
        int[] blue = {0, 0, 255};
        int[] purple = {160, 32, 255};
        couleurs.add(black);
        couleurs.add(red);
        couleurs.add(green);
        couleurs.add(blue);
        couleurs.add(purple);


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
        appli.core.Rectangle rec = new appli.core.Rectangle(0, 0, 50, 20,drawer);
        rec.modifyColor(0, 0, 0);
        rectangle.setOnMousePressed(e -> {
            System.out.println("Rectangle");
            shape_dragged=rectangle;
        });
        shapes.getItems().addAll(rectangle);
        shapes_toolbar.put(rectangle, rec);
        Rectangle rectangle2 = new Rectangle(50, 20);
        rectangle2.setFill(Color.RED);
        appli.core.Rectangle rec2 = new appli.core.Rectangle(0, 0, 100, 40,drawer);
        rec2.modifyColor(255, 0, 0);
        rectangle2.setOnMousePressed(e -> {
            System.out.println("Rectangle2");
            shape_dragged=rectangle2;
        });
        shapes.getItems().addAll(rectangle2);
        shapes_toolbar.put(rectangle2, rec2);

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

        

        //StackPane holder = new StackPane();
        Canvas canvas = new Canvas(300,300);

        //holder.getChildren().add(canvas);

        //holder.setStyle("-fx-background-color: red");

        AnchorPane anchorPane = new AnchorPane();
        
        anchorPane.setBottomAnchor(imageView, 5.0);
        anchorPane.setLeftAnchor(imageView, 10.0);
        anchorPane.setTopAnchor(shapes, 0.0);
        anchorPane.setLeftAnchor(shapes, 0.0);
        anchorPane.setBottomAnchor(shapes, 60.0);

        anchorPane.getChildren().addAll(shapes,imageView);

        

        


        VBox vbox2 = new VBox(shapes,imageView);
        

        //vbox2.setVgrow(shapes, Priority.ALWAYS);
        //vbox2.setVgrow(imageView, Priority.ALWAYS);

        HBox hbox = new HBox(vbox2,canvas);
        hbox.setHgrow(canvas, Priority.ALWAYS);

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

        GraphicsContext gc = canvas.getGraphicsContext2D();

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
            for(ShapeI shape : shapes_canvas){
                if(shape.pointIn((int)(drag_x-shapes.getWidth()),(int)(drag_y-toolbar.getHeight()))){
                    shape_moved=shape;
                }
            }
        });
        scene.setOnMouseReleased(e -> {
            if(e.getX()==drag_x && e.getY()==drag_y){
                System.out.println("C'était juste un click");
                System.out.println("Clic x: "+e.getX()+" y: "+e.getY());
                //System.out.println("Clic sans toolbar  x: "+(int)(e.getX()-shapes.getWidth())+" y: "+(int)(e.getY()-toolbar.getHeight()));
                ShapeI temp=null;
                for(ShapeI shape : shapes_canvas){
                    if(shape.pointIn((int)(e.getX()-shapes.getWidth()), (int)(e.getY()-toolbar.getHeight()))){
                        temp=shape;
                    }
                }
                if(temp != null) {
                    int nb = (int)(Math.random() * couleurs.size());
                    temp.modifyColor((int)couleurs.get(nb)[0],(int)couleurs.get(nb)[1],(int)couleurs.get(nb)[2]);
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    for(ShapeI shape : shapes_canvas){
                        shape.draw(gc);
                    }
                    primaryStage.show();
                }
            }else{
                if(e.getX()>=0 && e.getX()<=shapes.getWidth() && e.getY()>shapes.getHeight()+toolbar.getHeight() && e.getY()<=scene.getHeight()){
                    System.out.println("Forme supprimée");
                    if(shapes_canvas.contains(shape_moved)){
                        shapes_canvas.remove(shape_moved);
                        shape_moved=null;
                        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        for(ShapeI shape : shapes_canvas){
                            shape.draw(gc);
                        }
                        primaryStage.show();
                    }
                }if(e.getX()>shapes.getWidth() && e.getX()<=scene.getWidth() && e.getY()>toolbar.getHeight() && e.getY()<=scene.getHeight()){
                    System.out.println("Forme droppée");
                    shape_moved.setCenter((int)(e.getX()-shapes.getWidth()), (int)(e.getY()-toolbar.getHeight()));
                    shape_moved=null;
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    for(ShapeI shape : shapes_canvas){
                        shape.draw(gc);
                    }
                    primaryStage.show();
                }
                if(e.getX()>=0 && e.getX()<=shapes.getWidth() && e.getY()>toolbar.getHeight() && e.getY()<=shapes.getHeight()){
                    System.out.println("Forme ajoutée à la toolbar");
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
                if(e.getX()>shapes.getWidth() && e.getX()<=scene.getWidth() && e.getY()>=0 && e.getY()<=scene.getHeight()){
                    System.out.println("Forme droppée depuis la toolbar");
                    if(shape_dragged!=null){
                        System.out.println("Forme sélectionée");
                        //System.out.println("Clic x: "+e.getX()+" y: "+e.getY());
                        ShapeI instance = shapes_toolbar.get(shape_dragged);
                        if(instance instanceof appli.core.Rectangle){
                            //System.out.println("Rectangle");
                            appli.core.Rectangle bis = new appli.core.Rectangle((int)e.getX()-(int)shapes.getWidth()-25,(int)e.getY()-10, 50,  20,drawer);
                            bis.modifyColor(instance.getR(), instance.getG(), instance.getB());
                            shapes_canvas.add(bis);
                        }else if(instance instanceof appli.core.Polygon){
                            appli.core.Polygon p = (appli.core.Polygon)instance;
                            appli.core.Polygon bis = new appli.core.Polygon((int)e.getX()-(int)shapes.getWidth(),(int)e.getY(), p.getNbSides(),  p.getSideSize(),drawer);
                            bis.modifyColor(instance.getR(), instance.getG(), instance.getB());
                            shapes_canvas.add(bis);
                        }

                        //System.out.println(shapes_canvas);
                        
                        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        for(ShapeI shape : shapes_canvas){
                            shape.draw(gc);
                        }
                        /*scene.getGra
                        shapes_toolbar.get(shape_dragged).draw(o);*/
                        primaryStage.show();
                    }
                }
            }
            
        });
        
        canvas.setWidth(scene.getWidth()-shapes.getWidth());
        canvas.setHeight(scene.getHeight()+toolbar.getHeight());
        shapes.setPrefHeight(scene.getHeight()-50-toolbar.getHeight()-50);

        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println(toolbar.getHeight());
        System.out.println("Toolbar x: "+shapes.getLayoutX()+" y: "+shapes.getLayoutY()+" width: "+shapes.getWidth()+" height: "+shapes.getHeight());

        appli.core.Polygon poly = new appli.core.Polygon(50, 50, 5, 15,drawer);
        

        List<Point> points = poly.generatePolygonPoints(poly.getCenter(),poly.getNbSides(),poly.getSideSize());
        double[] coord = new double[points.size()*2];
        System.out.println(coord.length);
        for(int i=0;i<points.size()*2;i+=2){
            coord[i]=(double)points.get(i/2).getX();
            coord[i+1]=(double)points.get(i/2).getY();
        }
        Polygon p = new Polygon(coord);
        
        p.setFill(Color.BLUE);
        poly.modifyColor(0, 0, 255);
        p.setOnMousePressed(e -> {
            System.out.println("Polygon");
            shape_dragged=p;
        });
        shapes.getItems().addAll(p);
        shapes_toolbar.put(p, poly);

        primaryStage.show();

        System.out.println("toolbar height : "+shapes.getHeight());
        System.out.println("scene height : "+scene.getHeight());


    }

    public static void main(String[] args) {
        launch(args);
    }

}