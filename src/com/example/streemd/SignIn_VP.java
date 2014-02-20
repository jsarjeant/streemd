package com.example.streemd;

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

public class SignIn_VP extends Activity{
	
	/** Button used for signing in */
	protected Button m_vwSignInButton;
	
	/** Fills sign in fields with test data*/
	protected Button m_vwTestCredsButton;
	
	/** Input for entering username */
	protected EditText m_vwUsernameEditText;
	
	/** Input for entering password */
	protected EditText m_vwPasswordEditText;
	
	protected final static String BASE_URL = "https://streemd.herokuapp.com/api/sign_in";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.sign_in);
		
		m_vwSignInButton = (Button) findViewById(R.id.signInButton);
		m_vwTestCredsButton = (Button) findViewById(R.id.testSignInButton);
		m_vwUsernameEditText = (EditText) findViewById(R.id.usernameEditText);
		m_vwPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
		
		initSignInListener();
		super.onCreate(savedInstanceState);
	}
	
	/**
	 * Method is used to encapsulate the code that initializes and sets the
	 * Event Listeners which will respond to requests to Sign in
	 */
	protected void initSignInListener() {
		m_vwSignInButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				String username = m_vwUsernameEditText.getText().toString();
				String password = m_vwPasswordEditText.getText().toString();
				
				if(!username.equals("") && !username.equals(null) && !password.equals("") && !password.equals(null)) {
					Log.d(null, "username = " + username);
					Log.d(null, "password = " + password);
					if(checkCredentials(username, password)) {
						try {
							URL url =  new URL(BASE_URL + "/" + URLEncoder.encode(username, "UTF-8") + "/" + URLEncoder.encode(password, "UTF-8"));
							new AsyncTask<URL, Void, Boolean>() {
								@Override
								protected Boolean doInBackground(URL... urls) {
									boolean success = false;
									try {
										Scanner in;
										in = new Scanner(urls[0].openStream());
										if(in.hasNextLine()) {
											String response = in.nextLine();
											Log.d("RESPONSE: ", response);
											if(response.equals("true"))
												success = true;
										}
									} catch (Exception e) {
										Log.d("URL", e.toString());
									}
							        return success;
							     }

							     protected void onPostExecute(Boolean success) {
							    	 if (success) {
							    		 String toastText = "Login Successful";
							    		 Toast toast = Toast.makeText(SignIn_VP.this, toastText, Toast.LENGTH_SHORT);
								    	 toast.show();
								    	 Intent intent = new Intent(SignIn_VP.this, MainActivity.class);
								    	 startActivity(intent);
							    	 }
							    	 else {
							    		 String toastText = "Login Failed";
							    		 Toast toast = Toast.makeText(SignIn_VP.this, toastText, Toast.LENGTH_SHORT);
								    	 toast.show();
							    	 }
							     }

							}.execute(url);
						} catch (MalformedURLException e) {
							Log.e(null, e.toString());
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
					}
					else {
						Log.d(null, "Invalid user");
					}
				}
				
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(m_vwUsernameEditText.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(m_vwPasswordEditText.getWindowToken(), 0);
			}
		});
		m_vwTestCredsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				m_vwUsernameEditText.setText("exampleUser");
				m_vwPasswordEditText.setText("foobar");
			}
		});
	}
	
	public boolean checkCredentials(String username, String password) {
		return true;
	}

}
