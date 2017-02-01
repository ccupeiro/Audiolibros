package com.upvmaster.carlos.audiolibros.detail.view;

import android.media.MediaPlayer;
import android.media.session.MediaController;

import com.upvmaster.carlos.audiolibros.main.data.datasources.Libro;

/**
 * Created by Carlos on 25/01/2017.
 */

public class DetallePresenter {
    private Libro book;
    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private View view;

    public DetallePresenter(Libro book, MediaPlayer mediaPlayer, MediaController mediaController, View view) {
        this.book = book;
        this.mediaPlayer = mediaPlayer;
        this.mediaController = mediaController;
        this.view = view;
    }

    public interface View {
        void ponInfoLibro(Libro book);
        void playAudio(MediaPlayer mediaPlayer);
        void pauseAudio(MediaPlayer mediaPlayer);
        void stopAudio(MediaPlayer mediaPlayer);
    }
}
