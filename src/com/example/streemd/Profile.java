package com.example.streemd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class Profile extends YouTubePlayerSupportFragment implements OnInitializedListener{
	
   protected ArrayList<Post> m_arrPostList;
	   
   protected PostListAdapter m_postAdapter;
   
   protected ListView m_vwPostLayout;
   protected View rootView;
   
   protected YouTubePlayerSupportFragment youTubePlayerSupportFragment;
   protected Fragment m_videoListFragment;
   protected static YouTubePlayer m_youTubePlayer;
   
   protected final static String BASE_URL = "https://streemd.herokuapp.com/api/";
	
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	   super.onCreateView(inflater, container, savedInstanceState);
	      
      this.m_arrPostList = new ArrayList<Post>();
      this.m_postAdapter = new PostListAdapter(getActivity().getApplicationContext(), this.m_arrPostList);
      
      this.initLayout();
      
      rootView = inflater.inflate(R.layout.profile, container, false);
      this.m_vwPostLayout = (ListView) rootView.findViewById(R.id.profileListView);
      this.m_vwPostLayout.setAdapter(m_postAdapter);
      
      getPosts(1);
      
      return rootView;
   }
   
   public void initLayout() {
      this.checkAndInitializePlayer();
   }
   
   public void checkAndInitializePlayer() {
      FragmentManager fm = getFragmentManager();
      this.youTubePlayerSupportFragment = new YouTubePlayerSupportFragment();
      FragmentTransaction ft = fm.beginTransaction();
      ft.replace(R.id.youtube_profile_fragment, this.youTubePlayerSupportFragment);
      ft.commit();
      this.youTubePlayerSupportFragment.initialize(DeveloperKey.DEVELOPER_KEY, Profile.this);
   }

   @Override
   public void onInitializationFailure(Provider arg0,
      YouTubeInitializationResult arg1) {
      String toastText = "Video Player initialization failed....";
      Toast toast = Toast.makeText(getActivity(), toastText, Toast.LENGTH_LONG);
      toast.show();
      
   }

   @Override
   public void onInitializationSuccess(Provider provider, YouTubePlayer player,
      boolean wasRestored) {
      if (!wasRestored) {
         m_youTubePlayer = player;
         FragmentManager fm = getFragmentManager();
         FragmentTransaction ft = fm.beginTransaction();
         ft.hide(this.youTubePlayerSupportFragment);
         ft.commit();
         m_youTubePlayer.setPlayerStateChangeListener(new PlayerStateChangeListener() {

            @Override
            public void onAdStarted() {
               // TODO Auto-generated method stub
               
            }

            @Override
            public void onError(ErrorReason arg0) {
               // TODO Auto-generated method stub
            }

            @Override
            public void onLoaded(String arg0) {
               // TODO Auto-generated method stub
               FragmentManager fm = getActivity().getSupportFragmentManager();
               FragmentTransaction ft = fm.beginTransaction();
               ft.show(youTubePlayerSupportFragment);
               ft.commit();
            }

            @Override
            public void onLoading() {
               
            }

            @Override
            public void onVideoEnded() {
               // TODO Auto-generated method stub
            }

            @Override
            public void onVideoStarted() {
               // TODO Auto-generated method stub
            }
            
         });

      }
   }
   public static YouTubePlayer getYouTubePlayer() {
      return m_youTubePlayer;
   }
   
   public void getPosts(int pageNumber) {
	   try {
			URL url =  new URL(BASE_URL + "/posts/get/" + URLEncoder.encode("exampleUser", "UTF-8") + "/All/" + pageNumber);
			new AsyncTask<URL, Void, Boolean>() {
				@Override
				protected Boolean doInBackground(URL... urls) {
					Scanner in = null;
					String response = "";
					List<Post> posts = null;
					
					try {
						in = new Scanner(urls[0].openStream());
						while(in.hasNext()) {
							response += " " + in.next();
						}
						Log.d("RESPONSE: ", response);
						
						Gson gson = new Gson();
						posts = gson.fromJson(response, new TypeToken<List<Post>>(){}.getType());
						m_arrPostList.addAll(posts);
						
					} catch (IOException e) {
						Log.d("GetPostsError!", e.toString());
					} finally {
						if(in != null) {
							in.close();
						}
						getActivity().runOnUiThread(new Runnable() {
						     @Override
						     public void run() {
						    	 m_postAdapter.notifyDataSetChanged();
						     }
						});
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
