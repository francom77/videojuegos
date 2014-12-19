package com.example.virtualstreet;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Prefs {

    public static SharedPreferences sp;
        
    public static SharedPreferences getSharedPreferences(Context context){
    	 sp = context.getSharedPreferences("userdata", 0);
    	 SharedPreferences.Editor editor = sp.edit();
    	 editor.putInt("id", 1);
    	 editor.putInt("character", 1);
    	 editor.putString("idFB", "franco.escobar");
    	 editor.commit();
    	 return sp;

    }
    
    public static void saveInt(String key, int value, Context context){
    	sp = context.getSharedPreferences("userdata", 0);
   	 	SharedPreferences.Editor editor = sp.edit();
   	 	editor.putInt(key, value);
   	 	editor.commit();
   	 	
   	 	Log.i("prueba", Integer.toString(sp.getInt(key, 1000)));
    }
    
    
}