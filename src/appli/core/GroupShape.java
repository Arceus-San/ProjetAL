package appli.core;

import java.util.HashSet;
import java.util.Set;


public class GroupShape extends AbstractShape {

    private Set<ShapeI> shapes=new HashSet<ShapeI>();
    private int width;
    private int height;

    public GroupShape(int origin_x, int origin_y, int width, int height, Drawer d){
        super(origin_x+width/2,origin_y+height/2, d);
        this.width=width;
        this.height=height;
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

    @Override
    public void modifyColor(int new_r, int new_g, int new_b) {
        super.modifyColor(new_r, new_g, new_b);
        for(ShapeI shape : shapes){
            shape.modifyColor(new_r, new_g, new_b);
        }
    }

    @Override
    public void move(int new_x, int new_y) {
        super.move(new_x, new_y);
        for(ShapeI shape : shapes){
            shape.move(new_x, new_y);
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
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub
        return false;
    }




}