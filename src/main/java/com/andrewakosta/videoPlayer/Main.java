package com.andrewakosta.videoPlayer;

import com.andrewakosta.videoPlayer.controller.VideosView;
import com.andrewakosta.videoPlayer.utilities.Properties;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;

import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * @author Andres Acosta
 * is in charge of genrate the first view to choose teh directory of video files
 * */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    private TextField seeker;

    @FXML
    private GridPane gridPaneMain;


    /**
     * Main method to create a window.
     * */
    @Override
    public void start(Stage primaryStage) {

        seeker = new TextField();
        gridPaneMain = new GridPane();


        Parent root  = null;
        try{
            root = FXMLLoader.load(getClass().getResource("main.fxml"));
        } catch (IOException e) {
            System.out.println("An error has happened...!");
        }


        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    /**
     * Generate instance  of DirectChooser class to get a directory of videos and then set the value in a hasMap
     *
     * */
    public void handleBtnSearchVideos(MouseEvent mouseEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Videos Folder");
        Stage stage = new Stage();
        File file = directoryChooser.showDialog(stage);

        if(file != null ){
            Properties.setProperty("pathVideos",file.getPath());
            seeker.setText(file.getPath());
        }else {
            System.out.println("Something was wrong");
        }
    }

    /**
     * Generate a new instance of videos view to show them
     * */
    public void goToVideosView(MouseEvent mouseEvent) throws Exception {
        gridPaneMain.getChildren().clear();
        VideosView videosView = new VideosView();
        videosView.showAllVideosFromAnyDirectory(gridPaneMain);

    }
}
