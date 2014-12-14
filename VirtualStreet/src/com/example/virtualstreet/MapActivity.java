package com.example.virtualstreet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MapActivity extends ActionBarActivity implements LocationListener,
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private GoogleMap mMap;
	private List<Zona> listZonas;
	private LocationRequest mLocationRequest;
	private LocationClient mLocationClient;
	private boolean inside;
	private Zona inZona;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getZonas();
		setContentView(R.layout.activity_map);
		inside = false;

		mLocationRequest = LocationRequest.create();
		mLocationRequest
				.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest
				.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);
		mLocationClient = new LocationClient(this, this, this);

		setUpMapIfNeeded();

	}

	private void getZonas() {
		listZonas = new ArrayList<Zona>();
		RestClient.get("Zonas", null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray zonas) {
				for (int i = 0; i < zonas.length(); i++) {
					try {
						JSONObject JSONzona = zonas.getJSONObject(i);
						Zona zona = new Zona(JSONzona.getInt("idzona"),
								JSONzona.getInt("radio"), JSONzona
										.getString("nombre"), JSONzona
										.getString("descripcion"), JSONzona
										.getDouble("latitude"), JSONzona
										.getDouble("longitude"));
						drawZona(zona);
						listZonas.add(zona);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void drawZona(Zona zona) {
		mMap.addCircle(new CircleOptions().center(zona.getLatLng())
				.radius(zona.getRadio()).strokeColor(Color.RED).strokeWidth(3));
		mMap.addMarker(new MarkerOptions()
				.position(zona.getLatLng())
				.title(zona.getDescripcion())
				.snippet(Integer.toString(zona.getIdzona())));
		mMap.setOnMarkerClickListener(new OnMarkerClickListener()
		{
			@Override
			public boolean onMarkerClick(Marker arg0){
				Intent i = new Intent(MapActivity.this, DetailZoneActivity.class);
				i.putExtra("zona", arg0.getSnippet());
				startActivity(i);
				return true;
			}
		});
	}

	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		mMap.setMyLocationEnabled(true);
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				-27.4511792, -58.9864681), 16.0f));
	}

	@Override
	protected void onStop() {
		if (mLocationClient.isConnected()) {
			mLocationClient.removeLocationUpdates(this);
		}
		mLocationClient.disconnect();
		super.onStop();
	}

	@Override
	protected void onStart() {
		super.onStart();
		mLocationClient.connect();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {

                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

                /*
                * Thrown if Google Play services canceled the original
                * PendingIntent
                */

            } catch (IntentSender.SendIntentException e) {

                // Log the error
                e.printStackTrace();
            }
        } else {

            // If no resolution is available, display a dialog to the user with the error.
            showErrorDialog(connectionResult.getErrorCode());
        }
	}

	@Override
	public void onConnected(Bundle arg0) {
		mLocationClient.requestLocationUpdates(mLocationRequest, this);
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location location) {
		if (inside){
			float[] results = new float[1];
			Location.distanceBetween(location.getLatitude(), location.getLongitude(), inZona.getLatitude(), inZona.getLongitude(),results);
			if (results[0]>inZona.getRadio()) { //Salio de la zona 
				inside = false;
				hideWhatToFind();
				inZona = null;
			}
		}else{
			for (Iterator<Zona> iterator = listZonas.iterator(); iterator.hasNext();) {
				Zona zona = (Zona) iterator.next();
				float[] results = new float[1];
				Location.distanceBetween(location.getLatitude(), location.getLongitude(), zona.getLatitude(), zona.getLongitude(),results);
				if (results[0]<=zona.getRadio()) { //Entro a la zona
					inside = true;
					inZona = zona;
					showWhatToFind();
					break;
				}
			}
		}
	}

	private void showErrorDialog(int errorCode) {

        // Get the error dialog from Google Play services
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
            errorCode,
            this,
            LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

        // If Google Play services can provide an error dialog
        if (errorDialog != null) {

            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);

            // Show the error dialog in the DialogFragment
            errorFragment.show(getSupportFragmentManager(), LocationUtils.APPTAG);
        }
    }
	
	public static class ErrorDialogFragment extends DialogFragment {
		private Dialog mDialog;
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
	}
	
	public class WhatToFind extends DialogFragment{
		
		public WhatToFind(){
			
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			Dialog dialog = super.onCreateDialog(savedInstanceState);
			dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			return dialog;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			View view = inflater.inflate(R.layout.dialog_fragment, container);
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(MapActivity.this,vuforia.CloudReco.class);
					startActivity(i);
				}
			});
			// Cambiar la imagen
			return view;
		}
		
	}
	
	private void showWhatToFind(){
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		WhatToFind wtf = new WhatToFind();
		wtf.show(fm, "wtf");
	}
	
	private void hideWhatToFind(){
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		Fragment wtf = fm.findFragmentByTag("wtf");
		if (wtf != null){
			((DialogFragment) wtf).dismiss(); 
		}
	}
	
}
