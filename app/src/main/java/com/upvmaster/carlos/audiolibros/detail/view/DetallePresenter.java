package com.upvmaster.carlos.audiolibros.detail.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.MediaController;

import com.upvmaster.carlos.audiolibros.main.data.BooksRepository;
import com.upvmaster.carlos.audiolibros.main.data.datasources.Libro;
import com.upvmaster.carlos.audiolibros.main.data.datasources.LibroStorage;
import com.upvmaster.carlos.audiolibros.main.data.datasources.LibrosSingleton;

/**
 * Created by Carlos on 25/01/2017.
 */

public class DetallePresenter {
    private Libro book;
    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private View view;

    public DetallePresenter(int id, Context context, View view) {
        this.book = LibrosSingleton.getInstance(context).getListaLibros().get(id);
        this.mediaPlayer = new MediaPlayer();
        this.mediaController = new MediaController(context);
        this.view = view;
    }
    public void playAudio(){
        mediaPlayer.start();
    }
    public void pauseAudio(MediaPlayer mediaPlayer){

    }
    public void stopAudio(MediaPlayer mediaPlayer){

    }

    public interface View {
        void ponInfoLibro(Libro book);
        void updateZoomSeekBar();
    }
}
