package appli.builders;

import appli.core.Drawer;
import appli.core.Polygon;
import appli.core.Rectangle;

public class JFXToolBarBuilder implements ToolBarBuilder {

    private JFXSuperToolBar toolbar;

    @Override
    public void init() {
        this.toolbar=new JFXSuperToolBar();
    }

    @Override
    public void addRectangle(int width, int height, int r, int g, int b, Drawer drawer) {
        Rectangle rec = new Rectangle(0, 0, width, height, drawer);
        rec.modifyColor(r, g, b);
        this.toolbar.addShape(rec);
    }

    @Override
    public void addPolygon(int sideNb, int sideSize, Drawer drawer, int r, int g, int b) {
        Polygon polygon = new Polygon(0,0, sideNb, sideSize, drawer);
        polygon.modifyColor(r, g, b);
        this.toolbar.addShape(polygon);
    }


    public JFXSuperToolBar getResult(){
        return this.toolbar;
    }
    
}