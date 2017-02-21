package com.example.mammam.cowchat.controll;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by Mam  Mam on 1/11/2017.
 */

public class ManagerRington {
    private MediaPlayer mPlayer;
    private Context mContext;

    public ManagerRington(Context mContext) {
        this.mContext = mContext;
    }

    public void openMedia(int id,boolean loop){
        release();
        mPlayer = MediaPlayer.create(mContext,id);

        if(loop){
            mPlayer.setLooping(true);
        }


    }

    public void  setType(){
        mPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);

    }

    public void openMedia(Uri uri, boolean loop){
        release();
        mPlayer = MediaPlayer.create(mContext,uri);
        if(loop){
            mPlayer.setLooping(true);
        }

    }
    public void setVolume(int volume){
        mPlayer.setVolume(volume,volume);
    }

    public boolean isPlay(){
        return mPlayer.isPlaying();
    }

    public void play(){
        if(mPlayer != null && !mPlayer.isPlaying() ){
            mPlayer.start();
        }
    }

    public void release(){
        if (mPlayer != null){
            mPlayer.release();

        }
    }

    public void stop(){
        if(mPlayer != null && mPlayer.isPlaying()){
            mPlayer.stop();
        }
    }
    public void pause(){
        if(mPlayer != null && mPlayer.isPlaying()){
            mPlayer.pause();
        }
    }
}
