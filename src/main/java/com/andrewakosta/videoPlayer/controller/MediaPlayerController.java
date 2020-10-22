package com.andrewakosta.videoPlayer.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.andrewakosta.videoPlayer.utilities.Properties;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

/**
 * @author Andres Acsota
 * @description this class will handle the video play
 * */
public class MediaPlayerController implements Initializable {



    private String path;
    private javafx.scene.media.MediaPlayer mediaPlayer;

    //Nodes defined on XML file
    @FXML
    private MediaView mediaView;

    @FXML
    private Slider progressBar;

    @FXML
    private Label label;

    @FXML
    private StackPane pane;

    @FXML
    private Button btnSetPointOnCurrentTime;

    @FXML
    private Button btnPreviousVideo;


    @FXML
    private Button btnPause;

    @FXML
    private Button btnNextVideo;

    @FXML
    private Button btnNavigate;

    //This array will store the points set by the user
    public ArrayList<Duration> navigatePoints = new ArrayList<javafx.util.Duration>();

    //Position initial of the points
    int position = -1;

    //Status initial of video | default playing
    boolean videoIsRunning = true;

    /**
     * @author Andres Acosta
     * @description This is the main method which will handle the video play
     * and control the instance of the Media class player.
     * @return void
     *
    */
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

    /**
     * This is a kind of constructor of initialize interface which will be execute
     * when its FXML file be referenced.
     * */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        runVideo();
        setIconsToButtons();

    }

    /**
     * @author Andres Acosta
     * is in charge of setting the icons on the player buttons.
     * */
    private void setIconsToButtons() {

        //Btn btnSetPointOnCurrentTime
        Image imagePoint = new Image(getClass().getResourceAsStream("point.png"));
        ImageView imageViewPoint = new ImageView(imagePoint);
        imageViewPoint.setFitHeight(25);
        imageViewPoint.setFitWidth(25);

        //Btn  btnPreviousVideo
        Image imagePreviousVideo = new Image(getClass().getResourceAsStream("previus.png"));
        ImageView imageViewPreviousVideo = new ImageView(imagePreviousVideo);
        imageViewPreviousVideo.setFitHeight(25);
        imageViewPreviousVideo.setFitWidth(25);

        //Btn btnPause
        Image imagePause = new Image(getClass().getResourceAsStream("pause.png"));
        ImageView imageViewPause = new ImageView(imagePause);
        imageViewPause.setFitHeight(30);
        imageViewPause.setFitWidth(50);
        //Btn btnNextVideo
        Image imageNext = new Image(getClass().getResourceAsStream("next.png"));
        ImageView imageViewNext = new ImageView(imageNext);
        imageViewNext.setFitHeight(25);
        imageViewNext.setFitWidth(25);

        //Btn btnNextVideo
        Image imageNavigate = new Image(getClass().getResourceAsStream("navigate.png"));
        ImageView imageViewNavigate = new ImageView(imageNavigate);
        imageViewNavigate.setFitHeight(25);
        imageViewNavigate.setFitWidth(25);



        btnSetPointOnCurrentTime.setGraphic(imageViewPoint);
        btnPreviousVideo.setGraphic(imageViewPreviousVideo);
        btnPause.setGraphic(imageViewPause);
        btnNextVideo.setGraphic(imageViewNext);
        btnNavigate.setGraphic(imageViewNavigate);
    }

    public void pauseVideo(ActionEvent event){
        if(videoIsRunning){
            mediaPlayer.pause();
            videoIsRunning = false;
        }else {
            mediaPlayer.play();
            videoIsRunning = true;
        }
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

    public void previousVideo(ActionEvent actionEvent) {
        //TO DO
    }
    public void goToNextVideo(ActionEvent actionEvent) {
        //TO DO
    }
}
