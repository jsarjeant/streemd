package com.example.streemd;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class VideoList extends Fragment {
   protected ArrayList<Post> m_arrPostList;
   protected PostListAdapter m_postAdapter;
   
   protected ListView m_vwPostLayout;
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
      super.onCreateView(inflater, container, savedInstanceState);
      View view = inflater.inflate(R.layout.user_feed, container, false);
      
      this.m_arrPostList = new ArrayList<Post>();
      this.m_postAdapter = new PostListAdapter(getActivity().getApplicationContext(), this.m_arrPostList);
      
      this.m_vwPostLayout = (ListView) view.findViewById(R.id.postListView);
      this.m_vwPostLayout.setAdapter(m_postAdapter);
      
    //TODO Pull in posts from DB
      /*addPost(new Post("d2ZNaLQD60Y", "Test Post"));*/
      addPost(new Post("wKJ9KzGQq0w", "Youtube Test Video", "This is a video to be used in YouTube Player API demos.",  "other"));
      addPost(new Post("d8i-H6MVl18","Introduction to android - android tutorial for bigginers to advanced", "Hi i am posting all the videos that helped me in learning android. now i am having 3 years of mobile experience and any one want to learn android subscribe for my channel and ask any doubts in android.i will explain you.Please subscribe for channel.",  "other"));
      addPost(new Post("d2ZNaLQD60Y", "Game of Thrones Trailer #2 - Vengeance (HBO)", "Check out the new GOT trailer!! Only 6 more weeks I can't wait!",  "other"));
      
      return view;
  }
   
   public void addPost(Post post) {
      this.m_arrPostList.add(post);
      this.m_postAdapter.notifyDataSetChanged();
   }
}
