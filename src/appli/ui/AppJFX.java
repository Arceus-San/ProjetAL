package appli.ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import appli.builders.JFXSuperToolBar;
import appli.builders.JFXToolBarBuilder;
import appli.builders.SuperToolBarDirector;
import appli.core.AppCenter;
import appli.core.Drawer;
import appli.core.DrawerJavaFX;
import appli.core.GroupShape;
import appli.core.Point;
import appli.core.Polygon;
import appli.core.Rectangle;
import appli.core.ShapeI;
import appli.factory.GroupMenu;
import appli.factory.JFXPopUpMenuFactory;
import appli.factory.PolygonMenu;
import appli.factory.RectangleMenu;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AppJFX extends Application implements AppManager {

    private static Stage ps;
    public static GraphicsContext gc;
    private Scene scene;
    private double menuHeight;
    private double toolbarWidth;
    private double toolbarHeight;
    private double sceneWidth;
    private double sceneHeight;
    private double sizeBin;
    private boolean select;
    public static double canvasHeight;
    public static double canvasWidth;

    private double drag_x;
    private double drag_y;
    private ArrayList<int[]> couleurs;
    private Node shape_dragged;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Initialisation des structures spécifiques JFX : Drawer + Toolbar
        Drawer drawer = new DrawerJavaFX();

        JFXToolBarBuilder toolbarbuilder = new JFXToolBarBuilder();
        SuperToolBarDirector director = new SuperToolBarDirector();
        director.makeOriginalToolBar(toolbarbuilder, drawer);
        JFXSuperToolBar st = toolbarbuilder.getResult();

        AppCenter appcenter = AppCenter.getInstance();
        appcenter.setDrawer(drawer);
        appcenter.setSuperToolbar(st);

        // Liste de couleurs pour quand on clique
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

        // Création des boutons du menu et de leurs events associés
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

        // Création de la structure de la fenêtre (toolbar + menu + canvas + corbeille)
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
        Scene scene = new Scene(vbox3, 640, 400);
        st.toolbar.setPrefHeight(scene.getHeight() - sizeBin - toolbar.getHeight() - sizeBin);

        // Sauvegarde des des longueurs et largeurs des composants
        this.gc = canvas.getGraphicsContext2D();
        this.menuHeight = toolbar.getHeight();
        this.toolbarWidth = st.toolbar.getWidth();
        this.toolbarHeight = st.toolbar.getHeight();
        this.sceneHeight = scene.getHeight();
        this.sceneWidth = scene.getWidth();
        this.scene = scene;

        // Ajout des listener aux items de la toolbar
        for (Node node : st.toolbar.getItems()) {
            this.addEvent(node);
        }

        // Ajout des events pour pouvoir drag and drop
        addMousePressedScene();
        addMouseReleasedScene();
        addEventSelection();
        addMousePressedToolbar();
        addMouseReleasedToolbar();

        // On défini la taille du canvas en fonction de la taille de la fenêtre et des
        // composants
        canvas.setWidth(sceneWidth - toolbarWidth);
        canvas.setHeight(sceneHeight + toolbarHeight);

        // On affiche la scene
        primaryStage.setScene(scene);
        primaryStage.show();

        // On actualise les valeurs des longueurs et largeurs des composants
        this.ps = primaryStage;
        this.gc = canvas.getGraphicsContext2D();
        this.menuHeight = toolbar.getHeight();
        this.toolbarWidth = st.toolbar.getWidth();
        this.toolbarHeight = st.toolbar.getHeight();
        this.sceneHeight = scene.getHeight();
        this.sceneWidth = scene.getWidth();
        this.canvasHeight = canvas.getHeight();
        this.canvasWidth = canvas.getWidth();

    }

    // Renvoie le contexte graphique du canvas pour dessiner les formes
    @Override
    public Object getGraphicContext() {
        return this.gc;
    }

    // Actualise la fenêtre
    public static void update() {
        ps.show();
    }

    //Ajoute un event à un item de la toolbar pour savoir sur quel item on a cliqué et récupérer (plus tard)
    //sa Shape associée dans la HashMap de la supertoolbar pour la dessiner sur le canvas
    public void addEvent(Node node){
        node.setOnMousePressed(e -> {
            shape_dragged=node;
        });
    }

    //Event qui permet de dessiner le rectangle de sélection quand on sélectionne
    //Ici encore plusieurs cas en fonction des coordonnées de départ et d'arrivée
    @Override
    public void addEventSelection(){
        scene.setOnMouseDragged(e -> {
            if(select){
                int new_x=(int) (e.getX()-toolbarWidth);
                int new_y=(int) (e.getY()-menuHeight);
                AppCenter.getInstance().draw(gc, (int) canvasWidth, (int) canvasHeight);
                gc.setFill(Color.rgb(30, 144, 155, 0.5));
                if(new_x>=drag_x-toolbarWidth && new_y>=drag_y-menuHeight){
                    gc.fillRect(drag_x-toolbarWidth, drag_y-menuHeight, (int)e.getX()-drag_x, (int)e.getY()-drag_y);
                }else if(new_x<drag_x-toolbarWidth && new_y>=drag_y-menuHeight){
                    gc.fillRect(new_x, drag_y-menuHeight, (int)(drag_x-toolbarWidth-new_x), (int)e.getY()-drag_y);
                }
                else if(new_x>=drag_x-toolbarWidth && new_y<drag_y-menuHeight){
                    gc.fillRect(drag_x-toolbarWidth, new_y, (int)e.getX()-drag_x, (int)(drag_y-menuHeight-new_y));
                }else{
                    gc.fillRect(new_x, new_y, (int)(drag_x-toolbarWidth-new_x), (int)(drag_y-menuHeight-new_y));
                }
                this.update();
            }
        });
    }

    //Listener quand on clique avec la souris sur le Canvas
    // - Affiche la délimitation du Groupe de formes sur lequel on clique
    // - Défini si on sélectionne ou non
    @Override
    public void addMousePressedScene(){
        scene.setOnMousePressed(e -> {
            drag_x = e.getX();
            drag_y = e.getY();
            ShapeI temp = AppCenter.getInstance().getShapeFromClick((int)(drag_x-toolbarWidth), (int)(drag_y-menuHeight));
            //Si on a cliqué sur une forme
            if(temp!=null){
                select=false;
                //Si c'est un groupe de formes on affiche sa délimitation
                if(temp instanceof GroupShape){
                    GroupShape temp2 = (GroupShape)temp;
                    gc.setStroke(Color.BLACK);
                    gc.strokeRect(temp2.getOrigin().getX(), temp2.getOrigin().getY(), temp2.getWidth(), temp2.getHeight());
                    this.update();
                }
            }
            //Sinon on commence une sélection
            else{
                select=true;
            }
        });
    }

    //Listener quand on relache la souris sur le Canvas
    // - Regroupe les formes sélectionnées (s'il y'en a)
    // - Modifie la couleur 
    // - Ungroup un groupe de formes
    // - Supprime une forme du canvas
    // - Déplace une forme
    // - Ajoute une forme à la toolbar
    @Override
    public void addMouseReleasedScene(){

        scene.setOnMouseReleased(e -> {    
            //Si on était en sélection (et qu'on a pas juste relâché au même endroit)
            if(select && e.getX() != drag_x && e.getY() != drag_y){
                int select_x=(int)(e.getX()-toolbarWidth);
                int select_y=(int)(e.getY()-menuHeight);
                select=false;
                //On regroupe les formes dans la selection (coordonnées différentes en fonction de la position de sortie du click par rapport à celle d'entrée)
                if(select_x>=drag_x-toolbarWidth && select_y>=drag_y-menuHeight){
                    // x> y>
                    AppCenter.getInstance().groupSelection((int)(drag_x-toolbarWidth), (int)(drag_y-menuHeight), select_x, select_y);
                }else if(select_x<drag_x-toolbarWidth && select_y>=drag_y-menuHeight){
                    // x< y>
                    AppCenter.getInstance().groupSelection(select_x, (int)(drag_y-menuHeight),(int)(drag_x-toolbarWidth), select_y);
                }
                else if(select_x>=drag_x-toolbarWidth && select_y<drag_y-menuHeight){
                    // x> y<
                    AppCenter.getInstance().groupSelection((int)(drag_x-toolbarWidth), select_y,select_x, (int)(drag_y-menuHeight));
                }else{
                    // x< y<
                    AppCenter.getInstance().groupSelection(select_x, select_y,(int)(drag_x-toolbarWidth), (int)(drag_y-menuHeight));
                }
                //On affiche le résultat
                AppCenter.getInstance().draw(gc, (int)canvasWidth, (int)canvasHeight);
                this.update();
            }
            //Si on a juste fait un click (mais un clic gauche)
            else if (e.getX() == drag_x && e.getY() == drag_y && !e.getButton().equals(MouseButton.SECONDARY)) {
                //On récupère la forme correspondante au click (null si aucune ne correspond)
                ShapeI temp = AppCenter.getInstance().getShapeFromClick((int) (e.getX() - toolbarWidth),(int) (e.getY()-menuHeight));
                //Si on a bien cliqué sur une forme on affiche son menu
                if (temp != null) {
                    if(temp instanceof Rectangle){
                        JFXPopUpMenuFactory factory = new RectangleMenu();
                        factory.getShapeMenu((int)(drag_x-toolbarWidth),(int)(drag_y-menuHeight));
                    }
                    else if(temp instanceof Polygon){
                        JFXPopUpMenuFactory factory = new PolygonMenu();
                        factory.getShapeMenu((int)(drag_x-toolbarWidth),(int)(drag_y-menuHeight));
                    }
                    else if(temp instanceof GroupShape){
                        JFXPopUpMenuFactory factory = new GroupMenu();
                        factory.getShapeMenu((int)(drag_x-toolbarWidth),(int)(drag_y-menuHeight));
                    }
                }
            } 
            //Si on a cliqué avec le bouton droit
            else if (e.getX() == drag_x && e.getY() == drag_y && e.getButton().equals(MouseButton.SECONDARY)) {
                //On récupère la forme correspondante au click (null si aucune ne correspond)
                ShapeI shape = AppCenter.getInstance().getShapeFromClick((int)(drag_x-toolbarWidth), (int)(drag_y-menuHeight));
                //Si on a bien cliqué sur une forme qui est en fait un groupe
                if(shape!=null && shape instanceof GroupShape){
                    //On supprime le groupe et on ajoute les formes qu'il contient sur le canvas (elles sont maintenant indépendantes)
                    GroupShape new_shape = (GroupShape)shape;
                    List<ShapeI> shapes = new_shape.getPrimaryShapes();
                    AppCenter.getInstance().deleteShape((int)(drag_x-toolbarWidth), (int)(drag_y-menuHeight));
                    for(ShapeI s : shapes){
                        AppCenter.getInstance().addShapeToCanvas(s, s.getCenter().getX(), s.getCenter().getY());
                    }
                    //On affiche le résultat
                    AppCenter.getInstance().draw(gc, (int)canvasWidth, (int)canvasHeight);
                    this.update();
                }
            }
            //Sinon on a déplacé une forme
            else {
                //Si déplacée dans la corbeille on la supprime et on affiche
                if (e.getX() >= 0 && e.getX() <= toolbarWidth && e.getY() > toolbarHeight + menuHeight && e.getY() <= sceneHeight) {
                    AppCenter.getInstance().deleteShape((int) (drag_x - toolbarWidth), (int) (drag_y - menuHeight));
                    AppCenter.getInstance().draw(gc, (int) canvasWidth, (int) canvasHeight);
                    this.update();
                }
                //Si déplacée dans le canvas on update sa position et on affiche
                if (e.getX() > toolbarWidth && e.getX() <= sceneWidth && e.getY() > menuHeight && e.getY() <= sceneHeight) {
                    AppCenter.getInstance().moveShape((int) (drag_x - toolbarWidth), (int) (drag_y - menuHeight),
                            (int) (e.getX() - toolbarWidth), (int) (e.getY() - menuHeight));
                    AppCenter.getInstance().draw(gc, (int) canvasWidth, (int) canvasHeight);
                    this.update();
                }
                //Si déplacée dans la toolbar
                if (e.getX() >= 0 && e.getX() <= toolbarWidth && e.getY() > menuHeight && e.getY() <= toolbarHeight) {
                    ShapeI shape = AppCenter.getInstance().getShapeFromClick((int)(drag_x-toolbarWidth), (int)(drag_y-menuHeight));
                    //Si c'est un rectangle on crée le rectangle Javafx associé et on l'ajoute à la toolbar
                    if(shape instanceof Rectangle){
                        Rectangle clone = (Rectangle)shape.clone();
                        double coef = 1;
                        if(clone.getWidth()>50){
                            coef=50.0/(double)clone.getWidth();
                        }
                        javafx.scene.shape.Rectangle rec = new javafx.scene.shape.Rectangle(clone.getWidth()*coef, clone.getHeight()*coef,Color.rgb(clone.getR(),clone.getG(), clone.getB()));
                        this.addEvent(rec);
                        //On associe le rectangle du canvas au rectangle javafx dans la hashmap de SuperToolbar
                        //On pourra ensuite le récupérer pour le cloner et le re-dessiner sur le canvas quand on fera un drag and drop depuis la toolbar
                        JFXSuperToolBar toolbar = (JFXSuperToolBar) AppCenter.getInstance().getSuperToolbar();
                        toolbar.shapes.put(rec, clone);
                        toolbar.toolbar.getItems().add(rec);
                        this.update();
                    //Même opération avec le Polygone
                    }else if(shape instanceof Polygon){
                        Polygon clone = (Polygon)shape.clone();
                        List<Point> points = clone.generatePolygonPoints(clone.getCenter(), clone.getNbSides(),clone.getSideSize());
                        double[] x = new double[points.size()];
                        double min=1000.0;
                        double max=-1000.0;
                        for(int i=0;i<points.size();i++){
                            x[i]=(double)points.get(i).getX();
                            if(x[i]<min){
                                min=x[i];
                            }
                            if(x[i]>max){
                                max=x[i];
                            }
                        }
                        javafx.scene.shape.Polygon poly = new javafx.scene.shape.Polygon(pointsPoly(clone));
                        if(!(max-min<=50)){
                            double coef = 50.0/(max-min);
                            Polygon clone2 = (Polygon) clone.clone();
                            clone2.scale(coef);
                            poly = new javafx.scene.shape.Polygon(pointsPoly(clone2));
                        }

                        poly.setFill(Color.rgb(clone.getR(), clone.getG(), clone.getB()));
                        this.addEvent(poly);
                        JFXSuperToolBar toolbar = (JFXSuperToolBar) AppCenter.getInstance().getSuperToolbar();
                        toolbar.shapes.put(poly, clone);
                        toolbar.toolbar.getItems().add(poly);
                        this.update();
                    }
                    //Enfin pour le GroupShape on appelle la fonction 'addGroupToToolbar' détaillée plus bas (mais même principe)
                    else if(shape instanceof GroupShape){
                        GroupShape clone = (GroupShape)shape.clone();
                        addGroupToToolbar(clone);
                    }

                }
            }
        });
    }


    //Récupère les coordonnées lorsqu'on appuie avec la souris dans la toolbar
    @Override
    public void addMousePressedToolbar(){
        JFXSuperToolBar toolbar = (JFXSuperToolBar) AppCenter.getInstance().getSuperToolbar();
        toolbar.toolbar.setOnMousePressed(e -> {
            drag_x = e.getX();
            drag_y = e.getY();
        });
    }

    //Event qui gère les cas quand on relâche la souris lorsqu'on a appuyé dans la toolbar
    // - Suppression de forme de la toolbar
    // - Drag and drop de forme depuis la toolbar
    @Override
    public void addMouseReleasedToolbar(){
        JFXSuperToolBar toolbar = (JFXSuperToolBar) AppCenter.getInstance().getSuperToolbar();
        toolbar.toolbar.setOnMouseReleased(e -> {
            if (e.getX() == drag_x && e.getY() == drag_y) {
                //On a juste cliqué
            } 
            else {
                //Si une forme a été déplacée à la corbeille alors on la supprime de la toolbar
                if (e.getX() >= 0 && e.getX() <= toolbarWidth && e.getY() > toolbarHeight && e.getY() <= sceneHeight) {
                    toolbar.shapes.remove(shape_dragged);
                    toolbar.toolbar.getItems().remove(shape_dragged);
                    this.update();
                }
                //Si on a drag and drop depuis la toolbar
                if (e.getX() > toolbarWidth && e.getX() <= sceneWidth && e.getY() >= 0 && e.getY() <= sceneHeight) {
                    //Si on a bien cliqué sur une forme dans la toolbar
                    if (shape_dragged != null) {
                        ShapeI instance = toolbar.shapes.get(shape_dragged);
                        //Si c'est une rectangle alors on crée la forme associée sur le canvas
                        if (instance instanceof Rectangle) {
                            appli.core.Rectangle rec = (Rectangle) instance.clone();
                            AppCenter.getInstance().addShapeToCanvas(rec,(int)(e.getX()-toolbarWidth),(int)(e.getY()));
                            this.update();
                        //Si c'est un Polygon alors on crée la forme associée sur le canvas
                        }else if(instance instanceof Polygon){
                            Polygon p = (Polygon) instance.clone();
                            AppCenter.getInstance().addShapeToCanvas(p,(int)(e.getX()-toolbarWidth),(int)(e.getY()));
                            this.update();
                        }
                        //Si c'est un groupe alors on crée la forme associée sur le canvas (problèmes avec groupes de groupes)
                        else if(instance instanceof GroupShape){
                            GroupShape group = (GroupShape) instance.clone();
                            GroupShape drop = new GroupShape(group.getOrigin().getX(), group.getOrigin().getY(),group.getWidth(), group.getHeight(),AppCenter.getInstance().getDrawer());
                            for(ShapeI shape : group.getShapes()){
                                ShapeI shapebis = shape.clone();
                                drop.addShape(shapebis);
                            }
                            drop.move(drop.getCenter().getX(), drop.getCenter().getY(), (int)(e.getX()-toolbarWidth), (int)(e.getY()));
                            AppCenter.getInstance().addShapeToCanvas(drop,(int)(e.getX()-toolbarWidth),(int)(e.getY()));
                            this.update();
                        }

                        //On actualise l'affichage
                        AppCenter.getInstance().draw(gc, (int) canvasWidth, (int) canvasHeight);
                        this.update();
                        shape_dragged=null;
                    }
                }
            }
            
        });
    }

    //Renvoie un tableau des coordonnées des points d'un Polygone (Shape)
    @Override
    public double[] pointsPoly(Polygon clone){
        List<Point> points = clone.generatePolygonPoints(clone.getCenter(), clone.getNbSides(), clone.getSideSize());
        double[] coord = new double[points.size()*2];
        for(int i=0;i<points.size()*2;i+=2){
            coord[i]=(double)points.get(i/2).getX();
            coord[i+1]=(double)points.get(i/2).getY();
        }
        return coord;
    }

    //Ajoute un groupe de formes a la toolbar (Via un canvas)
    //On va redimensionner le groupe sur un canvas de longueur 50 (la longueur d'un item dans la toolbar)
    @Override
    public void addGroupToToolbar(GroupShape group){
        //On récupère le coefficient qui va réduire notre groupe
        double indice = 50.0/(double)group.getWidth();
        //On crée le canvas associé, 50 en longueur, la largeur sera celle du groupe multipliée par le coefficient
        Canvas gCanvas = new Canvas(50.0, group.getHeight()*indice);
        GraphicsContext gcC = gCanvas.getGraphicsContext2D();
        //On récupère les primaryShapes, c'est a dire que des rectangles et polygones, pas de groupes
        for(ShapeI shape : group.getPrimaryShapes()){
            //Si on a un rectangle on le dessine sur le canvas avec ses coordonnées et sa taille modifées avec le coefficient
            if(shape instanceof Rectangle){
                Rectangle rec = (Rectangle) shape;
                int x=(rec.getCenter().getX()-(rec.getWidth()/2))-group.getOrigin().getX();
                int y=(rec.getCenter().getY()-(rec.getHeight()/2))-group.getOrigin().getY();
                gcC.setFill(Color.rgb(rec.getR(), rec.getG(), rec.getB()));
                gcC.fillRect((double)x*indice, (double)y*indice, (double)rec.getWidth()*indice, (double)rec.getHeight()*indice);
            }
            //Même chose avec les polygones
            else if(shape instanceof Polygon){
                Polygon poly = (Polygon)shape;
                int x=(poly.getCenter().getX()-group.getOrigin().getX());
                int y=(poly.getCenter().getY()-group.getOrigin().getY());
                gcC.setFill(Color.rgb(poly.getR(), poly.getG(), poly.getB()));
                Point new_center = new Point((int)((double)x*indice),(int)((double)y*indice));
                List<Point> points = poly.generatePolygonPoints(new_center,poly.getNbSides(),poly.getSideSize()*indice);
                double[] xPoints = new double[points.size()];
                double[] yPoints = new double[points.size()];
                for(int i=0;i<points.size();i++){
                    xPoints[i]=(double)points.get(i).getX();
                    yPoints[i]=(double)points.get(i).getY();
                }
                gcC.fillPolygon(xPoints, yPoints, points.size());
            }
        }
        
        //On récupère la SuperToolbar
        //On ajoute le canvas dans la toolbar
        //On l'associe à son GroupShape dans la HashMap
        JFXSuperToolBar suptool = (JFXSuperToolBar) AppCenter.getInstance().getSuperToolbar();
        addEvent(gCanvas);
        suptool.toolbar.getItems().addAll(gCanvas);
        suptool.shapes.put(gCanvas, group);
        AppCenter.getInstance().draw(gc, (int)canvasWidth, (int)canvasHeight);
        this.update();
    }




    public static void main(String[] args) {
        launch(args);
    }
    
}