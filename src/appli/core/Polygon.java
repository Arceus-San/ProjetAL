package appli.core;

public class Polygon extends AbstractShape{

    private int nbSides;
    private double sideSize;

    public Polygon(int origin_x, int origin_y, int nbSides, int sideSize){
        super(origin_x, origin_y);
        this.nbSides=nbSides;
        this.sideSize=sideSize;
    }

    @Override
    public void scale(double size) {
        this.sideSize=this.sideSize*size;
    }

    @Override
    public void rotate(double angle) {
        // TODO Auto-generated method stub

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

    @Override
    public void draw(Object o) {
        // A faire avec un builder pour séparer les librairies graphiques
    }

    @Override
	public AbstractShape clone() {
		return super.clone();
	}


}