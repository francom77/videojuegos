package com.example.virtualstreet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ResultActivity extends Activity {
	
	private int zonaOrigen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_result);

		Bundle bundle = getIntent().getExtras();
		String puntos = bundle.getString("puntos");

		TextView puntostxtvw = (TextView) findViewById(R.id.puntos_title);
		puntostxtvw.setText("Tu puntaje es: " + puntos);
		zonaOrigen =  getIntent().getExtras().getInt("zonaOrigen");
		registrarPuntaje(Integer.parseInt(puntos));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
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

	public void salir(View view) {
		finish();
	}

	public void reintentar(View view){
		Intent i = new Intent(ResultActivity.this, LimpiacityActivity.class);
		i.putExtra("zonaOrigen", zonaOrigen);

		startActivity(i);
		finish();
	}

	public void registrarPuntaje(int puntaje){
		SharedPreferences sp = Prefs.getSharedPreferences(getApplication());
		RequestParams params = new RequestParams();
		params.put("usuarioIdusuario", sp.getInt("id", 100));
		params.put("zonaIdzona", zonaOrigen);
		params.put("puntaje", puntaje);
		RestClient.put("UsuarioHasZonas", params, new JsonHttpResponseHandler(){


		});
	}

		
}



