package com.andrewakosta.videoPlayer;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;

import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    private TextField seeker;

    @FXML
    private GridPane gridPaneMain;

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


    public void handleBtnSearchVideos(MouseEvent mouseEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Videos Folder");
        Stage stage = new Stage();
        File file = directoryChooser.showDialog(stage);

        if(file != null ){
            System.out.println(file.getPath());
            seeker.setText(file.getPath());
        }else {
            System.out.println("Something was wrong");
        }
    }

    public void goToVideosView(MouseEvent mouseEvent) {
        gridPaneMain.getChildren().clear();
    }
}
