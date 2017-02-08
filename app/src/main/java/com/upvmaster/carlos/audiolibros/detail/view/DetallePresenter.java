package com.upvmaster.carlos.audiolibros.detail.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;

import com.upvmaster.carlos.audiolibros.R;
import com.upvmaster.carlos.audiolibros.main.data.BooksRepository;
import com.upvmaster.carlos.audiolibros.main.data.datasources.Libro;
import com.upvmaster.carlos.audiolibros.main.data.datasources.LibroStorage;
import com.upvmaster.carlos.audiolibros.main.data.datasources.LibrosSingleton;

import java.io.IOException;

import static android.R.attr.id;

/**
 * Created by Carlos on 25/01/2017.
 */

public class DetallePresenter {
    private Libro book;
    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private Handler mHandler;
    private Runnable run_tiempo;
    private Context context;
    private View view;

    public DetallePresenter(Context context,MediaController mediaController, View view) {
        this.context = context;
        this.mediaPlayer = new MediaPlayer();
        this.mediaController = mediaController;
        this.mHandler = new Handler();
        this.view = view;
    }

    public OnZoomSeekBarListener getlistenerZoomBar(){
        return new OnZoomSeekBarListener() {
            @Override
            public void colocarAudio(int posicion) {
                if(mediaPlayer!=null)
                    mediaPlayer.seekTo(posicion*1000);
            }
        };
    }

    public void ponInfoLibro(String key) {
        this.book = LibrosSingleton.getInstance(context).getAdaptadorLibros().getItemByKey(key);
        view.ponInfoLibro(book);
    }

    public void onPrepareMediaPlayer(Context context, android.view.View anchovista, MediaController.MediaPlayerControl controller, ZoomSeekBar zoombar){
        Log.d("Audiolibros", "Entramos en onPrepared de MediaPlayer");
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferencias.getBoolean("pref_autoreproducir", true)) {
            mediaPlayer.start();
        }
        mediaController.setMediaPlayer(controller);
        mediaController.setAnchorView(anchovista);
        mediaController.setPadding(0, 0, 0, 110);
        mediaController.setEnabled(true);
        mediaController.show();
        //Poner el Zoombar
        int duracionAudio = mediaPlayer.getDuration() / 1000;
        zoombar.setValMin(0);
        zoombar.setEscalaMin(0);
        zoombar.setEscalaIni(0);
        zoombar.setEscalaRaya(duracionAudio/50);
        zoombar.setEscalaRayaLarga(10);
        zoombar.setValMax(duracionAudio);
        zoombar.setEscalaMax(duracionAudio);
        zoombar.setVisibility(android.view.View.VISIBLE);
    }

    public void onStop(){
        hideMediaController();
        mHandler.removeCallbacks(run_tiempo);
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        } catch (Exception e) {
            Log.d("Audiolibros", "Error en mediaPlayer.stop()");
        }
    }

    public Runnable getRun_tiempo(final ZoomSeekBar zoomView){
        run_tiempo =  new Runnable() {

            @Override
            public void run() {
                if(mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    zoomView.setVal(mCurrentPosition);
                }
                mHandler.postDelayed(this, 1000);
            }
        };
        return run_tiempo;
    }

    public void initMediaPlayer(MediaPlayer.OnPreparedListener onPreparedListener, Uri audio){
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(onPreparedListener);
        try {
            mediaPlayer.setDataSource(context,audio);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("Audiolibros", "ERROR: No se puede reproducir " + audio, e);
        }
    }

    public void startAudio(){
        mediaPlayer.start();
    }
    public void pauseAudio(){
        mediaPlayer.pause();
    }
    public void stopAudio(){
        mediaPlayer.stop();
    }
    public void seekToAudio(int pos){
        mediaPlayer.seekTo(pos);
    }
    public int getDuration(){
        return  mediaPlayer.getDuration();
    }
    public boolean audioIsPlaying(){
        return mediaPlayer.isPlaying();
    }
    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }
    public void showMediaController(){
        mediaController.show();
    }
    public void hideMediaController(){
        mediaController.hide();
    }



    public interface View {
        void ponInfoLibro(Libro book);
    }
}
