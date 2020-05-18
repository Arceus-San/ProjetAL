package appli.core;

import java.util.Collections;
import java.util.List;
import java.util.Set;


public abstract class AbstractShape implements ShapeI {

    private Point center;
    private int r;
    private int g;
    private int b;
    private double rotation;
    private Drawer drawer;

    public AbstractShape(int origin_x, int origin_y, Drawer d){
        this.center=new Point(origin_x,origin_y);
        //Dogerblue color (color in the toolbar)
        this.r=30;
        this.g=144;
        this.b=255;
        this.rotation=0.0;
        this.drawer=d;
    }


    @Override 
    public void move(int old_x, int old_y, int new_x, int new_y){
        setCenter(new_x,new_y);
    }

    @Override 
    public void modifyColor(int new_r, int new_g, int new_b){
        this.r=new_r;
        this.g=new_g;
        this.b=new_b;
    }
    
    @Override 
    public abstract void scale(double size);

    @Override 
    public abstract void rotate(double angle);

    @Override 
    public abstract boolean pointIn(int x, int y);

    //Getters
    @Override 
    public Point getCenter(){
        return this.center;
    }

    @Override 
    public double getRotation(){
        return this.rotation;
    }

    @Override 
    public int getR(){
        return this.r;
    }

    @Override 
    public int getG(){
        return this.g;
    }

    @Override 
    public int getB(){
        return this.b;
    }

    @Override 
    public Drawer getDrawer(){
        return this.drawer;
    }


    //Setters
    @Override 
    public void setCenter(int new_x, int new_y){
        this.center=new Point(new_x,new_y);
    }

    @Override 
    public void setRotation(double new_rotation){
        this.rotation=new_rotation;
    };

    //Others
    @Override 
    public AbstractShape clone(){
        AbstractShape clone = null;
		try {
			clone = (AbstractShape) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
    }

    //Composite
    @Override
    public void addShape(ShapeI shape){
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeShape(ShapeI shape) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<ShapeI> getShapes() {
        return Collections.emptySet();
    }

    public List<Point> rotation(List<Point> pointList) {
        for (int i = 0; i < pointList.size(); i++) {
        		Point p = this.rotatePoint(pointList.get(i), getCenter(), getRotation());
            pointList.set(i, p);
        }
        return pointList;
    }

    public Point rotatePoint(Point pt, Point center, double angleDeg) {
        
        double dx = (pt.getX() - center.getX()); 
        double dy = (pt.getY() - center.getY()); 
        
        double angleRad = Math.toRadians(angleDeg);

        double pX = center.getX() + (double) (dx * Math.cos(angleRad) - dy * Math.sin(angleRad));
        double pY = center.getY() + (double) (dx * Math.sin(angleRad) + dy * Math.cos(angleRad));
                
        return new Point((int)pX, (int)pY);
    }

}