package com.example.streemd;

import android.app.Application;

public class StreemdApplication extends Application 
{     
    public class SessionInfo {
    	String username;
    	String password;
    	
    	public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
    }
	
	public SessionInfo session = new SessionInfo();
}