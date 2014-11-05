package com.example.virtualstreet;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.activity_main);
		//obteniendo fuente
		Typeface font = Typeface.createFromAsset(getAssets(), "gloriahallelujah.ttf");
		//obtenendo elementos
		TextView title= (TextView)findViewById(R.id.text_juv);
		Button start = (Button)findViewById(R.id.button_start);
		Button map = (Button)findViewById(R.id.button_map);
		Button settings = (Button)findViewById(R.id.button_settings);
		//seteando la fuente
		title.setTypeface(font);
		start.setTypeface(font);
		map.setTypeface(font);
		settings.setTypeface(font);
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	public void configurar(View view) {
        Intent i = new Intent(this, SelectCharacter.class );
        startActivity(i);
  } 
	public void showMap(View view){
		Intent i = new Intent(this, MapActivity.class);
		startActivity(i);
	}
}