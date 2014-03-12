package com.example.streemd;

import java.util.ArrayList;
import java.util.List;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.api.services.youtube.model.SearchResult;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class VideoList extends YouTubePlayerSupportFragment implements OnInitializedListener {
protected ArrayList<Post> m_arrPostList;
   
   protected PostListAdapter m_postAdapter;
   
   protected ListView m_vwPostLayout;
   protected YouTubePlayerSupportFragment youTubePlayerSupportFragment;
   protected Fragment m_videoListFragment;
   protected static YouTubePlayer m_youTubePlayer;
   
   protected final static String BASE_URL = "https://streemd.herokuapp.com/api/posts";
   
   protected CreatePost mCallback;
   
   public interface CreatePost {
      public void onVideoSelected(Post post);
   }
   
   @Override
   public void onAttach(Activity activity) {
       super.onAttach(activity);
       
       // This makes sure that the container activity has implemented
       // the callback interface. If not, it throws an exception
       try {
           mCallback = (CreatePost) activity;
       } catch (ClassCastException e) {
           throw new ClassCastException(activity.toString()
                   + " must implement OnHeadlineSelectedListener");
       }
   }
   
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      super.onCreateView(inflater, container, savedInstanceState);
      
      this.m_arrPostList = new ArrayList<Post>();
      this.m_postAdapter = new PostListAdapter(getActivity().getApplicationContext(), this.m_arrPostList, false);
      
      this.initLayout();
      
      View rootView = inflater.inflate(R.layout.search_list, container, false);
      this.m_vwPostLayout = (ListView) rootView.findViewById(R.id.searchListView);
      
      this.m_vwPostLayout.setOnItemClickListener(new OnItemClickListener() {

         @Override
         public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
            long arg3) {
            Log.d("jsarjean", "Item clicked");
            mCallback.onVideoSelected((Post) arg0.getItemAtPosition(arg2));
            
         }
         
      });
      this.m_vwPostLayout.setAdapter(m_postAdapter);
      
      
      populateList();
      
      return rootView;
   }
   
   protected void initLayout() {
      this.checkAndInitializePlayer();
      
   }
   
   public void checkAndInitializePlayer() {
      FragmentManager fm = getFragmentManager();
      this.youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
      FragmentTransaction ft = fm.beginTransaction();
      ft.replace(R.id.search_results_youtube_container, this.youTubePlayerSupportFragment);
      ft.commit();
      this.youTubePlayerSupportFragment.initialize(DeveloperKey.DEVELOPER_KEY, VideoList.this);
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
         
         StreemdApplication appState = ((StreemdApplication) getActivity().getApplication());
         appState.setYouTubePlayer(this.m_youTubePlayer);
      }
   }
   
   public void populateList() {
      List<SearchResult> resultsList = ((StreemdApplication) getActivity().getApplication()).getSearchResultList();
      for (SearchResult result : resultsList) {
         addPost(new Post(result.getId().getVideoId(), result.getSnippet().getTitle(), result.getSnippet().getDescription(), "temp"));
      }
   }
   
   @Override
   public void onDestroyView() {
      this.m_youTubePlayer.release();
      super.onDestroyView();
   }
}
