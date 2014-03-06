package com.example.streemd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SearchUsers extends Fragment{
	
	protected EditText m_vwSearchField;
	protected Button m_vwSearchButton;
	protected Button m_vwTestUserButton;
	
	protected final static String BASE_URL = "https://streemd.herokuapp.com/api/users/get_by_username/";
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
      
	  super.onCreateView(inflater, container, savedInstanceState);
      View view = inflater.inflate(R.layout.search_users, container, false);
      this.m_vwSearchField = (EditText) view.findViewById(R.id.search_users_field);
      this.m_vwSearchButton = (Button) view.findViewById(R.id.search_users_button);
      this.m_vwSearchButton.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View arg0) {
        	searchUsers(m_vwSearchField.getText().toString());
         }
         
      });
      this.m_vwTestUserButton = (Button) view.findViewById(R.id.test_user_button);
      m_vwTestUserButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				m_vwSearchField.setText("exampleUser2");
			}
		});
      return view;
   }
   
   public void initLayout() {
	   
   }
   
   public void searchUsers(String username) {
	   try {
			URL url =  new URL(BASE_URL + URLEncoder.encode(username, "UTF-8"));
			new AsyncTask<URL, Void, Boolean>() {
				@Override
				protected Boolean doInBackground(URL... urls) {
					Scanner in = null;
					String response = "";
					User user = null;
					
					try {
						in = new Scanner(urls[0].openStream());
						while(in.hasNext()) {
							response += " " + in.next();
						}
							
						Gson gson = new Gson();
						user = gson.fromJson(response, new TypeToken<User>(){}.getType());
						
						FragmentManager fm = getActivity().getSupportFragmentManager();
			            Fragment f = new UserProfile();
			            
			            Bundle args = new Bundle();
			            args.putString("username", user.username);
			            
			            f.setArguments(args);
			            fm.beginTransaction().replace(R.id.content_frame, f).commit();
						
					} catch (IOException e) {
						Log.d("GetUserError!", e.toString());
					} finally {
						if(in != null) {
							in.close();
						}
					}
			        return false;
			     }
			}.execute(url);
		} catch (MalformedURLException e) {
			Log.e(null, e.toString());
		} catch (UnsupportedEncodingException e1) {
			Log.e(null, e1.toString());
		} 
   }
}
