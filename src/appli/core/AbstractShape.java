package appli.core;

import java.util.Collections;
import java.util.Set;

public abstract class AbstractShape implements Shape {

    private Point center;
    private int r;
    private int g;
    private int b;
    private double rotation;

    public AbstractShape(int origin_x, int origin_y){
        this.center=new Point(origin_x,origin_y);
        //Dogerblue color (color in the toolbar)
        this.r=30;
        this.g=144;
        this.b=255;
        this.rotation=0.0;
    }

    @Override 
    public void move(int new_x, int new_y){
        this.setCenter(new_y,new_y);
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
    public void addShape(Shape shape){
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeShape(Shape shape) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Shape> getShapes() {
        return Collections.emptySet();
    }

}