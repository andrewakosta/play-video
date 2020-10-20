package com.andrewakosta.videoPlayer.controller;


import com.andrewakosta.videoPlayer.utilities.Properties;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;


public class Player extends Application {

    Player(String path){
        this.path = Properties.getProperty("pathVideos") + "/" + path;
    }

    String path;

    public void playVideo(){



    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        try{
            Media media = new Media(new File(path).toURI().toString());
            MediaPlayer player = new MediaPlayer(media);
            player.setAutoPlay(true);
            MediaView mediaView = new MediaView(player);
            Group root = new Group();
            root.getChildren().add(mediaView);
            Scene scene = new Scene(root,600,400);
            primaryStage.setScene(scene);
            primaryStage.setTitle(path);
            primaryStage.show();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
}

