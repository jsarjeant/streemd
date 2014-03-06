package com.example.streemd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


import java.io.IOException;
import java.io.File;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SearchVideos extends Fragment{
   protected EditText m_vwSearchField;
   protected Button m_vwSearchButton;
   protected List<SearchResult> searchResultList;
   protected OnVideoSearch mCallBack;
   
   public interface OnVideoSearch {
      public void goToSearchResults();
   }
   public void onCreate() {
   }
   @Override
   public void onAttach(Activity activity) {
       super.onAttach(activity);
       
       // This makes sure that the container activity has implemented
       // the callback interface. If not, it throws an exception
       try {
           mCallBack = (OnVideoSearch) activity;
       } catch (ClassCastException e) {
           throw new ClassCastException(activity.toString()
                   + " must implement OnHeadlineSelectedListener");
       }
   }
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
      super.onCreateView(inflater, container, savedInstanceState);
      
      
      View view = inflater.inflate(R.layout.search_videos, container, false); 
      this.m_vwSearchField = (EditText) view.findViewById(R.id.search_videos_field);
      this.m_vwSearchButton = (Button) view.findViewById(R.id.search_videos_button);
      this.m_vwSearchButton.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
            Log.d("Onclick", "True");
            new AsyncTask<Void, Void, Boolean>() {

               @Override
               protected Boolean doInBackground(Void... params) {
                  try {
                     YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), 
                                                           new HttpRequestInitializer() {
                                                              public void initialize(HttpRequest request) throws IOException {
                                                              }
                                                          }).setApplicationName("Streemd").build();
                     
                  // Define the API request for retrieving search results.
                     YouTube.Search.List search = youtube.search().list("id,snippet");
                     // Set your developer key from the Google Developers Console for
                     // non-authenticated requests. See:
                     // https://cloud.google.com/console
                     String apiKey = DeveloperKey.DEVELOPER_KEY;
                     search.setKey(apiKey);
                     String queryString = m_vwSearchField.getText().toString();
                     search.setQ(queryString);
                     search.setFields("items(id/videoId,snippet/title, snippet/description)");
                     search.setMaxResults((long) 50);
                     search.setType("video");
                     
                     SearchListResponse searchResponse = search.execute();
                     searchResultList = searchResponse.getItems();
                     if (searchResultList != null) {
                        int i = 0;
                        for (SearchResult result : searchResultList) {
                           Log.d("Background", i++ + ") " + result.getId().getVideoId() + ": " + result.getSnippet().getTitle());
                        }
                     }
                     ((StreemdApplication) getActivity().getApplication()).setSearchResultList(searchResultList);
                     
                  } catch (IOException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
                  return null;
               }
               @Override
               protected void onPostExecute(Boolean b) { 
                  mCallBack.goToSearchResults();
               }
            }.execute();
            
            
            
         }
      });
      return view;
   }
}
