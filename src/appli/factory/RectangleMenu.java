package appli.factory;

import appli.core.AppCenter;
import appli.core.Rectangle;
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

public class RectangleMenu implements JFXPopUpMenuFactory {

    Integer r,g,b;

    //Affiche le menu pour modifier un rectangle
    @Override
    public void getShapeMenu(int x,int y) {
        Rectangle rec = (Rectangle) AppCenter.getInstance().getShapeFromClick(x, y);

        //Un colorpicker pour choisir la couleur
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction(t -> {
            Color c = colorPicker.getValue();
            r=(int)(c.getRed() * 255);
            g=(int)(c.getGreen() * 255);
            b=(int)(c.getBlue() * 255);
        });

        //Modification de la taille
        Label label1 = new Label("Sale multiplier : ");
        TextField textField = new TextField ();
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField);
        hb.setSpacing(10);
        hb.setAlignment(Pos.TOP_CENTER);

        //Vérifie si la valeur est bien un entier ou un double
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    textField.setText(oldValue);
                }
            }
        });

        //Modification de la longueur
        Label sideWidth = new Label("Width : ");
        TextField swith = new TextField ();
        HBox hb2 = new HBox();
        hb2.getChildren().addAll(sideWidth, swith);
        hb2.setSpacing(10);
        hb2.setAlignment(Pos.TOP_CENTER);

        swith.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}")) {
                    swith.setText(oldValue);
                }
            }
        });

        //Modification de la largeur
        Label sideHeight = new Label("Height : ");
        TextField sheight = new TextField ();
        HBox hb3 = new HBox();
        hb3.getChildren().addAll(sideHeight, sheight);
        hb3.setSpacing(10);
        hb3.setAlignment(Pos.TOP_CENTER);

        sheight.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}")) {
                    sheight.setText(oldValue);
                }
            }
        });

        //Checkbox pour choisir si on arrondi les coins ou pas
        CheckBox cb = new CheckBox("Rounded corners");
        cb.setIndeterminate(false);
        cb.setSelected(rec.getArcRound());

        VBox vbox = new VBox(colorPicker,hb,hb2,hb3,cb);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10.0);

        Stage stage = new Stage();
        Scene scene = new Scene(vbox,300,180);
        stage.setScene(scene);
        stage.show();

        //A la fermeture du menu
        stage.setOnHiding(e -> {
            //Si on a choisi une couleur on la modifie
            if(r!=null){
                AppCenter.getInstance().changeColor(x,y, r, g, b);
            }
            //Modification des bords arrondis
            AppCenter.getInstance().roundBorders(x, y, cb.isSelected());
            GraphicsContext gc = (GraphicsContext) AppJFX.gc;
            //On modifie la longueur (si valeur entrée)
            if(!swith.textProperty().get().isEmpty()){
                AppCenter.getInstance().modifyWidth(x, y, Integer.parseInt(swith.textProperty().get()));
            }
            //On modifie la largeur (si valeur entrée)
            if(!sheight.textProperty().get().isEmpty()){
                AppCenter.getInstance().modifyHeight(x, y, Integer.parseInt(sheight.textProperty().get()));
            }
            //On modifie la taille (si valeur entrée)
            if(!textField.textProperty().get().isEmpty()){
                System.out.println(Double.valueOf(textField.textProperty().get()));
                AppCenter.getInstance().scale(x, y, Double.valueOf(textField.textProperty().get()));
            }
            //On affiche le résultat
            AppCenter.getInstance().draw(gc, (int)AppJFX.canvasWidth, (int)AppJFX.canvasHeight);
            AppJFX.update();
        });
    }

}