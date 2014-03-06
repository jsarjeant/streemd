package com.example.streemd;

import java.util.List;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.api.services.youtube.model.SearchResult;

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
	protected YouTubePlayer youTubePlayer;
	protected List<SearchResult> searchResultList;
	
	public List<SearchResult> getSearchResultList() {
      return searchResultList;
   }
   public void setSearchResultList(List<SearchResult> searchResultList) {
      this.searchResultList = searchResultList;
   }
   public YouTubePlayer getYouTubePlayer() {
		return youTubePlayer;
	}
	public void setYouTubePlayer(YouTubePlayer youTubePlayer) {
		this.youTubePlayer = youTubePlayer;
	}
}