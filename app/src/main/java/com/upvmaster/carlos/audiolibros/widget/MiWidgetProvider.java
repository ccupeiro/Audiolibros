package com.upvmaster.carlos.audiolibros.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.upvmaster.carlos.audiolibros.R;
import com.upvmaster.carlos.audiolibros.activities.MainActivity;
import com.upvmaster.carlos.audiolibros.entities.Aplicacion;
import com.upvmaster.carlos.audiolibros.entities.Libro;

import static android.R.attr.id;
import static android.content.Context.MODE_PRIVATE;

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
        int id = leerTituloAutor(context, widgetId);
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

    private static int leerTituloAutor(Context context, int widgetId) {
        SharedPreferences pref = context.getSharedPreferences(
                "com.upvmaster.carlos.audiolibros_internal", MODE_PRIVATE);
        int id = pref.getInt("ultimo", -1);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("ultimo_" + widgetId, id);
        editor.commit();
        return id;
    }

}
