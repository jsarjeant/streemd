package com.example.streemd;

import com.google.android.youtube.player.YouTubePlayer;

public class Post {
   private String youtubeId;
   private String title;
   private String description;
   
   public Post(String youtubeId) {
      this.youtubeId = youtubeId;

      //TODO Get the title and description through the data API and set accordingly
      this.title = "Test Title";
      this.description = "Test Description";
   }
   
   public String getYoutubeId() {
      return this.youtubeId;
   }
   
   public void setYoutubeId(String youtubeId) {
      this.youtubeId = youtubeId;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }
}
