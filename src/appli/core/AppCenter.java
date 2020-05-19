package appli.core;

import java.util.ArrayList;
import java.util.List;

import appli.builders.SuperToolbar;

public class AppCenter {
    
    private static AppCenter instance;
    private SuperToolbar toolbar_shapes;
    public List<ShapeI> canvas_shapes;
    private Drawer drawer;

    public AppCenter(){
        this.canvas_shapes=new ArrayList<ShapeI>();
    }

    public void setSuperToolbar(SuperToolbar st){
        this.toolbar_shapes=st;
    }

    public static AppCenter getInstance() {
        if (instance == null) {
            instance = new AppCenter();
        }
        return instance;
    }

    public SuperToolbar getSuperToolbar(){
        return this.toolbar_shapes;
    }

    public Drawer getDrawer(){
        return this.drawer;
    }

    public void setDrawer(Drawer d){
        this.drawer=d;
    }

    //Renvoie la Shape associée au click sur le canvas
    public ShapeI getShapeFromClick(int x, int y){
        ShapeI res=null;
        for(ShapeI shape : canvas_shapes){
            if(shape.pointIn(x,y)){
                res=shape;
                break;
            }
        }
        return res;
    }

    //Dessine les formes sur le canvas
    public void draw(Object o, int width, int height){
        drawer.cleanCanvas(o, width, height);
        for(ShapeI shape : canvas_shapes){
            shape.draw(o);
        }
    }

    //Supprime la forme associée au click sur le Canvas
    public void deleteShape(int x, int y){
        ShapeI res=getShapeFromClick(x, y);
        if(canvas_shapes.contains(res)){
            canvas_shapes.remove(res);
        }
    }

    //Déplace la shape associée au click sur le canvas
    public void moveShape(int origin_x, int origin_y, int new_x, int new_y){
        ShapeI res=getShapeFromClick(origin_x, origin_y);
        if(res!=null){
            res.move(origin_x,origin_y,new_x, new_y);
        }
    }

    //Ajoute une shape au canvas
    public void addShapeToCanvas(ShapeI shape, int x, int y){
        shape.setCenter(x, y);
        canvas_shapes.add(shape);
    }

    //Regroupe les formes de la sélection dans un seul ShapeGroup
    public void groupSelection(int origin_x, int origin_y, int new_x, int new_y){
        List<ShapeI> selection = new ArrayList<ShapeI>();
        for(ShapeI shape : canvas_shapes){
            if(origin_x<=shape.getCenter().getX() && shape.getCenter().getX()<=new_x && origin_y<=shape.getCenter().getY() && shape.getCenter().getY()<=new_y){
                selection.add(shape);
            }
        }
        if(selection.size()>1){
            GroupShape group = new GroupShape(origin_x, origin_y, new_x-origin_x, new_y-origin_y, drawer);
            for(ShapeI shape : selection){
                group.addShape(shape);
                canvas_shapes.remove(shape);
            }
            canvas_shapes.add(group);
        }
        
    }

    //Modifie la couleur de la forme aux coordonnées x et y
    public void changeColor(int x, int y, int r, int g, int b){
        ShapeI shape = getShapeFromClick(x, y);
        System.out.println(shape);
        shape.modifyColor(r, g, b);
    }

    //Défini si le rectangle au coordonnées x et y à les bords arrondis
    public void roundBorders(int x, int y, boolean b){
        Rectangle shape = (Rectangle) getShapeFromClick(x, y);
        shape.setArcRound(b);
    }

    //Redimmensionne la forme aux coordonées en fonction du coefficient
    public void scale(int x, int y, double multiplier){
        ShapeI shape = getShapeFromClick(x, y);
        shape.scale(multiplier);
    }

    //Modifie la longueur du rectangle en x et y
    public void modifyWidth(int x, int y, int width){
        Rectangle shape = (Rectangle) getShapeFromClick(x, y);
        shape.setWidth(width);
    }

    //Modifie la largeur du rectangle en x et y
    public void modifyHeight(int x, int y, int height){
        Rectangle shape = (Rectangle) getShapeFromClick(x, y);
        shape.setHeight(height);
    }

    //Modifie le nombre de côtés du polygone en x et y
    public void modifyNbSides(int x, int y, int nbSides){
        Polygon shape = (Polygon) getShapeFromClick(x, y);
        shape.setNbSides(nbSides);
    }

    //Modifie la taille des côtés du polygone en x et y
    public void modifySideSize(int x, int y, int sideSize){
        Polygon shape = (Polygon) getShapeFromClick(x, y);
        shape.setSideSize(sideSize);
    }

}