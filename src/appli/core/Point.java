package appli.core;

public class Point implements Cloneable {

    private int x;
    private int y;

    public Point(int x, int y){
        this.x=x;
        this.y=y;
    }

    public void move(int new_x, int new_y){
        this.x=new_x;
        this.y=new_y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public boolean equals(Point p) {
		return (this.x==p.getX() && this.y==p.getY());
	}
	
	public Point clone() {
		Point clone = null;
		try {
			clone = (Point) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}

}