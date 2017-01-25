package com.upvmaster.carlos.audiolibros.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.upvmaster.carlos.audiolibros.R;
import com.upvmaster.carlos.audiolibros.Main.MainActivity;
import com.upvmaster.carlos.audiolibros.entities.Libro;
import com.upvmaster.carlos.audiolibros.entities.LibroStorage;
import com.upvmaster.carlos.audiolibros.entities.LibroSharedPrefenceStorage;

/**
 * Created by Carlos on 08/01/2017.
 */

public class MiWidgetProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] widgetIds) {
        for (int widgetId : widgetIds) {
            actualizaWidget(context, widgetId);
        }
    }

    public static void actualizaWidget(Context context, int widgetId) {
        int id = 0;
        LibroStorage libroStoragePreferencesStorage =LibroSharedPrefenceStorage.getInstance(context);
        if(libroStoragePreferencesStorage.hasLastBook()){
            id = libroStoragePreferencesStorage.getLastBook();
        }
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        if (id >= 0 && id < Libro.ejemploLibros().size()) {
            Libro libro = Libro.ejemploLibros().get(id);
            remoteViews.setTextViewText(R.id.tv_titulo, libro.titulo);
            remoteViews.setTextViewText(R.id.tv_autor, libro.autor);
        } else {
            remoteViews.setTextViewText(R.id.tv_titulo, "No hay último libro");
            remoteViews.setTextViewText(R.id.tv_autor, "");
        }
        //Eventos
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.iv_hamb, pendingIntent);
        AppWidgetManager.getInstance(context).updateAppWidget(widgetId, remoteViews);
    }

}
