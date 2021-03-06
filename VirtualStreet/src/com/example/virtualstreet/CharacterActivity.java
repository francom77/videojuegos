package com.example.virtualstreet;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class CharacterActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_character);
		// obteniendo fuente
		Typeface font = Typeface.createFromAsset(getAssets(),
				"gloriahallelujah.ttf");
		// obtenendo elementos
		TextView title = (TextView) findViewById(R.id.title_char);
		TextView desc = (TextView) findViewById(R.id.desc_char);
		Button btnChar = (Button) findViewById(R.id.btn_char);
		// seteando la fuente
		title.setTypeface(font);
		desc.setTypeface(font);
		btnChar.setTypeface(font);

		Bundle bundle = getIntent().getExtras();
		getCurrentCharacter(bundle.getString("character"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.character, menu);
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

	private void setData(String name, String descripcion, String id){
		TextView title = (TextView) findViewById(R.id.title_char);
		TextView desc = (TextView) findViewById(R.id.desc_char);

		title.setText(name);
		desc.setText(descripcion);

		ImageView imagen = (ImageView) findViewById(R.id.imageView1);
		imagen.setImageResource(Link.getImagen(name));

		Button btn = (Button) findViewById(R.id.btn_char);
		btn.setContentDescription(id);

	}
	private void getCurrentCharacter(String idPersonaje){

		RestClient.get("Personajes/"+idPersonaje, null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject JSONchar) {
				try {	

					setData(JSONchar.getString("nombre"), JSONchar.getString("descripcion"), Integer.toString(JSONchar.getInt("idpersonaje")));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		});


	}

	public void saveCharacter(View view){
		
		int id = Integer.valueOf((String) view.getContentDescription());
		SharedPreferences sp = Prefs.getSharedPreferences(getApplication().getApplicationContext());
		Prefs.saveInt("character", id , getApplication().getApplicationContext());
		Log.i("idtoapi", Integer.toString(sp.getInt("id", 100)));
		Log.i("character", Integer.toString(sp.getInt("character", 100)));
		Log.i("character", sp.getString("idFB", "default"));
		RequestParams params = new RequestParams();
		params.put("idusuario", sp.getInt("id", 100) );
		params.put("personajeIdpersonaje", id);
		
		RestClient.put("Usuarios", params, new JsonHttpResponseHandler(){

		});
		finish();
	}
}