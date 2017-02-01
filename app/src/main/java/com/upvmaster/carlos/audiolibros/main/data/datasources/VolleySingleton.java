package com.upvmaster.carlos.audiolibros.main.data.datasources;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by carlos.cupeiro on 25/01/2017.
 */
public class VolleySingleton {
    private static VolleySingleton instance;
    private static RequestQueue colaPeticiones;
    private static ImageLoader lectorImagenes;

    public static VolleySingleton getInstance(Context context) {
        if(instance == null){
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    private VolleySingleton(Context context) {
        colaPeticiones = Volley.newRequestQueue(context);
        lectorImagenes = new ImageLoader(colaPeticiones, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
        });
    }

    public static RequestQueue getColaPeticiones() {
        return colaPeticiones;
    }

    public static ImageLoader getLectorImagenes() {
        return lectorImagenes;
    }
}
