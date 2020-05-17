package appli.builders;

import appli.core.Drawer;

public interface ToolBarBuilder {
    
    public void init();
    public void addRectangle(int width, int height, int r, int g, int b, Drawer drawer);
    public void addPolygon(int sideNb, int sideSize, Drawer drawer, int r, int g, int b);

}