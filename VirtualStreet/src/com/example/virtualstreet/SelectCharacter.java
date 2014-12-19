package com.example.virtualstreet;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class SelectCharacter extends Activity {

    private ViewGroup layout;
    private ScrollView scrollView;
    private JSONArray characters;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_character);		
		layout = (ViewGroup) findViewById(R.id.character_container);    
        scrollView = (ScrollView) findViewById(R.id.character_scroll); 
        this.getPersonajes();


	}

	public void juv(View view) {
		
		Intent i = new Intent(this, CharacterActivity.class);
		i.putExtra("character", view.getContentDescription());
		startActivity(i);
	}

	public void add(String name, String idcharacter) {
		LayoutInflater inflater = LayoutInflater.from(this);
		int id = R.layout.character_fragment;
		LinearLayout linearLayout = (LinearLayout) inflater.inflate(id, null, false);
		TextView textView = (TextView) linearLayout.findViewById(R.id.text_name);
		textView.setText(name);
		
		ImageButton imagen = (ImageButton) linearLayout.findViewById(R.id.img_btn);
		
		imagen.setImageResource(Link.getImagen(name));
		
		imagen.setContentDescription(idcharacter);
		
		//layout params
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		linearLayout.setLayoutParams(params);
		/////// 
		layout.addView(linearLayout);
		//scroll to last element
		//http://stackoverflow.com/questions/6438061/can-i-scroll-a-scrollview-programmatically-in-android
		scrollView.post(new Runnable() { 
			public void run() { 
				scrollView.fullScroll(ScrollView.FOCUS_DOWN);
			} 
		});
		///////
	}
	
	private void getPersonajes() {

		RestClient.get("Personajes", null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray personajes) {
				
				characters = personajes;
				System.out.println(characters.length());
								
				for (int i = 0; i < characters.length(); i++) {
					try {
						JSONObject JSONchar = characters.getJSONObject(i);
						add(JSONchar.getString("nombre"), Integer.toString(JSONchar.getInt("idpersonaje")));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		
		
		
	}

}
