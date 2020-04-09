package appli.core;

public class Rectangle extends AbstractShape{

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
    public void draw(Object o) {
        // A faire avec un builder pour choisir entre Javafx et les autres librairies graphiques
    }

    @Override
	public AbstractShape clone() {
		return super.clone();
	}
    
}