package appli.core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//Permet de dessiner avec JavaFX
public class DrawerJavaFX implements Drawer {

    @Override
    public void setColor(Object o, int r, int g, int b) {
        GraphicsContext gc = (GraphicsContext)o;
        gc.setFill(Color.rgb(r,g,b));
    }

    @Override
    public void drawRectangle(Object o, int center_x, int center_y, int width, int height) {
        GraphicsContext gc = (GraphicsContext)o;
        gc.fillRect(center_x-width/2, center_y-height/2, width, height);
    }

    @Override
    public void drawPolygon(Object o, double[] x, double[] y, int nb_points) {
        GraphicsContext gc = (GraphicsContext)o;
        gc.fillPolygon(x, y, nb_points);
    }

    @Override
    public void cleanCanvas(Object o, int width, int height) {
        GraphicsContext gc = (GraphicsContext)o;
        gc.clearRect(0, 0, width, height);
    }
    
}