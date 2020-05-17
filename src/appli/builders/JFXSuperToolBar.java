package appli.builders;

import java.util.HashMap;
import java.util.List;

import appli.core.GroupShape;
import appli.core.Point;
import appli.core.Polygon;
import appli.core.Rectangle;
import appli.core.ShapeI;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class JFXSuperToolBar implements SuperToolbar {

    public ToolBar toolbar;
    public HashMap<Shape,ShapeI> shapes;

    public JFXSuperToolBar(){
        this.toolbar = new ToolBar();
        this.shapes = new HashMap<Shape,ShapeI>();
    }

    @Override
    public void addShape(ShapeI shape) {
        if(shape instanceof Rectangle){
            Rectangle shape2=(Rectangle)shape.clone();
            javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(shape2.getWidth(), shape2.getHeight());
            rectangle.setFill(Color.rgb(shape2.getR(), shape2.getG(), shape2.getB()));
            shapes.put(rectangle, shape2);
            toolbar.getItems().addAll(rectangle);

        }else if(shape instanceof Polygon){
            Polygon poly=(Polygon)shape.clone();

            List<Point> points = poly.generatePolygonPoints(poly.getCenter(),poly.getNbSides(),poly.getSideSize());
            double[] coord = new double[points.size()*2];
            System.out.println(coord.length);
            for(int i=0;i<points.size()*2;i+=2){
                coord[i]=(double)points.get(i/2).getX();
                coord[i+1]=(double)points.get(i/2).getY();
            }

            javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon(coord);
            polygon.setFill(Color.rgb(poly.getR(), poly.getG(), poly.getB()));
            shapes.put(polygon, poly);
            toolbar.getItems().addAll(polygon);
        }else if(shape instanceof GroupShape){
            
        }
    }

    @Override
    public void removeShape(ShapeI shape) {
        // TODO Auto-generated method stub

    }
    
}