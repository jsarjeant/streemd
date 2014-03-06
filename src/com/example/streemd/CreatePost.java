package com.example.streemd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import android.widget.Spinner;
import android.widget.Toast;

public class CreatePost extends Fragment{
   protected Button m_vwCancel, m_vwCreate;
   protected EditText m_vwTitle, m_vwDescription;
   protected Spinner m_vwCategory;
   protected final static String BASE_URL = "https://streemd.herokuapp.com/api/posts";
   protected Bundle args;
   
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
      super.onCreateView(inflater, container, savedInstanceState);
      args = getArguments();
      View view =  inflater.inflate(R.layout.create_post, container, false);
      
      this.m_vwTitle = (EditText) view.findViewById(R.id.title_input);
      this.m_vwTitle.setText(args.getString("title"));
      this.m_vwDescription = (EditText) view.findViewById(R.id.description);
      this.m_vwDescription.setText(args.getString("description"));
      this.m_vwCategory = (Spinner) view.findViewById(R.id.category_select);
      
      this.m_vwCancel = (Button) view.findViewById(R.id.cancel_post);
      this.m_vwCancel.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment f = new SearchVideos();
            fm.beginTransaction().replace(R.id.content_frame, f).commit();
         }
      });
      
      this.m_vwCreate = (Button) view.findViewById(R.id.create_post);
      this.m_vwCreate.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
            
            createPost();
         }
      });
      return view;
  }
   
   public void createPost() {
      try {
         StreemdApplication appState = ((StreemdApplication) this.getActivity().getApplication());
         String username = appState.session.getUsername();
         
         URL url =  new URL(BASE_URL + "/create/" + URLEncoder.encode(username, "UTF-8") + "/" + URLEncoder.encode(args.getString("id"), "UTF-8") + "/"
                              + URLEncoder.encode(this.m_vwTitle.getText().toString(), "UTF-8") + "/" + URLEncoder.encode(this.m_vwDescription.getText().toString(), "UTF-8") + "/"
                              + URLEncoder.encode(this.m_vwCategory.getSelectedItem().toString(), "UTF-8"));
         new AsyncTask<URL, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(URL... urls) {
               Scanner in = null;
               String response = "";
               boolean success = false;
               
               try {
                  in = new Scanner(urls[0].openStream());
                  if (in.hasNext() && in.nextLine().equals("success")) {
                     success = true;
                     Log.d("jsarjean", "success");
                     Toast.makeText(getActivity(), "Create Successful", Toast.LENGTH_SHORT).show();
                  }
                  else {
                     Log.d("jsarjean", "failed");
                     Toast.makeText(getActivity(), "Create Failed", Toast.LENGTH_SHORT).show();
                  }
                  
               } catch (IOException e) {
                  Log.d("CreatePostsError!", e.toString());
               } finally {
                  FragmentManager fm = getActivity().getSupportFragmentManager();
                  Fragment f = new UserFeed_VP();
                  fm.beginTransaction().replace(R.id.content_frame, f).commit();
               }
                 return success;
              }
         }.execute(url);
      } catch (MalformedURLException e) {
         Log.e(null, e.toString());
      } catch (UnsupportedEncodingException e1) {
         Log.e(null, e1.toString());
      } 
   }
}
