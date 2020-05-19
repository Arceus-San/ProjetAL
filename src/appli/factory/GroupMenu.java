package appli.factory;

import java.util.List;

import appli.core.AppCenter;
import appli.core.GroupShape;
import appli.core.ShapeI;
import appli.ui.AppJFX;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GroupMenu implements JFXPopUpMenuFactory {

        
    Integer r,g,b;

    //Renvoie le menu pour modifier le GroupShape
    @Override
    public void getShapeMenu(int x,int y) {

        //Un colorpicker pour la couleur
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction(t -> {
            Color c = colorPicker.getValue();
            r=(int)(c.getRed() * 255);
            g=(int)(c.getGreen() * 255);
            b=(int)(c.getBlue() * 255);
        });

        //Un paramètre pour redimmensionner
        Label label1 = new Label("Sale multiplier : ");
        TextField textField = new TextField ();
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField);
        hb.setSpacing(10);
        hb.setAlignment(Pos.TOP_CENTER);

        //Vérification que la valeur est un entier ou double
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    textField.setText(oldValue);
                }
            }
        });

        //Une checkbox pour ungroup les formes
        CheckBox cb = new CheckBox("Ungroup");
        cb.setIndeterminate(false);
        cb.setSelected(false);


        VBox vbox = new VBox(colorPicker,hb,cb);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10.0);

        Stage stage = new Stage();
        Scene scene = new Scene(vbox,300,200);
        stage.setScene(scene);
        stage.show();

        //Quand on ferme le menu
        stage.setOnHiding(e -> {
            GroupShape group = (GroupShape) AppCenter.getInstance().getShapeFromClick(x, y);
            List<ShapeI> shapes = group.getPrimaryShapes();
            GraphicsContext gc = (GraphicsContext) AppJFX.gc;
            //Pour toutes les formes dans le groupe
            for(ShapeI shape : shapes){
                //On modifie la couleur (si on en a choisie une)
                if(r!=null){
                    shape.modifyColor(r, g, b);
                }
                //On modifie la taille (si on en a choisie une)
                if(!textField.textProperty().get().isEmpty()){
                    shape.scale(Double.valueOf(textField.textProperty().get()));
                } 
            }

            if(cb.isSelected()){
                //On ungroup les formes du groupe
                for(ShapeI shape : shapes){
                    AppCenter.getInstance().addShapeToCanvas(shape, shape.getCenter().getX(),shape.getCenter().getY());
                }
                AppCenter.getInstance().deleteShape(x, y);
            }
            else{
                //Si le coefficient de redimension est supérieur à 1 (la taille augmente)
                if(!textField.textProperty().get().isEmpty() && Double.valueOf(textField.textProperty().get())>=1){
                    //On calcule la taille ajoutée
                    int width_add = (int)((double)group.getWidth()*Double.valueOf(textField.textProperty().get()))-group.getWidth();
                    int height_add = (int)((double)group.getHeight()*Double.valueOf(textField.textProperty().get()))-group.getHeight();
                    //On modifie les paramètres du groupe en conséquence
                    group.setOrigin(group.getOrigin().getX()-width_add/2,group.getOrigin().getY()-height_add/2);
                    group.setWidth(group.getWidth()+width_add);
                    group.setHeight(group.getHeight()+height_add);
                }
                //Si le coefficient de redimension est inférieur à 1 (la taille baisse)
                else if(!textField.textProperty().get().isEmpty() && Double.valueOf(textField.textProperty().get())<1){
                    //On calcule la taille supprimée
                    int width_remove = (int)((double)group.getWidth()*Double.valueOf(textField.textProperty().get()))-group.getWidth();
                    int height_remove = (int)((double)group.getHeight()*Double.valueOf(textField.textProperty().get()))-group.getHeight();
                    //On modifie les paramètres du groupe en conséquence
                    group.setOrigin(group.getOrigin().getX()-width_remove/2,group.getOrigin().getY()-height_remove/2);
                    group.setWidth(group.getWidth()+width_remove);
                    group.setHeight(group.getHeight()+height_remove);
                }
            }
            //On affiche les modifications
            AppCenter.getInstance().draw(gc, (int)AppJFX.canvasWidth, (int)AppJFX.canvasHeight);
            AppJFX.update();
        });
    }
		

    
}