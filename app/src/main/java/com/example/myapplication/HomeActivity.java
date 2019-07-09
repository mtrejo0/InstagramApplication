package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
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




    private ImageButton btnRefresh;
    private ImageButton btnCreate;
    private ImageButton btnLock;

    PostAdapter postAdapter;
    ArrayList<Post> posts;
    RecyclerView rvPosts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        btnCreate = findViewById(R.id.btnCreate);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnLock = findViewById(R.id.btnLock);

        rvPosts = findViewById(R.id.rvPosts);

        posts = new ArrayList<>();

        postAdapter = new PostAdapter(posts);

        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        rvPosts.setAdapter(postAdapter);

        loadTopPosts();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvPosts.getContext(),
                new LinearLayoutManager(this).getOrientation());
        rvPosts.addItemDecoration(dividerItemDecoration);



        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 reloadTopPosts();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent i = new Intent(HomeActivity.this,MakePostActvity.class);

                startActivity(i);

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
