   package com.example.streemd;

   import java.util.List;

   import android.content.Context;
   import android.view.View;
   import android.view.ViewGroup;
   import android.widget.BaseAdapter;

   public class PostListAdapter extends BaseAdapter {
      private Context m_context;
   
      private List<Post> m_postList;
   
      public PostListAdapter(Context context, List<Post> postList) {
         this.m_context = context;
         this.m_postList = postList;
      }
   
      public int getCount() {
         return this.m_postList.size();
      }
   
      public Object getItem(int pos) {
         return this.m_postList.get(pos);
      }
   
      public long getItemId(int pos) {
         return pos;
      }
   
      public View getView(int pos, View convertView, ViewGroup parent) {
         PostView postView = null;
      
         if (convertView == null) {
            postView = new PostView(m_context, this.m_postList.get(pos));
         }
         else {
            postView = (PostView) convertView;
         }
         postView.setPost(this.m_postList.get(pos));
         return postView;
      }
   }
