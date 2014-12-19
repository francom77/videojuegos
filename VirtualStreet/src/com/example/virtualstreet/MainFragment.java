package com.example.virtualstreet;

import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainFragment extends Fragment{
	
	private static final String TAG = "MainFragment";
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
		
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	
	private UiLifecycleHelper uiHelper;
	
	@Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed()) ) {
			onSessionStateChange(session, session.getState(), null);
		}
		uiHelper.onResume();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		uiHelper.onPause();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		uiHelper.onDestroy();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.activity_main, container, false);
		LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
		authButton.setFragment(this);
		authButton.setReadPermissions(Arrays.asList("public_profile","user_friends"));
		return view;
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception){
		if (state.isOpened()) {
			Log.i(TAG, "Logged in");
			Request.newMeRequest(Session.getActiveSession(), new Request.GraphUserCallback() {
				
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null) {
						Log.i("id", user.getId());
						Log.i("nombre", user.getName());
						Log.i("cumple", user.getBirthday());
						//CallRestApi(user.getId(),user.getName(),user.getBirthday());
					}									
				}
			}).executeAsync();
		} else {
			Log.i(TAG, "Logged out");
		}
	}
	
	private void CallRestApi(String Id,String name,String fecha){
		RequestParams params = new RequestParams();
		params.put("nombre", name );
		params.put("idFb", Id);
		params.put("fechaNac", fecha);
		params.put("personajeIdpersonaje", "1");
		RestClient.post("Usuarios", params, new JsonHttpResponseHandler(){
			
			
			
			//idusuario
			//nombre
			//idFb
			//fechaNac
			//personajeIdpersonaje
		});
	}
	
}
