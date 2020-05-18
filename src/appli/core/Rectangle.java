package appli.core;

public class Rectangle extends AbstractShape {

    private int width;
    private int height;
    private boolean arcRound;

    public Rectangle(int origin_x, int origin_y, int width, int height, Drawer drawer){
        super(origin_x+width/2,origin_y+height/2, drawer);
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

    @Override
	public AbstractShape clone() {
		return super.clone();
	}

    @Override
    public void draw(Object o) {
        getDrawer().setColor(o, getR(), getG(), getB());
        Point center = getCenter();
        getDrawer().drawRectangle(o, center.getX(), center.getY(), width, height);
    }

    @Override
    public boolean pointIn(int x, int y) {
        Point c = getCenter();
        if(x>=c.getX()-width/2 && x<=c.getX()+width/2 && y>=c.getY()-height/2 && y<=c.getY()+height/2) {
			return true;
		}
		return false;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public void setArcRound(boolean arcRound){
        this.arcRound=arcRound;
    }

    
}