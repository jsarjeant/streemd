package com.example.streemd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity{
	
	/** Button used for signing in */
	protected Button m_vwSignUpButton;
	
	/** Input for entering name */
	protected EditText m_vwNameEditText;
	
	/** Input for entering email */
	protected EditText m_vwEmailEditText;
	
	/** Input for entering username */
	protected EditText m_vwUsernameEditText;
	
	/** Input for entering password */
	protected EditText m_vwPasswordEditText;
	
	/** Input for entering password confirmation*/
	protected EditText m_vwPasswordConfirmEditText;
	
	protected final static String BASE_URL = "https://streemd.herokuapp.com/api/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.sign_up);
		
		m_vwNameEditText = (EditText) findViewById(R.id.signUpNameEditText);
		m_vwEmailEditText = (EditText) findViewById(R.id.signUpEmailEditText);
		m_vwUsernameEditText = (EditText) findViewById(R.id.signUpUsernameEditText);
		m_vwPasswordEditText = (EditText) findViewById(R.id.signUpPasswordEditText);
		m_vwPasswordConfirmEditText = (EditText) findViewById(R.id.signUpPasswordConfirmEditText);
		m_vwSignUpButton = (Button) findViewById(R.id.signUpConfirmButton);
		
		initSignUpListener();
		
		super.onCreate(savedInstanceState);
	}
	
	/**
	 * Method is used to encapsulate the code that initializes and sets the
	 * Event Listeners which will respond to requests to Sign in
	 */
	protected void initSignUpListener() {
		m_vwSignUpButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				final String name = m_vwNameEditText.getText().toString();
				final String email = m_vwEmailEditText.getText().toString();
				final String username = m_vwUsernameEditText.getText().toString();
				final String password = m_vwPasswordEditText.getText().toString();
				final String passConfirm = m_vwPasswordConfirmEditText.getText().toString();
				
				if(checkFields(name, email, username, password, passConfirm)) {
					try {
						URL url =  new URL(BASE_URL + "users/add/" + URLEncoder.encode(username, "UTF-8") 
								+ "/" + URLEncoder.encode(password, "UTF-8")
								+ "/" + URLEncoder.encode(name, "UTF-8")
								+ "/" + URLEncoder.encode(email, "UTF-8"));
						new AsyncTask<URL, Void, String>() {
							@Override
							protected String doInBackground(URL... urls) {
								String result = null;
								Scanner in = null;
								try {
									in = new Scanner(urls[0].openStream());
									if(in.hasNextLine()) {
										String response = in.nextLine();
										if(response.equals("success"))
											result = "success";
										else {
											result = "Problem with " + response;
										}
									}
								} catch (IOException e) {
									e.printStackTrace();
								} finally {
									if(in != null) {
										in.close();
									}
								}
								
						        return result;
						     }

						     protected void onPostExecute(String result) {
						    	 if (result.equals("success")) {
						    		 StreemdApplication appState = ((StreemdApplication) SignUpActivity.this.getApplication());
					    			 appState.session.setUsername(username);
					    			 appState.session.setPassword(password);
					    			 
							    	 Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
							    	 startActivity(intent);
						    	 }
						    	 else {
						    		 Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
						    		 toast.show();
						    	 }
						    		 
						     }

						}.execute(url);
					} catch (MalformedURLException e) {
						Log.e(null, e.toString());
					} catch (UnsupportedEncodingException e1) {
						Log.e(null, e1.toString());
					}
				}
				
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(m_vwUsernameEditText.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(m_vwPasswordEditText.getWindowToken(), 0);
			}
		});
	}
	public boolean checkFields(String name, String email, String username, String password, String passConfirm) {
		
		if(name.equals("") || email.equals("") || username.equals("") || password.equals("") || passConfirm.equals("")) {
			Toast toast = Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
		
		if(username.length() < 6) {
			Toast toast = Toast.makeText(this, "Username must be > 6 characters", Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
		
		if(!password.equals(passConfirm)) {
			Toast toast = Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
		
		return true;
	}
}
