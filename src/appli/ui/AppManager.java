package appli.ui;

import appli.core.GroupShape;
import appli.core.Polygon;

public interface AppManager {

    public Object getGraphicContext();
    public void addEventSelection();
    public void addMousePressedScene();
    public void addMouseReleasedScene();
    public void addMousePressedToolbar();
    public void addMouseReleasedToolbar();
    public double[] pointsPoly(Polygon polygon);
    public void addGroupToToolbar(GroupShape group);

    
}