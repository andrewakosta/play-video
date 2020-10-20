package com.andrewakosta.videoPlayer.utilities;

public class MainUtilities {
    public  static  String getDefaultDirectory(){

        String SO_ = null;

        String SO = System.getProperty("os.name");

        switch (SO){
            case "Linux":
                SO_ = "/home/andres/Videos";
                break;
            default:
                SO_ = "...Select you directory ofo videos...";

        }
        return  SO_;
    }
}
