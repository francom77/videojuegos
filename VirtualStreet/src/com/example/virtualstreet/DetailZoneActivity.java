package com.example.virtualstreet;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DetailZoneActivity extends Activity {

	public Zona currentZona;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_zone);
		setTitle("Plaza");
		getCurrentZona(getIntent().getExtras().getString("zona"));		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_zone, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void setData(String nombre, String description){
		TextView title = (TextView) findViewById(R.id.zone_title);
		TextView descripcion = (TextView) findViewById(R.id.descripcion_text);
		title.setText(nombre);
		descripcion.setText(description);
	}
	
	private void getCurrentZona(String idZona){

		RestClient.get("Zonas/"+idZona, null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject JSONzona) {
					try {	
						currentZona = new Zona(JSONzona.getInt("idzona"),
								JSONzona.getInt("radio"), JSONzona
										.getString("nombre"), JSONzona
										.getString("descripcion"), JSONzona
										.getDouble("latitude"), JSONzona
										.getDouble("longitude"));
						setData(currentZona.getNombre(), currentZona.getDescripcion());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			
		});
	
	
	}
}

