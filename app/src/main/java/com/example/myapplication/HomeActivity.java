package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.myapplication.fragments.ComposeFragment;
import com.example.myapplication.models.Post;
import com.example.myapplication.models.PostAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {




    PostAdapter postAdapter;
    ArrayList<Post> posts;
    RecyclerView rvPosts;

    private SwipeRefreshLayout swipeContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        rvPosts = findViewById(R.id.rvPosts);

        posts = new ArrayList<>();

        postAdapter = new PostAdapter(posts);

        LinearLayoutManager lin = new LinearLayoutManager(this);


        rvPosts.setLayoutManager(lin);


        rvPosts.setAdapter(postAdapter);

        loadTopPosts();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvPosts.getContext(),
                new LinearLayoutManager(this).getOrientation());
        rvPosts.addItemDecoration(dividerItemDecoration);


        swipeContainer =  findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                reloadTopPosts();

            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



        final FragmentManager fragmentManager = getSupportFragmentManager();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new  ComposeFragment();
                Intent i;
                switch (item.getItemId()) {
                    case R.id.btnLock:
//                        ParseUser.logOut();
//                        i = new Intent(HomeActivity.this,MainActivity.class);
//                        startActivity(i);
//                        finish();
                        fragment = new ComposeFragment();
                        break;
                    case R.id.btnCreate:

//                        i = new Intent(HomeActivity.this,MakePostActvity.class);
//
//                        // display the activity
//                        startActivityForResult(i,100);
                        fragment = new ComposeFragment();

                        break;
                    case R.id.btnHome:



                        break;



                    default: return true;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer,fragment).commit();
                        return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.btnCreate);



    }

    // handle the results from edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 100 && resultCode == RESULT_OK)
        {
            //TODO fix this reload when image is posted
//            final Post post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
//            posts.add(0, post);
//            // notify adapter and scroll up
//            postAdapter.notifyItemInserted(0);
//            rvPosts.scrollToPosition(0);
            reloadTopPosts();
        }

    }



    private void reloadTopPosts()
    {

        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser().decendingTime();


        posts.clear();
        postAdapter.notifyDataSetChanged();
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {


                if(e==null)
                {
                    for(int i = 0; i < objects.size();i++)
                    {
                        Log.d("HomeActivity","Post: "+i +" "+ objects.get(i).getDescription()+" "+objects.get(i).getUser().getUsername());
                        Post post = objects.get(i);
                        posts.add(post);
                        postAdapter.notifyItemInserted(posts.size()-1);
                    }
                    // end refresh icon
                    swipeContainer.setRefreshing(false);
                }
                else
                {
                    e.printStackTrace();
                }
            }
        });

    }


    private void loadTopPosts()
    {

        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser().decendingTime();
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e==null)
                {
                    for(int i = 0; i < objects.size();i++)
                    {
                        Log.d("HomeActivity","Post: "+i +" "+ objects.get(i).getDescription()+" "+objects.get(i).getUser().getUsername());
                        Post post = objects.get(i);
                        posts.add(post);
                        postAdapter.notifyItemInserted(posts.size()-1);
                    }
                }
                else
                {
                    e.printStackTrace();
                }
            }
        });
    }

}
