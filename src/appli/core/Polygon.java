package appli.core;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Polygon extends AbstractShape {

    private int nbSides;
    private double sideSize;

    public Polygon(int origin_x,int origin_y,int nbSides,double sideSize, Drawer d){
        super(origin_x, origin_y, d);
        this.nbSides=nbSides;
        this.sideSize=sideSize;
    }

    @Override
    public void scale(double size) {
        this.sideSize=(double)this.sideSize*size;
    }

    @Override
    public void rotate(double angle) {
        // TODO Auto-generated method stub

    }

    @Override
    public void draw(Object o) {
        GraphicsContext gc = (GraphicsContext)o;
        gc.setFill(Color.rgb(getR(), getG(), getB()));
        Point center = getCenter();
        List<Point> points = generatePolygonPoints(center, nbSides, sideSize);
        double[] x = new double[points.size()];
        double[] y = new double[points.size()];
        for(int i=0;i<points.size();i++){
            x[i]=(double)points.get(i).getX();
            y[i]=(double)points.get(i).getY();
        }
        gc.fillPolygon(x, y, points.size());
    }

    @Override
	public AbstractShape clone() {
		return super.clone();
	}

    @Override
    public boolean pointIn(int x,int y) {
        final List<Point> pointList = generatePolygonPoints( getCenter(), nbSides, sideSize);
		int i, j;
	    boolean c = false;
	    for (i = 0, j = pointList.size()-1; i < pointList.size(); j = i++) {
	        if ( ((pointList.get(i).getY()>y) != (pointList.get(j).getY()>y)) &&
	    	(x < (pointList.get(j).getX()-pointList.get(i).getX()) * (y-pointList.get(i).getY()) / (pointList.get(j).getY()-pointList.get(i).getY()) + pointList.get(i).getX()) )
	        c = !c;
	    }
	    return c;
    }

    public int getNbSides(){
        return this.nbSides;
    }

    public double getSideSize(){
        return this.sideSize;
    }

    public void setSideSize(int side_size){
        this.sideSize=side_size;
    }

    public void setNbSides(int nb_sides){
        this.nbSides=nb_sides;
    }

    //Renvoie les coordonnées des points pour le polygone avec les valeurs en paramètre
    public List<Point> generatePolygonPoints(Point center, int nSides,double sideSize){
        List<Point> points = new ArrayList<Point>();
        for(int x = 0; x<nSides; x++) {
            final Point p = new Point(
                    (int)(center.getX() + sideSize/(2.0*Math.sin(Math.PI/nSides)) * Math.cos(2.0*Math.PI*x/nSides)), 
                    (int)(center.getY() + sideSize/(2.0*Math.sin(Math.PI/nSides)) * Math.sin(2.0*Math.PI*x/nSides))
            );
            points.add(p);
        }
        points = rotation(points);
        return points;
    }


}