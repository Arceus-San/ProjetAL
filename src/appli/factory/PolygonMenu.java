package appli.factory;

import appli.core.AppCenter;
import appli.ui.AppJFX;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PolygonMenu implements JFXPopUpMenuFactory {

    Integer r,g,b;

    //Affiche le menu de modification du Polygone
    @Override
    public void getShapeMenu(int x,int y) {

        //Un colorpicker pour choisir la couleur
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction(t -> {
            Color c = colorPicker.getValue();
            r=(int)(c.getRed() * 255);
            g=(int)(c.getGreen() * 255);
            b=(int)(c.getBlue() * 255);
        });

        //Sélection de la taille
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

        //Choix de la taille des côtés
        Label sidesizeL = new Label("Side size : ");
        TextField sidesize = new TextField ();
        HBox hb2 = new HBox();
        hb2.getChildren().addAll(sidesizeL, sidesize);
        hb2.setSpacing(10);
        hb2.setAlignment(Pos.TOP_CENTER);

        sidesize.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}")) {
                    sidesize.setText(oldValue);
                }
            }
        });

        //Choix du nombre de côtés
        Label nbsidesL = new Label("Side number : ");
        TextField nbside = new TextField ();
        HBox hb3 = new HBox();
        hb3.getChildren().addAll(nbsidesL, nbside);
        hb3.setSpacing(10);
        hb3.setAlignment(Pos.TOP_CENTER);

        nbside.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}")) {
                    nbside.setText(oldValue);
                }
            }
        });

        

        VBox vbox = new VBox(colorPicker,hb,hb2,hb3);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10.0);

        Stage stage = new Stage();
        Scene scene = new Scene(vbox,300,170);
        stage.setScene(scene);
        stage.show();

        //Quand on quitte le menu
        stage.setOnHiding(e -> {
            //Si on a choisi un couleur alors on modifie la couleur
            if(r!=null){
                AppCenter.getInstance().changeColor(x,y, r, g, b);
            }
            GraphicsContext gc = (GraphicsContext) AppJFX.gc;
            //On modifie la taille (si valeur entrée)
            if(!textField.textProperty().get().isEmpty()){
                AppCenter.getInstance().scale(x, y, Double.valueOf(textField.textProperty().get()));
            }
            //On modifie le nb de côtés (si valeur entrée)
            if(!nbside.textProperty().get().isEmpty()){
                int i = Integer.parseInt(nbside.textProperty().get());
                AppCenter.getInstance().modifyNbSides(x,y,i);
            }
            //On modifie la taille des côtés (si valeur entrée)
            if(!sidesize.textProperty().get().isEmpty()){
                int i = Integer.parseInt(sidesize.textProperty().get());
                AppCenter.getInstance().modifySideSize(x,y,i);
            }
            //On affiche le résultat
            AppCenter.getInstance().draw(gc, (int)AppJFX.canvasWidth, (int)AppJFX.canvasHeight);
            AppJFX.update();
        });
    }
    
}