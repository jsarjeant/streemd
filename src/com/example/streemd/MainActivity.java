package com.example.streemd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        StreemdApplication appState = ((StreemdApplication) this.getApplication());
        String toastText = "Welcome " +  appState.session.getUsername() + "!";
		Toast toast = Toast.makeText(this, toastText, Toast.LENGTH_SHORT);
		toast.show();
        
        mPlanetTitles = getResources().getStringArray(R.array.navigation_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        selectItem(0);
    }
    
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        //Fragment fragment = new Fragment();
       // Bundle args = new Bundle();
        //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        //fragment.setArguments(args);
       Fragment fragment = null;
       FragmentManager fragmentManager = getSupportFragmentManager();
       switch(position) {
          case 0:
             fragment = new UserFeed_VP();
             break;  
          case 1:
             fragment = new SearchVideos();
             break;
          case 2:
             fragment = new SearchUsers();
             break;
          case 3:
        	  fragment = new MyProfile();
        	  Bundle args = new Bundle();
        	  args.putString("username", ((StreemdApplication) this.getApplication()).session.getUsername());
        	  fragment.setArguments(args);
        	  break;
          case 4:
        	  ((StreemdApplication) this.getApplication()).session.setUsername(null);
        	  ((StreemdApplication) this.getApplication()).session.setPassword(null);
        	  Intent intent = new Intent(MainActivity.this, SignInActivity.class);
		      startActivity(intent);
		      return;
       }
        // Insert the fragment by replacing any existing fragment
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
       
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        return false;
    }
}