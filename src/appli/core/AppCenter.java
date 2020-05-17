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

    public void setDrawer(Drawer d){
        this.drawer=d;
    }

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

    public void draw(Object o, int width, int height){
        drawer.cleanCanvas(o, width, height);
        for(ShapeI shape : canvas_shapes){
            shape.draw(o);
        }
    }

    public void deleteShape(int x, int y){
        ShapeI res=getShapeFromClick(x, y);
        if(canvas_shapes.contains(res)){
            canvas_shapes.remove(res);
        }
    }

    public void moveShape(int origin_x, int origin_y, int new_x, int new_y){
        ShapeI res=getShapeFromClick(origin_x, origin_y);
        if(res!=null){
            res.setCenter(new_x, new_y);
        }
    }

    public void addShapeToCanvas(ShapeI shape, int x, int y){
        shape.setCenter(x, y);
        canvas_shapes.add(shape);
        System.out.println(canvas_shapes);
    }

}