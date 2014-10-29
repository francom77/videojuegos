package com.example.virtualstreet;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class SelectCharacter extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_character);
		//obteniendo fuente
		Typeface font = Typeface.createFromAsset(getAssets(), "gloriahallelujah.ttf");
		//obtenendo elementos
		TextView juv= (TextView)findViewById(R.id.text_juv);
		TextView dult = (TextView)findViewById(R.id.text_dult);
		TextView bila = (TextView)findViewById(R.id.text_bila);
		//seteando la fuente
		juv.setTypeface(font);
		dult.setTypeface(font);
		bila.setTypeface(font);						
		
	}

}
