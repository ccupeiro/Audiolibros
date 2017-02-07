package com.upvmaster.carlos.audiolibros.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.upvmaster.carlos.audiolibros.R;
import com.upvmaster.carlos.audiolibros.common.data.datasources.FirebaseDatabaseSingleton;
import com.upvmaster.carlos.audiolibros.main.data.datasources.LibrosSingleton;
import com.upvmaster.carlos.audiolibros.main.view.MainActivity;
import com.upvmaster.carlos.audiolibros.main.data.BooksRepository;
import com.upvmaster.carlos.audiolibros.main.data.datasources.Libro;
import com.upvmaster.carlos.audiolibros.main.data.datasources.LibroSharedPrefenceStorage;
import com.upvmaster.carlos.audiolibros.main.domain.GetLastBook;
import com.upvmaster.carlos.audiolibros.main.domain.HasLastBook;

import static android.R.attr.id;
import static com.upvmaster.carlos.audiolibros.main.data.datasources.Libro.LIBRO_EMPTY;

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
        String key = "";
        BooksRepository booksRepository = new BooksRepository(LibroSharedPrefenceStorage.getInstance(context));
        if(new HasLastBook(booksRepository).execute()){
            key = new GetLastBook(booksRepository).execute();
        }
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        Libro book = LibrosSingleton.getInstance(context).getAdaptadorLibros().getItemByKey(key);
        if (book!=null && book!=LIBRO_EMPTY) {
            Libro libro = Libro.ejemploLibros().get(id);
            remoteViews.setTextViewText(R.id.tv_titulo, book.getTitulo());
            remoteViews.setTextViewText(R.id.tv_autor, book.getAutor());
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
