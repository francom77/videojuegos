package com.example.virtualstreet;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsoluteLayout;
import android.widget.Button;

public class LimpiacityActivity extends Activity {
	private int zonaOrigen;
	private int puntaje = 0;
	private final int delayTime = 3000;
	private Handler myHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Puntaje: " + Integer.toString(puntaje));
		setContentView(R.layout.activity_limpiacity);
		createButton();
		zonaOrigen =  getIntent().getExtras().getInt("zonaOrigen");
		myHandler.postDelayed(closeControls, delayTime);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.limpiacity, menu);
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
	
	private void createButton(){
		Button button = (Button)findViewById(R.id.my_button);
		AbsoluteLayout.LayoutParams absParams = 
		    (AbsoluteLayout.LayoutParams)button.getLayoutParams();

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int width = displaymetrics.widthPixels;
		int height = displaymetrics.heightPixels;


		Random r = new Random();

		absParams.x =  r.nextInt(width-200);
		absParams.y =  r.nextInt(height-200);
		button.setLayoutParams(absParams);
	}
	
	public void limpiar(View view){
		puntaje++;
		createButton();
		setTitle("Puntaje: " + Integer.toString(puntaje));
	}
	
	private Runnable closeControls = new Runnable() {
	    public void run() {
	    	Intent i = new Intent(LimpiacityActivity.this, ResultActivity.class);
	    	i.putExtra("puntos", Integer.toString(puntaje));
	    	i.putExtra("zonaOrigen", zonaOrigen);
	    	startActivity(i);
            finish();
	    	
	    }
	};
}

//)
