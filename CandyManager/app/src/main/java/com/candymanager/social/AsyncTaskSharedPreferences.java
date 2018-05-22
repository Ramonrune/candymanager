package com.candymanager.social;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Usuario on 21/05/2018.
 */

public class AsyncTaskSharedPreferences {

    private static final String PREFERENCIA = "ASYNC_TASK";
    private int quantidade = 0;
    private Context context;
    public AsyncTaskSharedPreferences(Context context, int quantidade){
        this.context = context;
        this.quantidade = quantidade;
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE).edit();
        editor.putInt("number", 0);
        editor.apply();
    }


    public void incrementaCounter(){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        int count = prefs.getInt("number", 0);

        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE).edit();
        count++;

        System.out.println("Counter valeeee " + count);
        editor.putInt("number", count);

        editor.apply();
    }

    public boolean terminou(){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        int count = prefs.getInt("number", 0);

        System.out.println("Chamou terminou valendoooo " + count);

        if(count == quantidade){
            SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE).edit();
            editor.putInt("number", 0);
            editor.apply();

            return true;
        }


        return false;
    }
}
