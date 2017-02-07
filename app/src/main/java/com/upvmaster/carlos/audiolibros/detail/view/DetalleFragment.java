package com.upvmaster.carlos.audiolibros.detail.view;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.upvmaster.carlos.audiolibros.R;
import com.upvmaster.carlos.audiolibros.main.data.BooksRepository;
import com.upvmaster.carlos.audiolibros.main.data.datasources.LibroSharedPrefenceStorage;
import com.upvmaster.carlos.audiolibros.main.view.MainActivity;
import com.upvmaster.carlos.audiolibros.main.data.datasources.Libro;
import com.upvmaster.carlos.audiolibros.main.data.datasources.LibrosSingleton;
import com.upvmaster.carlos.audiolibros.main.data.datasources.VolleySingleton;

import java.io.IOException;

import android.os.Handler;

import static android.R.attr.id;

/**
 * Created by carlos.cupeiro on 22/12/2016.
 */

public class DetalleFragment extends Fragment implements View.OnTouchListener, MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl,DetallePresenter.View {
    public static String ARG_ID_LIBRO = "id_libro";
    private MediaController mediaController;
    private View vista;
    private DetallePresenter presenter;
    private ZoomSeekBar zoombar;


    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        vista = inflador.inflate(R.layout.fragment_detalle, contenedor, false);
        Bundle args = getArguments();
        String key="";
        if (args != null) {
            key = args.getString(ARG_ID_LIBRO);
        }
        presenter = new DetallePresenter(vista.getContext(), this);
        presenter.ponInfoLibro(key);
        mediaController = new MediaController(getActivity());
        //Poner aquí los cambios en ZoomSeekBar
        zoombar = (ZoomSeekBar) vista.findViewById(R.id.zoombar);
        zoombar.setVisibility(View.INVISIBLE);
        //Probar con el seekTo para cambiar el zoomSeekBar
        zoombar.setOnZoomSeekBarListener(presenter.getlistenerZoomBar());
        //Make sure you update Seekbar on UI thread
        getActivity().runOnUiThread(presenter.getRun_tiempo(zoombar));
        return vista;
    }

    public void ponInfoLibro(String key) {
        presenter.ponInfoLibro(key);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("Audiolibros", "Entramos en onPrepared de MediaPlayer");
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (preferencias.getBoolean("pref_autoreproducir", true)) {
            mediaPlayer.start();
        }
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(getView().findViewById(
                R.id.fragment_detalle));
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
        zoombar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        DetalleFragment detalleFragment = (DetalleFragment) getFragmentManager().findFragmentById(R.id.detalle_fragment);
        if (detalleFragment == null) {
            ((MainActivity) getActivity()).mostrarElementos(false);
        }
        super.onResume();
    }

    @Override
    public boolean onTouch(View vista, MotionEvent evento) {
        mediaController.show();
        return false;
    }

    @Override
    public void onStop() {
        mediaController.hide();
        presenter.onStop();
        super.onStop();
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        try {
            return presenter.getCurrentPosition();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int getDuration() {
        return presenter.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return presenter.audioIsPlaying();
    }

    @Override
    public void pause() {
        presenter.pauseAudio();
    }

    @Override
    public void seekTo(int pos) {
        presenter.seekToAudio(pos);
    }

    @Override
    public void start() {
        presenter.startAudio();
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void ponInfoLibro(Libro book) {
        ((TextView) vista.findViewById(R.id.titulo)).setText(book.getTitulo());
        ((TextView) vista.findViewById(R.id.autor)).setText(book.getAutor());
        ((NetworkImageView) vista.findViewById(R.id.portada)).
                setImageUrl(book.getUrlImagen(), VolleySingleton.getInstance(vista.getContext()).getLectorImagenes());
        vista.setOnTouchListener(this);
        Uri audio = Uri.parse(book.getUrlAudio());
        presenter.initMediaPlayer(this,audio);
    }
}
