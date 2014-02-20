package com.example.streemd;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class UserFeed_VP extends  YouTubePlayerSupportFragment implements OnInitializedListener {
   
   protected ArrayList<Post> m_arrPostList;
   
   protected PostListAdapter m_postAdapter;
   
   protected ListView m_vwPostLayout;
   protected YouTubePlayerSupportFragment youTubePlayerSupportFragment;
   protected static YouTubePlayer m_youTubePlayer;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      super.onCreateView(inflater, container, savedInstanceState);
      
      this.m_arrPostList = new ArrayList<Post>();
      
      
      this.m_postAdapter = new PostListAdapter(getActivity().getApplicationContext(), this.m_arrPostList);
      
      View rootView = inflater.inflate(R.layout.user_feed, container, false);
      
      this.m_vwPostLayout = (ListView) rootView.findViewById(R.id.postListView);
      this.m_vwPostLayout.setAdapter(m_postAdapter);
      
      this.youTubePlayerSupportFragment = (YouTubePlayerSupportFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
      this.youTubePlayerSupportFragment.initialize(DeveloperKey.DEVELOPER_KEY, UserFeed_VP.this);
      
      for (String s : this.getResources().getStringArray(R.array.post_array)) {
         addPost(new Post(s));
      }
      
      return rootView;
   }

   /*@Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }*/
   
   protected void initLayout() {
      
   }
   
   public void addPost(Post post) {
      this.m_arrPostList.add(post);
      this.m_postAdapter.notifyDataSetChanged();
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
         this.m_youTubePlayer = player;
         String toastText = "Video Player initialization suceeded....";
         Toast toast = Toast.makeText(getActivity(), toastText, Toast.LENGTH_LONG);
         toast.show();
      }
      
   }

   public static YouTubePlayer getYouTubePlayer() {
      return m_youTubePlayer;
   }

}
