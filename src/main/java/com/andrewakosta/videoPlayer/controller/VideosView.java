package com.andrewakosta.videoPlayer.controller;


import com.andrewakosta.videoPlayer.utilities.Properties;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.File;

import java.util.ArrayList;

/**
 * @author  Andres Acosta
 * is in charge of show the videso .mp4 on a directory
 * */
public class VideosView  {

    //Videos Directory
    String location = Properties.getProperty("pathVideos");

    /**
     * @author Andres Acosta
     * @description is in charge of show videos on a gridPane which it received by the parameter
     * @return  GridPane - a gridPane with all videos on x location
     * @param  gridPane - the gridPane which will be use to show the videos.
     *
     **/
    public GridPane showAllVideosFromAnyDirectory(GridPane gridPane){

        gridPane = removeRowsAndColumns(gridPane);

        int rowCounter = 0, columnCounter = 0;

        for (String videoName : getAllVideoOfOneDirectory()){
            Pane pane = getPaneCustomized(videoName);
            gridPane.add(pane, columnCounter, rowCounter);
            if(columnCounter == 5){
                columnCounter = 0;
                rowCounter ++;
            }else{
                columnCounter ++;
            }
        }
        return gridPane;
    }

    /**
     * Filter all mp4 videos of a directory
     * */
    ArrayList<String> getAllVideoOfOneDirectory(){

        ArrayList<String> files = new ArrayList<String>();
        try {
            File files_ = new File(this.location);
            for(String file : files_.list()){
                if(file.endsWith(".mp4")){
                    files.add(file);
                }
            }
          return files;
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error File");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return files;
        }
    }
    /**
     * Reset all rows and columns od a gridPane
     * */
    GridPane removeRowsAndColumns(GridPane gridPane){
        while(gridPane.getRowConstraints().size() > 0){
            gridPane.getRowConstraints().remove(0);
        }
        while (gridPane.getColumnConstraints().size()>0){
            gridPane.getColumnConstraints().remove(0);
        }
        //Customizer some values of gridPane
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPrefSize(1200, 1200);
        return  gridPane;
    }
   /**
    * Customize a pane to individual view of a video
    * */
    Pane getPaneCustomized(String videoName){
        int paneSize = 90;
        Pane pane = new Pane();
        pane.setMaxSize(paneSize, paneSize);
        pane.setMinSize(paneSize, paneSize);
        pane.setPrefSize(paneSize, paneSize);
        pane.getChildren().add(new Label(videoName));
        pane.getStyleClass().add("video-pane");
        pane.setOnMouseClicked(event -> {
            playVideo(videoName);
        });
        return  pane;
    }
    /**
     * @author Andres Acosta
     * is in charge of call a video player an send it a video to be reproduced
     *
     * */
    void playVideo(String video){
        try {
            File file = new File(Properties.getProperty("pathVideos")+"/"+video);
            Properties.map.put("currentVideo",file.toURI().toString());
            System.out.println(Properties.map.get("currentVideo"));
            Player player = new Player();
            Stage stage = new Stage();
            player.start(stage);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            System.out.println(e.getMessage());
            alert.showAndWait();
        }
    }
}
