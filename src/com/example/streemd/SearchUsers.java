package com.example.streemd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchUsers extends Fragment{
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
      super.onCreateView(inflater, container, savedInstanceState);
      return inflater.inflate(R.layout.search_users, container, false);
  }
}
