package com.example.streemd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SearchVideos extends Fragment{
   protected EditText m_vwSearchField;
   protected Button m_vwSearchButton;
   
   public void onCreate() {
   }
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
      super.onCreateView(inflater, container, savedInstanceState);
      View view = inflater.inflate(R.layout.search_videos, container, false);
      this.m_vwSearchButton = (Button) view.findViewById(R.id.search_videos_button);
      this.m_vwSearchButton.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View arg0) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment f = new CreatePost();
            fm.beginTransaction().replace(R.id.content_frame, f).commit();
         }
         
      });
      return view;
   }
   
   public void initLayout() {
      
   }
}
