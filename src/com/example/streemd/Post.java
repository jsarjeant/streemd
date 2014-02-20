   package com.example.streemd;

   import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;

   public class Post {
      private String youtubeId;
      private String title;
      private String description;
   
      public Post(String youtubeId, String description) {
         this.youtubeId = youtubeId;
      
         try {
            URL url = new URL("https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + youtubeId + "&fields=items%2Fsnippet%2Ftitle&key=" + DeveloperKey.DEVELOPER_KEY);
            
            new AsyncTask<URL, String, String>() {
               @Override
               protected String doInBackground(URL... urls) {
                  String temp = "";
                  try {
                     Scanner in;
                     in = new Scanner(urls[0].openStream());
                     for (int i = 0; i < 4; i++) {
                        in.nextLine();
                     }
                     in.next();
                     String temp2 = in.nextLine();
                     title = temp2.substring(2,temp2.length() - 1);
                     Log.d("TestAdd", title);
                  } 
                  catch (IOException e) {
                     e.printStackTrace();
                  }
                  return temp;
               }
               protected void onPostExecute(String title) {
                  Post.this.setTitle(title);
               }
            }.execute(url);
         } 
         catch (MalformedURLException e) {
            e.printStackTrace();
         }
         this.description = description;
      }
      
      public Post(String youTubeId, String title, String description) {
         this.youtubeId = youTubeId;
         this.title = title;
         this.description = description;
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
