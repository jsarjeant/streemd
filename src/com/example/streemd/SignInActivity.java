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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends Activity{
	
	/** Button used for signing in */
	protected Button m_vwSignInButton;
	
	/** Button used for signing in */
	protected Button m_vwSignUpButton;
	
	/** Input for entering username */
	protected EditText m_vwUsernameEditText;
	
	/** Input for entering password */
	protected EditText m_vwPasswordEditText;
	
	protected final static String BASE_URL = "https://streemd.herokuapp.com/api/sign_in";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.sign_in);
		
		m_vwSignInButton = (Button) findViewById(R.id.signInButton);
		m_vwUsernameEditText = (EditText) findViewById(R.id.usernameEditText);
		m_vwPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
		
		m_vwSignUpButton = (Button) findViewById(R.id.signUpButton);
		m_vwSignUpButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
		    	startActivity(intent);
			}
		});
		
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
				final String username = m_vwUsernameEditText.getText().toString();
				final String password = m_vwPasswordEditText.getText().toString();
				
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
									Scanner in = null;
									try {
										in = new Scanner(urls[0].openStream());
										if(in.hasNextLine()) {
											String response = in.nextLine();
											if(response.equals("true"))
												success = true;
										}
									} catch (IOException e) {
										Log.d("CredsCheckError!", e.toString());
									} finally {
										if(in != null) {
											in.close();
										}
									}
									
							        return success;
							     }

							     protected void onPostExecute(Boolean success) {
							    	 if (success) {
							    		 StreemdApplication appState = ((StreemdApplication) SignInActivity.this.getApplication());
						    			 appState.session.setUsername(username);
						    			 appState.session.setPassword(password);
						    			 
								    	 Intent intent = new Intent(SignInActivity.this, MainActivity.class);
								    	 startActivity(intent);
							    	 }
							    	 else {
							    		 String toastText = "Login Failed";
							    		 Toast toast = Toast.makeText(SignInActivity.this, toastText, Toast.LENGTH_SHORT);
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
					else {
						Log.d(null, "Invalid user");
					}
				}
				
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(m_vwUsernameEditText.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(m_vwPasswordEditText.getWindowToken(), 0);
			}
		});
	}
	
	public boolean checkCredentials(String username, String password) {
		return true;
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        return false;
    }

}
