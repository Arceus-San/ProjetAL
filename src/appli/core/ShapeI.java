package appli.core;

import java.util.Set;


public interface ShapeI extends Cloneable {

    //Modify fonctions
    public void move(int new_x, int new_y);
    public void modifyColor(int new_r, int new_g, int new_b);
    public void scale(double size);
    public void rotate(double angle);

    //Getters
    public Point getCenter();
    public double getRotation();
    public int getR();
    public int getG();
    public int getB();
    public Drawer getDrawer();

    //Setters
    public void setCenter(int new_x, int new_y);
    public void setRotation(double new_rotation);

    //Composite
    public void addShape(ShapeI shape);
    public void removeShape(ShapeI shape);
    public Set<ShapeI> getShapes();

    //Others
    public AbstractShape clone();

    public void draw(Object o);


    public boolean pointIn(int x, int y);


}