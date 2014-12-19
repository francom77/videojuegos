package com.example.virtualstreet;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Prefs {


        
    public static SharedPreferences getSharedPreferences(Context context){
    	 return context.getSharedPreferences("userdata", 0);

    }
    
    public static void saveInt(String key, int value, Context context){
    	context.getSharedPreferences("userdata", 0).edit().putInt(key, value).commit();

    }
    
    public static void saveString(String key, String value, Context context){
    	context.getSharedPreferences("userdata", 0).edit().putString(key, value).commit();
   	 	
    }
    
    
}