package com.andrewakosta.videoPlayer.utilities;

import javafx.scene.media.MediaPlayer;

import java.util.HashMap;


public class Properties {
    /*Media pLayer Objects*/
    public  static HashMap<String, MediaPlayer> mediaPlayerHashMap = new HashMap<String, javafx.scene.media.MediaPlayer>();
    /*Text Properties*/
    public static  HashMap<String,String> map = new HashMap<String,String>();

    public  static  String getProperty(String property){
        if(map.containsKey(property)){
            return  map.get(property);
        }else {
            return "The requested key does not exist";
        }
    }
    public static void  setProperty(String key, String value){
        map.put(key, value);
    }

}
