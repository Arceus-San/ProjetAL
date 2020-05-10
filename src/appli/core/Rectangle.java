package appli.core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends AbstractShape {

    private int width;
    private int height;
    private boolean arcRound;

    public Rectangle(int origin_x, int origin_y, int width, int height){
        super(origin_x+width/2,origin_y+height/2);
        this.width=width;
        this.height=height;
        this.arcRound=false;
    }

    @Override
	public void scale(double size) {
		this.height=(int)(this.height*size);
		this.width=(int)(this.width*size);	
    }

    @Override
    public void rotate(double angle) {
        // TODO Auto-generated method stub

    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public void setWidth(int new_width){
        this.width=new_width;
    }

    public void setHeight(int new_height){
        this.width=new_height;
    }

    public void setArcRound(boolean arcRound){
        this.arcRound=arcRound;
    }

    @Override
	public AbstractShape clone() {
		return super.clone();
	}

    @Override
    public void draw(Object o) {
        GraphicsContext gc = (GraphicsContext)o;
        gc.setFill(Color.rgb(getR(), getG(), getB()));
        Point center = getCenter();
        //System.out.println("Center form : "+center.getX()+" "+center.getY());
        //System.out.println("Width "+width);
        //.out.println("Heigth : "+height);
        gc.fillRect(center.getX()-(width/2), center.getY()-(height/2), width, height);
    }

    @Override
    public boolean pointIn(int x, int y) {
        Point c = getCenter();
        //System.out.println("haut gauche "+(int)(getCenter().getX()-width/2)+" "+(int)(getCenter().getY()-height/2));
        //System.out.println("bas droit "+(int)(getCenter().getX()+width/2)+" "+(int)(getCenter().getY()+height/2));
        if(x>=c.getX()-width/2 && x<=c.getX()+width/2 && y>=c.getY()-height/2 && y<=c.getY()+height/2) {
			return true;
		}
		return false;
    }

    
}