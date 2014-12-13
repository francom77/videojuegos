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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class DetailZoneActivity extends Activity {

	private Zona currentZona;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_zone);
		setTitle("Plaza");
		Bundle bundle = getIntent().getExtras();
		getCurrentZona(getIntent().getExtras().getString("zona"));

		Toast.makeText(this, "Dentro de zona numero "+ currentZona.getNombre(), Toast.LENGTH_LONG).show();
		
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
	
	public void getCurrentZona(String idZona){
		RequestParams params = new RequestParams();
		params.put("ID", idZona);
		RestClient.get("Zonas", params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject JSONzona) {
					try {	
						Zona zona = new Zona(JSONzona.getInt("idzona"),
								JSONzona.getInt("radio"), JSONzona
										.getString("nombre"), JSONzona
										.getString("descripcion"), JSONzona
										.getDouble("latitude"), JSONzona
										.getDouble("longitude"));
						currentZona = zona;
					System.out.println(zona.getNombre());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			
		});
	
	
	}
}

