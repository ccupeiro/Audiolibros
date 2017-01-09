package com.upvmaster.carlos.audiolibros.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;


/**
 * Created by Carlos on 05/11/2016.
 */

public class ServicioMusica extends Service {

    private static final int ID_NOTIFICACION_CREAR = 1;

    MediaPlayer reproductor;

    @Override
    public void onCreate() {
        reproductor = null;//MediaPlayer.create(this, R.raw.monkey);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /*//PendingIntents
        Intent iMapa = new Intent(this, Mapa_Activity.class);
            //Mensaje
        String message = intent.getStringExtra(ReceptorSMS.MESSAGE_KEY);
        if(message!=null)
            iMapa.putExtra(ReceptorSMS.MESSAGE_KEY,message);
        PendingIntent pIntentMapa = PendingIntent.getActivity( this, 0,iMapa , 0);
        PendingIntent pIntentMusicaOff = PendingIntent.getActivity( this, 0, new Intent(this, MusicOFF_Activity.class), 0);
        NotificationCompat.Builder notific = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.serviciomusica_notificacion_title))
                .setSmallIcon(R.drawable.ic_music_player)
                .setContentText(getString(R.string.serviciomusica_notificacion_text))
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setDefaults(Notification.DEFAULT_LIGHTS);
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                                                    R.drawable.ic_location,
                                                    getString(R.string.serviciomusica_notificacion_action_text), pIntentMapa)
                                                    .build();
        notific.addAction(action);
        notific.setContentIntent(pIntentMusicaOff);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_NOTIFICACION_CREAR, notific.build());*/
        reproductor.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        /*NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(ID_NOTIFICACION_CREAR);*/
        reproductor.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
