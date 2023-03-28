package minesweeper;


import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;

public class Music extends Frame {
    static long lastUpdate=System.currentTimeMillis();
    static long timeLaps=500;
    public static synchronized void Click(){
        if(System.currentTimeMillis()-lastUpdate<timeLaps)
            return;
        lastUpdate=System.currentTimeMillis();
        play("src/Music/点开方块.wav",false);
    }
    public static synchronized void boom(){
        if(System.currentTimeMillis()-lastUpdate<timeLaps)
            return;
        lastUpdate=System.currentTimeMillis();
        play("src/Music/爆炸.wav",false);
    }
    public static synchronized void BGM1(){
        play("src/Music/BGM1.wav",false);
    }
    public static synchronized void flag(){
        if(System.currentTimeMillis()-lastUpdate<timeLaps)
            return;
        lastUpdate=System.currentTimeMillis();
        play("src/Music/插旗.wav",false);
    }
//    public static synchronized void BGM2(){
//        play("src/Music/BGM2.mp3",false);
//    }
    public static synchronized void play(String name,boolean loop) {
        try {
            InputStream in = new FileInputStream(name);
            AudioStream audioStream = new AudioStream(in);
            if(loop){
                ContinuousAudioDataStream cad=new ContinuousAudioDataStream(audioStream.getData());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AudioPlayer.player.start(cad);
                    }
                }).start();
            }
            else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AudioPlayer.player.start(audioStream);
                    }
                }).start();
                AudioPlayer.player.stop(audioStream);
            }
        }
        catch(Exception e){

        }
    }
}
