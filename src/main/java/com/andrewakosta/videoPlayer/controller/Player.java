package com.andrewakosta.videoPlayer.controller;



import com.andrewakosta.videoPlayer.utilities.Properties;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Label;
import javafx.stage.Stage;




public class Player extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        try{

            Parent root = FXMLLoader.load(getClass().getResource("MediaPlayer.fxml"));


            Scene scene = new Scene(root);
            stage.setTitle("Media Player");

            //Listeners
            /*Expand screen*/
            scene.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 ){
                    stage.setFullScreen(!stage.fullScreenProperty().getValue());
                }
            });
            //Stop Video
            stage.setOnHidden(event -> {
                Properties.mediaPlayerHashMap.get("currentMediaPlayer").stop();

            });

            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.out.println(e);


        }

    }
}

