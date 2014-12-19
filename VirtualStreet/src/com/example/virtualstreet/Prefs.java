package com.example.virtualstreet;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Prefs {

    public static SharedPreferences sp;
        
    public static SharedPreferences getSharedPreferences(Context context){
    	 sp = context.getSharedPreferences("userdata", 0);
    	 return sp;

    }
    
    public static void saveInt(String key, int value, Context context){
    	sp = context.getSharedPreferences("userdata", 0);
   	 	SharedPreferences.Editor editor = sp.edit();
   	 	editor.putInt(key, value);
   	 	editor.commit();
    }
    
    public static void saveString(String key, String value, Context context){
    	sp = context.getSharedPreferences("userdata", 0);
   	 	SharedPreferences.Editor editor = sp.edit();
   	 	editor.putString(key, value);
   	 	editor.commit();
    }
    
    
}