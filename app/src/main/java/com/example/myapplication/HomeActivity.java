package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.models.Post;
import com.example.myapplication.models.PostAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {




    private ImageButton btnCreate;
    private ImageButton btnLock;

    PostAdapter postAdapter;
    ArrayList<Post> posts;
    RecyclerView rvPosts;

    private SwipeRefreshLayout swipeContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        btnCreate = findViewById(R.id.btnCreate);
        btnLock = findViewById(R.id.btnLock);

        rvPosts = findViewById(R.id.rvPosts);

        posts = new ArrayList<>();

        postAdapter = new PostAdapter(posts);

        LinearLayoutManager lin = new LinearLayoutManager(this);


        lin.setReverseLayout(true);
        lin.setStackFromEnd(true);

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






        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent i = new Intent(HomeActivity.this,MakePostActvity.class);



                startActivityForResult(i,100);

            }
        });

        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                final Intent i = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(i);
                finish();

            }
        });


    }

    // handle the results from edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        reloadTopPosts();

    }



    private void reloadTopPosts()
    {

        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();

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
        postQuery.getTop().withUser();
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
