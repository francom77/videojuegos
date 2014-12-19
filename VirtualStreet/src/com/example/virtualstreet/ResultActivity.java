package com.example.virtualstreet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ResultActivity extends Activity {

	private int zonaOrigen;
	private UiLifecycleHelper uiHelper;
	private String puntos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_result);

		Bundle bundle = getIntent().getExtras();
		puntos = bundle.getString("puntos");

		TextView puntostxtvw = (TextView) findViewById(R.id.puntos_title);
		puntostxtvw.setText("Tu puntaje es: " + puntos);
		zonaOrigen = getIntent().getExtras().getInt("zonaOrigen");
		registrarPuntaje(Integer.parseInt(puntos));
		Button publishButton = (Button) findViewById(R.id.publishButton);
		publishButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publishStory();

			}

		});

	}

	public void publishStory() {
		FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
				.setName("Virtual Street")
				.setDescription("He hecho "+puntos+" puntos. ¿Podras superarme?")
				.setLink("http://virtualsback-uniquegames.rhcloud.com/explorer/").build();
		uiHelper.trackPendingDialogCall(shareDialog.present());

	}

	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
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

	public void reintentar(View view) {
		Intent i = new Intent(ResultActivity.this, LimpiacityActivity.class);
		i.putExtra("zonaOrigen", zonaOrigen);

		startActivity(i);
		finish();
	}

	public void registrarPuntaje(int puntaje) {
		SharedPreferences sp = Prefs.getSharedPreferences(getApplication());
		RequestParams params = new RequestParams();
		params.put("usuarioIdusuario", sp.getInt("id", 100));
		params.put("zonaIdzona", zonaOrigen);
		params.put("puntaje", puntaje);
		RestClient.put("UsuarioHasZonas", params,
				new JsonHttpResponseHandler() {

				});
	}

}
