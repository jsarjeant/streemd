package com.example.streemd;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class UserFeed_VP extends  YouTubePlayerSupportFragment implements OnInitializedListener {
   
   protected ArrayList<Post> m_arrPostList;
   
   protected PostListAdapter m_postAdapter;
   
   protected ListView m_vwPostLayout;
   protected YouTubePlayerSupportFragment youTubePlayerSupportFragment;
   protected Fragment m_videoListFragment;
   protected static YouTubePlayer m_youTubePlayer;
   protected View rootView;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      super.onCreateView(inflater, container, savedInstanceState);
      
      this.m_arrPostList = new ArrayList<Post>();
      this.m_postAdapter = new PostListAdapter(getActivity().getApplicationContext(), this.m_arrPostList);
      
      
      this.initLayout();
      
      rootView = inflater.inflate(R.layout.user_feed, container, false);
      this.m_vwPostLayout = (ListView) rootView.findViewById(R.id.postListView);
      this.m_vwPostLayout.setAdapter(m_postAdapter);
      
    //TODO Pull in posts from DB
      /*addPost(new Post("d2ZNaLQD60Y", "Test Post"));*/
      addPost(new Post("wKJ9KzGQq0w", "Youtube Test Video", "This is a video to be used in YouTube Player API demos."));
      addPost(new Post("d8i-H6MVl18","Introduction to android - android tutorial for bigginers to advanced", "Hi i am posting all the videos that helped me in learning android. now i am having 3 years of mobile experience and any one want to learn android subscribe for my channel and ask any doubts in android.i will explain you.Please subscribe for channel."));
      addPost(new Post("d2ZNaLQD60Y", "Game of Thrones Trailer #2 - Vengeance (HBO)", "Check out the new GOT trailer!! Only 6 more weeks I can't wait!"));
      addPost(new Post("wKJ9KzGQq0w", "Youtube Test Video", "This is a video to be used in YouTube Player API demos."));
      addPost(new Post("d8i-H6MVl18","Introduction to android - android tutorial for bigginers to advanced", "Hi i am posting all the videos that helped me in learning android. now i am having 3 years of mobile experience and any one want to learn android subscribe for my channel and ask any doubts in android.i will explain you.Please subscribe for channel."));
      addPost(new Post("d2ZNaLQD60Y", "Game of Thrones Trailer #2 - Vengeance (HBO)", "Check out the new GOT trailer!! Only 6 more weeks I can't wait!"));
      addPost(new Post("wKJ9KzGQq0w", "Youtube Test Video", "This is a video to be used in YouTube Player API demos."));
      addPost(new Post("d8i-H6MVl18","Introduction to android - android tutorial for bigginers to advanced", "Hi i am posting all the videos that helped me in learning android. now i am having 3 years of mobile experience and any one want to learn android subscribe for my channel and ask any doubts in android.i will explain you.Please subscribe for channel."));
      addPost(new Post("d2ZNaLQD60Y", "Game of Thrones Trailer #2 - Vengeance (HBO)", "Check out the new GOT trailer!! Only 6 more weeks I can't wait!"));
      addPost(new Post("wKJ9KzGQq0w", "Youtube Test Video", "This is a video to be used in YouTube Player API demos."));
      addPost(new Post("d8i-H6MVl18","Introduction to android - android tutorial for bigginers to advanced", "Hi i am posting all the videos that helped me in learning android. now i am having 3 years of mobile experience and any one want to learn android subscribe for my channel and ask any doubts in android.i will explain you.Please subscribe for channel."));
      addPost(new Post("d2ZNaLQD60Y", "Game of Thrones Trailer #2 - Vengeance (HBO)", "Check out the new GOT trailer!! Only 6 more weeks I can't wait!"));
          
      
      return rootView;
   }
   
   protected void initLayout() {
      this.checkAndInitializePlayer();
      
   }
   
   public void checkAndInitializePlayer() {
      FragmentManager fm = getFragmentManager();
      this.youTubePlayerSupportFragment = new YouTubePlayerSupportFragment();
      FragmentTransaction ft = fm.beginTransaction();
      ft.replace(R.id.youtube_fragment, this.youTubePlayerSupportFragment);
      ft.commit();
      this.youTubePlayerSupportFragment.initialize(DeveloperKey.DEVELOPER_KEY, UserFeed_VP.this);
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
         String toastText = "Video Player initialization success!";
         Toast toast = Toast.makeText(getActivity(), toastText, Toast.LENGTH_LONG);
         toast.show();
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
}
