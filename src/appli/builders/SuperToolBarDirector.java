package appli.builders;

import appli.core.Drawer;

public class SuperToolBarDirector {

    public void makeOriginalToolBar(ToolBarBuilder builder, Drawer drawer){
        builder.init();
        builder.addRectangle(50, 20, 255, 0, 0, drawer);
        builder.addRectangle(50, 20, 0, 0, 0, drawer);
        builder.addPolygon(5, 15, drawer, 0, 0, 255);
    }

}