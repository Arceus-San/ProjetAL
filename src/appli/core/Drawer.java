package appli.core;

public interface Drawer {

        public void cleanCanvas(Object o, int width, int height);
        public void setColor(Object o, int r, int g, int b);
        public void drawRectangle(Object o, int center_x, int center_y, int width, int height);
        public void drawPolygon(Object o, double[] x, double[] y, int nb_points);

}