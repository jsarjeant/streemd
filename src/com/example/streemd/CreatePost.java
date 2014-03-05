package com.example.streemd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class CreatePost extends Fragment{
   protected Button m_vwCancel, m_vwCreate;
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
      super.onCreateView(inflater, container, savedInstanceState);
      View view =  inflater.inflate(R.layout.create_post, container, false);
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
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment f = new UserFeed_VP();
            fm.beginTransaction().replace(R.id.content_frame, f).commit();
         }
      });
      return view;
  }
}
