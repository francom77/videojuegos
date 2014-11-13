package com.example.virtualstreet;

import com.google.android.gms.maps.model.LatLng;

public class Zona {
	private int idzona;
	private int radio;
	private String nombre;
	private String descripcion;
	private double latitude;
	private double longitude;
	public Zona(int idzona, int radio, String nombre, String descripcion,
			double latitude, double longitude) {
		super();
		this.idzona = idzona;
		this.radio = radio;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public int getIdzona() {
		return idzona;
	}
	public int getRadio() {
		return radio;
	}
	public String getNombre() {
		return nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	
	public LatLng getLatLng(){
		return new LatLng(getLatitude(), getLongitude());
	}
	
}
