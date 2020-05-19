package appli.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GroupShape extends AbstractShape {

    private Set<ShapeI> shapes=new HashSet<ShapeI>();
    private int width;
    private int height;
    private Point origin;

    public GroupShape(int origin_x, int origin_y, int width, int height, Drawer d){
        super(origin_x+width/2,origin_y+height/2, d);
        origin = new Point(origin_x,origin_y);
        this.width=width;
        this.height=height;
    }


    @Override
    public void modifyColor(int new_r, int new_g, int new_b) {
        super.modifyColor(new_r, new_g, new_b);
        for(ShapeI shape : shapes){
            shape.modifyColor(new_r, new_g, new_b);
        }
    }

    @Override
    public void move(int old_x, int old_y, int new_x, int new_y) {
        int diff_x=new_x-old_x;
        int diff_y=new_y-old_y;
        setCenter(new_x, new_y);
        setOrigin(getOrigin().getX()+diff_x, getOrigin().getY()+diff_y);
        for(ShapeI shape : shapes){
            int end_x = shape.getCenter().getX()+diff_x;
            int end_y = shape.getCenter().getY()+diff_y;
            shape.move(old_x,old_y,end_x, end_y);
        }
    }

    @Override
    public void scale(double size) {
        this.height=(int)(this.height*size);
		this.width=(int)(this.width*size);
		for(ShapeI shape : shapes) {
			shape.scale(size);
		}
    }

    @Override
    public void rotate(double angle) {
        // TODO Auto-generated method stub

    }

    @Override
    public void draw(Object o) {
        for(ShapeI shape : shapes){
            shape.draw(o);
        }
    }

    //Composite

    @Override
    public void addShape(ShapeI shape) {
        this.shapes.add(shape);
    }

    @Override
    public void removeShape(ShapeI shape) {
        this.shapes.remove(shape);
    }

    @Override
    public Set<ShapeI> getShapes() {
        return this.shapes;
    }

    @Override
    public boolean pointIn(int x, int y) {
        for(ShapeI shape : shapes){
            if(shape.pointIn(x, y)){
                return true;
            }
        }
        return false;
    }

    @Override
	public AbstractShape clone() {
		GroupShape group = null;
	    // On récupère l'instance à renvoyer par l'appel de la
        // méthode super.clone()
        group = (GroupShape) super.clone();
	    // On clone les attributs qui ne sont pas immuable.
	    for(ShapeI shape : group.getShapes()){
            shape.clone();
        }
	    // on renvoie le clone
	    return group;
    }
    

    public Point getOrigin(){
        return this.origin;
    }

    public void setOrigin(int x, int y){
        origin = new Point(x,y);
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return this.height;
    }

    public void setWidth(int w){
        width=w;
    }

    public void setHeight(int h){
        height=h;
    }

    public List<ShapeI> getPrimaryShapes(){
        List<ShapeI> list = new ArrayList<ShapeI>();
        for(ShapeI shape : shapes){
            if(shape instanceof GroupShape){
                GroupShape shape2 = (GroupShape)shape;
                List<ShapeI> temp = shape2.getPrimaryShapes();
                for(ShapeI forme : temp){
                    list.add(forme);
                }
            }else{
                list.add(shape);
            }
        }
        return list;
    }



}