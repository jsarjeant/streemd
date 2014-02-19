   package com.example.streemd;

   import com.google.android.youtube.player.YouTubeInitializationResult;
   import com.google.android.youtube.player.YouTubePlayer;
   import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
   import com.google.android.youtube.player.YouTubePlayer.Provider;
   import com.google.android.youtube.player.YouTubePlayerView;

   import android.content.Context;
   import android.util.Log;
   import android.view.LayoutInflater;
   import android.view.View;
   import android.widget.Button;
   import android.widget.LinearLayout;
   import android.widget.TextView;
   import android.widget.Toast;

   public class PostView extends LinearLayout implements OnInitializedListener {
   
      private Post m_Post;
   
      private TextView m_vwTitle;
      private TextView m_vwDescription;
   
      private Button m_vwPlayButton;
   
      protected String toastText = "Onclick function";
   
      public PostView(Context context, Post post) { 
         super(context);
         LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         inflater.inflate(R.layout.post_view, this, true);
      
         this.m_vwTitle = (TextView) findViewById(R.id.postTitle);
         this.m_vwDescription = (TextView) findViewById(R.id.postDescription);
         this.m_vwPlayButton = (Button) findViewById(R.id.postPlayButton);
      
         this.m_vwPlayButton.setOnClickListener(
               new OnClickListener() {
                  @Override
                  public void onClick(View arg0) {
                  /*UserFeed_VP.setPlayerVisibility(View.VISIBLE);
                  
                  YouTubePlayerView ytpv = (YouTubePlayerView) findViewById(R.id.youtube_view);
                  ytpv.initialize(DeveloperKey.DEVELOPER_KEY, PostView.this);*/
                  
                     YouTubePlayer player = UserFeed_VP.getYouTubePlayer();
                     player.loadVideo(m_Post.getYoutubeId());
                     Log.d("jsarjean", "On click function");
                  }
               
               });
      
         this.setPost(post);
      }
   
      public void setPost(Post post) {
         this.m_Post = post;
         this.m_vwTitle.setText(post.getTitle());
         this.m_vwDescription.setText(post.getDescription());
      
      }
   
      @Override
      public void onInitializationFailure(Provider arg0,
      YouTubeInitializationResult arg1) {
      
      
      }
   
      @Override
      public void onInitializationSuccess(Provider provider, YouTubePlayer player,
      boolean wasRestored) {
         if (!wasRestored) {
            player.loadVideo(this.m_Post.getYoutubeId());
         }
      
      }
   
   
   
   }
