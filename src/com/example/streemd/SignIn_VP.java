package com.example.streemd;

import com.example.streemd.R;

import edu.calpoly.android.lab3.Joke;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class SignIn_VP extends Activity{
	
	/** Button used for signing in */
	protected Button m_vwSignInButton;
	
	/** Input for entering username */
	protected EditText m_vwUsernameText;
	
	/** Input for entering password */
	protected EditText m_vwPasswordText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		m_vwSignInButton = (Button) findViewById(R.id.signInButton);
		m_vwUsernameText = (EditText) findViewById(R.id.usernameEditText);
		m_vwPasswordText = (EditText) findViewById(R.id.passwordEditText);
		
		initSignInListener();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
	}
	
	/**
	 * Method is used to encapsulate the code that initializes and sets the
	 * Event Listeners which will respond to requests to Sign in
	 */
	protected void initSignInListener() {
		m_vwSignInButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Resources r = getResources();
				String username = m_vwUsernameText.getText().toString();
				String password = m_vwPasswordText.getText().toString();
				
				/*if(!jokeStr.equals("") && !jokeStr.equals(null)) {
					m_vwJokeEditText.setText("");
					addJoke(new Joke(jokeStr, r.getString(R.string.author_name)));
				}*/
				
				/*InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(m_vwJokeEditText.getWindowToken(), 0);*/
			}
		});
	}

}
