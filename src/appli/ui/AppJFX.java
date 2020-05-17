package appli.ui;

import java.io.InputStream;
import java.util.ArrayList;

import appli.builders.JFXSuperToolBar;
import appli.builders.JFXToolBarBuilder;
import appli.builders.SuperToolBarDirector;
import appli.core.AppCenter;
import appli.core.Drawer;
import appli.core.DrawerJavaFX;
import appli.core.Polygon;
import appli.core.Rectangle;
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
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class AppJFX extends Application implements AppManager {

    private Stage ps;
    private GraphicsContext gc;
    private double menuHeight;
    private double toolbarWidth;
    private double toolbarHeight;
    private double sceneWidth;
    private double sceneHeight;
    private double sizeBin;

    private double drag_x;
    private double drag_y;
    private ArrayList<int[]> couleurs;
    private Shape shape_dragged;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Drawer drawer = new DrawerJavaFX();

        JFXToolBarBuilder toolbarbuilder = new JFXToolBarBuilder();
        SuperToolBarDirector director = new SuperToolBarDirector();
        director.makeOriginalToolBar(toolbarbuilder, drawer);
        JFXSuperToolBar st = toolbarbuilder.getResult();

        AppCenter appcenter = AppCenter.getInstance();
        appcenter.setDrawer(drawer);
        appcenter.setSuperToolbar(st);

        couleurs = new ArrayList<int[]>();
        int[] black = { 0, 0, 0 };
        int[] red = { 255, 0, 0 };
        int[] green = { 0, 255, 0 };
        int[] blue = { 0, 0, 255 };
        int[] purple = { 160, 32, 255 };
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

        ToolBar toolbar = new ToolBar(save, load, undo, redo);

        st.toolbar.setOrientation(Orientation.VERTICAL);

        InputStream input = getClass().getResourceAsStream("iconfinder_trash_115789.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);

        this.sizeBin = 50.0;
        imageView.setFitHeight(sizeBin);
        imageView.setFitWidth(sizeBin);
        imageView.setPreserveRatio(true);

        Canvas canvas = new Canvas(300, 300);

        AnchorPane anchorPane = new AnchorPane();

        anchorPane.setBottomAnchor(imageView, 0.0);
        anchorPane.setLeftAnchor(imageView, 10.0);
        anchorPane.setTopAnchor(st.toolbar, 0.0);
        anchorPane.setLeftAnchor(st.toolbar, 0.0);
        anchorPane.setBottomAnchor(st.toolbar, 60.0);

        anchorPane.getChildren().addAll(st.toolbar, imageView);

        VBox vbox2 = new VBox(st.toolbar, imageView);

        HBox hbox = new HBox(vbox2, canvas);
        hbox.setHgrow(canvas, Priority.ALWAYS);

        VBox vbox3 = new VBox(toolbar, hbox);
        vbox3.setVgrow(hbox, Priority.ALWAYS);

        GridPane grid = new GridPane();

        Scene scene = new Scene(vbox3, 640, 400);

        st.toolbar.setPrefHeight(scene.getHeight() - sizeBin - toolbar.getHeight() - sizeBin);

        this.gc = canvas.getGraphicsContext2D();
        this.menuHeight = toolbar.getHeight();
        this.toolbarWidth = st.toolbar.getWidth();
        this.toolbarHeight = st.toolbar.getHeight();
        this.sceneHeight = scene.getHeight();
        this.sceneWidth = scene.getWidth();


        for (Shape shape : st.shapes.keySet()) {
            this.addEvent(shape);
        }

        scene.setOnMousePressed(e -> {
            System.out.println("Forme draggée depuis le Canvas");
            drag_x = e.getX();
            drag_y = e.getY();

        });
        scene.setOnMouseReleased(e -> {
            if (e.getX() == drag_x && e.getY() == drag_y) {
                System.out.println("C'était juste un click");
                // System.out.println("Clic x: "+e.getX()+" y: "+e.getY());
                // System.out.println("Clic sans toolbar x:
                // "+(int)(e.getX()-shapes.getWidth())+" y:
                // "+(int)(e.getY()-toolbar.getHeight()));
                ShapeI temp = AppCenter.getInstance().getShapeFromClick((int) (e.getX() - toolbarWidth),(int) (e.getY()-menuHeight));
                System.out.println(temp);
                if (temp != null) {
                    int nb = (int) (Math.random() * couleurs.size());
                    temp.modifyColor((int) couleurs.get(nb)[0], (int) couleurs.get(nb)[1], (int) couleurs.get(nb)[2]);
                    AppCenter.getInstance().draw(gc, (int) canvas.getWidth(), (int) canvas.getHeight());
                    this.update();
                }
            } else {
                if (e.getX() >= 0 && e.getX() <= toolbarWidth && e.getY() > toolbarHeight + menuHeight
                        && e.getY() <= sceneHeight) {
                    System.out.println("Forme supprimée");
                    AppCenter.getInstance().deleteShape((int) (drag_x - toolbarWidth), (int) (drag_y - menuHeight));
                    AppCenter.getInstance().draw(gc, (int) canvas.getWidth(), (int) canvas.getHeight());
                    this.update();
                }
                if (e.getX() > toolbarWidth && e.getX() <= sceneWidth && e.getY() > menuHeight
                        && e.getY() <= sceneHeight) {
                    System.out.println("Forme droppée");
                    AppCenter.getInstance().moveShape((int) (drag_x - toolbarWidth), (int) (drag_y - menuHeight),
                            (int) (e.getX() - toolbarWidth), (int) (e.getY() - menuHeight));
                    AppCenter.getInstance().draw(gc, (int) canvas.getWidth(), (int) canvas.getHeight());
                    this.update();
                }
                if (e.getX() >= 0 && e.getX() <= toolbarWidth && e.getY() > menuHeight && e.getY() <= toolbarHeight) {
                    System.out.println("Forme ajoutée à la toolbar");
                    ShapeI shape = AppCenter.getInstance().getShapeFromClick((int)(drag_x-toolbarWidth), (int)(drag_y-menuHeight));
                    System.out.println(shape);
                    if(shape instanceof Rectangle){
                        System.out.println("C'est un rectangle");
                        Rectangle clone = (Rectangle)shape.clone();
                        javafx.scene.shape.Rectangle rec = new javafx.scene.shape.Rectangle(clone.getWidth(), clone.getHeight(),Color.rgb(clone.getR(),clone.getG(), clone.getB()));
                        this.addEvent(rec);
                        st.shapes.put(rec, clone);
                        st.toolbar.getItems().add(rec);
                        this.update();
                    }

                }
            }
        });

        st.toolbar.setOnMousePressed(e -> {
            System.out.println("Forme draggée depuis la toolbar");
            drag_x = e.getX();
            drag_y = e.getY();
        });
        st.toolbar.setOnMouseReleased(e -> {
            if (e.getX() == drag_x && e.getY() == drag_y) {
                System.out.println("C'était juste un click");
            } else {
                /*
                 * System.out.println("x: "+e.getX()); System.out.println("y: "+e.getY());
                 */

                if (e.getX() >= 0 && e.getX() <= toolbarWidth && e.getY() > toolbarHeight && e.getY() <= sceneHeight) {
                    System.out.println("Forme supprimée");
                    st.shapes.remove(shape_dragged);
                    st.toolbar.getItems().remove(shape_dragged);
                    this.update();
                }
                if (e.getX() >= 0 && e.getX() <= toolbarWidth && e.getY() > menuHeight && e.getY() <= toolbarHeight) {
                    System.out.println("Forme ajoutée à la toolbar tt");
                }
                if (e.getX() > toolbarWidth && e.getX() <= sceneWidth && e.getY() >= 0 && e.getY() <= sceneHeight) {
                    System.out.println("Forme droppée depuis la toolbar");
                    if (shape_dragged != null) {
                        // System.out.println("Forme sélectionée");
                        System.out.println("Clic x: "+e.getX()+" y: "+e.getY());
                        ShapeI instance = st.shapes.get(shape_dragged);
                        if (instance instanceof Rectangle) {
                            // System.out.println("Rectangle");
                            appli.core.Rectangle rec = (Rectangle) instance.clone();
                            System.out.println(st.toolbar.getWidth());
                            System.out.println(e.getX()-toolbarWidth+" "+e.getY());
                            AppCenter.getInstance().addShapeToCanvas(instance.clone(),(int)(e.getX()-toolbarWidth),(int)(e.getY()));
                            this.update();
                        }else if(instance instanceof Polygon){
                            Polygon p = (Polygon) instance.clone();
                            AppCenter.getInstance().addShapeToCanvas(instance.clone(),(int)(e.getX()-toolbarWidth),(int)(e.getY()));
                            this.update();
                        }

                        //System.out.println(shapes_canvas);
                        
                        AppCenter.getInstance().draw(gc, (int) canvas.getWidth(), (int) canvas.getHeight());
                        this.update();
                        shape_dragged=null;
                    }
                }
            }
            
        });




        canvas.setWidth(sceneWidth-toolbarWidth);
        canvas.setHeight(sceneHeight+toolbarHeight);

        primaryStage.setScene(scene);
        primaryStage.show();

        this.ps=primaryStage;

        this.gc = canvas.getGraphicsContext2D();
        this.menuHeight = toolbar.getHeight();
        this.toolbarWidth = st.toolbar.getWidth();
        this.toolbarHeight = st.toolbar.getHeight();
        this.sceneHeight = scene.getHeight();
        this.sceneWidth = scene.getWidth();
    

    }

    @Override
    public double getHeightMenu() {
        return this.menuHeight;
    }

    @Override
    public double getWidthShapes() {
        return this.toolbarWidth;
    }

    @Override
    public double getHeightShapes() {
        return this.toolbarHeight;
    }

    @Override
    public double getHeightWindow() {
        return this.sceneHeight;
    }

    @Override
    public double getWidthWindow() {
        return this.sceneWidth;
    }

    @Override
    public Object getGraphicContext() {
        return this.gc;
    }

    @Override
    public void update() {
        ps.show();
    }

    public void addEvent(Shape shape){
        shape.setOnMousePressed(e -> {
            System.out.println("Rectangle");
            shape_dragged=shape;
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}