package appli.core;

import java.util.Set;


public interface Shape extends Cloneable {

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

    //Setters
    public void setCenter(int new_x, int new_y);
    public void setRotation(double new_rotation);

    //Composite
    public void addShape(Shape shape);
    public void removeShape(Shape shape);
    public Set<Shape> getShapes();

    //Others
    public AbstractShape clone();

    public void draw(Object o);


    public boolean pointIn(int x, int y);


}