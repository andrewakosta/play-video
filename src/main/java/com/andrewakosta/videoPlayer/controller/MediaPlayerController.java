package com.andrewakosta.videoPlayer.controller;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.andrewakosta.videoPlayer.utilities.Properties;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;


/**
 *
 * @author Ammar
 */
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class MediaPlayerController implements Initializable {



    private String path;
    private javafx.scene.media.MediaPlayer mediaPlayer;

    @FXML
    private MediaView mediaView;

    @FXML
    private Button setPoint;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider progressBar;

    @FXML
    private Label label;

    @FXML
    private StackPane pane;

    public ArrayList<Duration> navigatePoints = new ArrayList<javafx.util.Duration>();

    int position = -1;
    private void runVideo(){
        path = Properties.getProperty("currentVideo");

        if(path != null){

            Media media = new Media(path);
            mediaPlayer = new javafx.scene.media.MediaPlayer(media);
            Properties.mediaPlayerHashMap.put("currentMediaPlayer", mediaPlayer);
            mediaView.setMediaPlayer(mediaPlayer);


            //creating bindings
            DoubleProperty widthProp = mediaView.fitWidthProperty();
            DoubleProperty heightProp = mediaView.fitHeightProperty();

            widthProp.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            heightProp.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

            volumeSlider.setValue(mediaPlayer.getVolume()*100);
            volumeSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(volumeSlider.getValue()/100);
                }
            });

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<javafx.util.Duration>() {
                @Override
                public void changed(ObservableValue<? extends javafx.util.Duration> observable, javafx.util.Duration oldValue, javafx.util.Duration newValue) {
                    progressBar.setValue(newValue.toSeconds());
                }
            }
            );

            progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
                }
            });

            progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
                }
            });

            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    javafx.util.Duration total = media.getDuration();
                    progressBar.setMax(total.toSeconds());
                }
            });

            mediaPlayer.play();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("An error has happened while the video was played");
            alert.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        runVideo();

    }
    public void pauseVideo(ActionEvent event){
        mediaPlayer.pause();
    }



    public void playVideo(ActionEvent event){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }

    public void skip5(ActionEvent event){
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(javafx.util.Duration.seconds(5)));
    }

    public void furtherSpeedUpVideo(ActionEvent event){
        mediaPlayer.setRate(2);
    }

    public void back5(ActionEvent event){
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(javafx.util.Duration.seconds(-5)));
    }

    public void furtherSlowDownVideo(ActionEvent event){
        mediaPlayer.setRate(0.5);


    }
    public void  setPointOnCurrentTime(ActionEvent event){
        navigatePoints.add(mediaPlayer.getCurrentTime());
    }
    public  void navigateThroughThePoints(ActionEvent event){
        position++;
        if(position < navigatePoints.size()){
            mediaPlayer.seek(navigatePoints.get(position));
            return;
        }else{
            position = 0;
            mediaPlayer.seek(navigatePoints.get(position));
        }

    }

}
