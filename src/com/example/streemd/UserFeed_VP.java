package com.example.streemd;

import com.examples.youtubeapidemo.YouTubeFailureRecoveryActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UserFeed_VP extends Activity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.user_feed);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }

   /*@Override
   public void onInitializationSuccess(Provider arg0, YouTubePlayer arg1,
      boolean arg2) {
      // TODO Auto-generated method stub
      
   }

   @Override
   protected Provider getYouTubePlayerProvider() {
      // TODO Auto-generated method stub
      return null;
   }*/

}
