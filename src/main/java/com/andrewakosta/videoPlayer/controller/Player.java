package com.andrewakosta.videoPlayer.controller;


import com.andrewakosta.videoPlayer.utilities.Properties;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;


public class Player extends Application {
    String path;
    Player(String path){
        this.path = Properties.getProperty("pathVideos") + "/" + path;
    }



    public void playVideo(){



    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        File f = new File(path);
        try{
            Media m = new Media(f.toURI().toString());
            MediaPlayer mp = new MediaPlayer(m);
            MediaView mv = new MediaView(mp);
            mv.autosize();
            mv.preserveRatioProperty();
            DoubleProperty widthProp = mv.fitWidthProperty();
            DoubleProperty heightProp = mv.fitHeightProperty();

            widthProp.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
            heightProp.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
            mv.fitWidthProperty();
            mv.fitHeightProperty();

            VBox vb = new VBox(mv);
            vb.setStyle("-fx-background-color: #000000");
            vb.setSpacing(20);
            vb.setAlignment(Pos.CENTER);
            primaryStage.setOnHiding(event -> {
                mp.stop();
            });

            Scene scene = new Scene(vb, 650, 400);

            primaryStage.setScene(scene);
            primaryStage.setTitle("JavaFX with embedded media player");
            primaryStage.onCloseRequestProperty()
                    .setValue(e -> System.out.println("Bye! See you later!"));
            primaryStage.show();

            mp.play();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
}

