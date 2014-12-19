package com.example.virtualstreet;

import java.util.Hashtable;

public class Link {
	
	private static Hashtable<String, Integer> imagenes = new Hashtable<String, Integer>();
	private static Hashtable<String, Integer> zonas = new Hashtable<String, Integer>();
	
	
	static int getImagen(String key){
		imagenes.put("JUV", R.drawable.juv);
		imagenes.put("DULT", R.drawable.dult);
		imagenes.put("BILA", R.drawable.bila);
		return imagenes.get(key);
	}

	static int getZona(String key){
		zonas.put("2", R.drawable.stones);
		zonas.put("3", R.drawable.stones);
		zonas.put("4", R.drawable.stones);
		zonas.put("5", R.drawable.stones);
		return zonas.get(key);
	}
}
