package com.example.streemd;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class SignIn_VP extends Activity{
	
	/** Button used for signing in */
	protected Button m_vwSignInButton;
	
	/** Fills sign in fields with test data*/
	protected Button m_vwTestCredsButton;
	
	/** Input for entering username */
	protected EditText m_vwUsernameEditText;
	
	/** Input for entering password */
	protected EditText m_vwPasswordEditText;
	
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
						Log.d(null, "Valid user");
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
		return false;
	}

}
