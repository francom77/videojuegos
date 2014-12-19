package com.example.virtualstreet;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class DetailZoneActivity extends Activity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail_zone);
		getCurrentZona(getIntent().getExtras().getString("zona"));
		getKing(getIntent().getExtras().getString("zona"));
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
	
	private void setKingName(String nombre){
		TextView name = (TextView) findViewById(R.id.nombre);
		name.setText(nombre);
	}

	private void setKingPuntos(String puntos){
		TextView p = (TextView) findViewById(R.id.puntos);
		p.setText("Puntaje: " + puntos);
	}

	private void getKing(String idZona){

		String urlfilter = "UsuarioHasZonas/?filter[where][zonaIdzona]="+idZona+"&filter[order]=puntaje DESC&filter[limit]=1";
		RestClient.get(urlfilter, null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray usersAPI) {
				for (int i = 0; i < usersAPI.length(); i++) {
					try {
						JSONObject JSONuser = usersAPI.getJSONObject(i);
						setKingPuntos(Integer.toString(JSONuser.getInt("puntaje")));
						RestClient.get("Usuarios/"+ JSONuser.getInt("usuarioIdusuario"), null, new JsonHttpResponseHandler() {
							@Override
							public void onSuccess(int statusCode, Header[] headers,
									JSONObject userAPI) {
								try {
									setKingName(userAPI.getString("nombre"));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						});

					} catch (JSONException e) {

						e.printStackTrace();
					}
				}
				
			}
			
		});


	}

	private void getCurrentZona(String idZona){

		RestClient.get("Zonas/"+idZona, null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject JSONzona) {
				try {	
					Zona currentZona = new Zona(JSONzona.getInt("idzona"),
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

