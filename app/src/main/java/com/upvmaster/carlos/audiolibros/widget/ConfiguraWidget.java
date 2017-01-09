package com.upvmaster.carlos.audiolibros.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.upvmaster.carlos.audiolibros.R;

/**
 * Created by carlos.cupeiro on 09/01/2017.
 */

public class ConfiguraWidget extends Activity {
    int widgetId;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configura_widget);
        editText = (EditText) findViewById(R.id.editText1);
        setResult(RESULT_CANCELED);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        }
        widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
    }

    public void buttonOK(View view) {
        int id;
        try {
            id = Integer.parseInt(editText.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "No es un número", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences pref = getSharedPreferences(
                "com.upvmaster.carlos.audiolibros_internal", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("ultimo", id);
        editor.commit();
        MiWidgetProvider.actualizaWidget(this, widgetId);
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
