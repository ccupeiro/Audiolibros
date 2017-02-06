package com.upvmaster.carlos.audiolibros.detail.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.MediaController;

import com.upvmaster.carlos.audiolibros.main.data.BooksRepository;
import com.upvmaster.carlos.audiolibros.main.data.datasources.Libro;
import com.upvmaster.carlos.audiolibros.main.data.datasources.LibroStorage;
import com.upvmaster.carlos.audiolibros.main.data.datasources.LibrosSingleton;

import java.io.IOException;

/**
 * Created by Carlos on 25/01/2017.
 */

public class DetallePresenter {
    private Libro book;
    private MediaPlayer mediaPlayer;
    private MediaPlayer.OnPreparedListener onPreparedListener;
    private Handler mHandler;
    private Runnable run_tiempo;
    private Context context;
    private View view;

    public DetallePresenter(Context context, View view) {
        this.context = context;
        this.mediaPlayer = new MediaPlayer();
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

    public void ponInfoLibro(int id) {
        this.book = LibrosSingleton.getInstance(context).getAdaptadorLibros().getItemById(id);
        view.ponInfoLibro(book);
    }

    public void onStop(){
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



    public interface View {
        void ponInfoLibro(Libro book);
    }
}
