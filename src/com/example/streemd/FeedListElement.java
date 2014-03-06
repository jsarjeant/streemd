   package com.example.streemd;

   import com.google.android.youtube.player.YouTubePlayer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

   public class FeedListElement extends LinearLayout implements PostView {
   
      private Post m_Post;
   
      private TextView m_vwTitle;
      private TextView m_vwDescription;
   
      private Button m_vwPlayButton;
   
      protected String toastText = "Onclick function";
   
      public FeedListElement(Context context, Post post) { 
         super(context);
         LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         inflater.inflate(R.layout.post_view, this, true);
      
         this.m_vwTitle = (TextView) findViewById(R.id.postTitle);
         this.m_vwDescription = (TextView) findViewById(R.id.postDescription);
         this.m_vwPlayButton = (Button) findViewById(R.id.postPlayButton);
         this.m_Post = post;
      
         this.m_vwPlayButton.setOnClickListener(
               new OnClickListener() {
                  @Override
                  public void onClick(View arg0) {
                	  StreemdApplication appState = (StreemdApplication) getContext();
                	  
                      YouTubePlayer player = appState.getYouTubePlayer();
                      player.loadVideo(m_Post.getYoutubeId());
                  }
               
               });
      
         this.setPost(post);
      }
   
      public void setPost(Post post) {
         this.m_Post = post;
         this.m_vwTitle.setText(post.getTitle());
         this.m_vwDescription.setText(post.getDescription());
      
      }
   }
