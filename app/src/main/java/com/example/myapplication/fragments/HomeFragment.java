package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.PostAdapter;
import com.example.myapplication.R;
import com.example.myapplication.importedFiles.EndlessRecyclerViewScrollListener;
import com.example.myapplication.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    PostAdapter postAdapter;
    ArrayList<Post> posts;
    RecyclerView rvPosts;
    ImageButton btnLock;

    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;

    int maxPosts = 20;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate((R.layout.fragment_home),container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rvPosts = view.findViewById(R.id.rvPosts);
        posts = new ArrayList<>();
        postAdapter = new PostAdapter(posts);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPosts.setAdapter(postAdapter);

        btnLock = view.findViewById(R.id.btnLockUser);

        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // log out user
                ParseUser.logOut();

                // start log in page activity
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);

                // kill current screen
                getActivity().finish();

            }
        });


        // add dividers on posts
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvPosts.getContext(),
                new LinearLayoutManager(getContext()).getOrientation());
        rvPosts.addItemDecoration(dividerItemDecoration);


        // set up swipe container on recycler view
        setUpSwipeContainer(getView());

        // populate timeline with top 20 posts
        loadTopPosts(maxPosts);



        // set up scroll listener to know when to reload posts
        infiniteScroll();

    }

    public void infiniteScroll()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list

                infiniteReload(maxPosts+=20);
                rvPosts.scrollToPosition(maxPosts-20);


            }
        };
        // Adds the scroll listener to RecyclerView
        rvPosts.addOnScrollListener(scrollListener);
    }


    public void setUpSwipeContainer(View v)
    {
        swipeContainer =  v.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                reloadTopPosts(20);

            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void infiniteReload(final int maxPosts)
    {

        final Post.Query postQuery = new Post.Query();
        postQuery.getTop(maxPosts).withUser().decendingTime();

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {


                if(e==null)
                {
                    for(int i = maxPosts-20; i < objects.size();i++)
                    {
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



    private void reloadTopPosts(int maxPosts)
    {

        final Post.Query postQuery = new Post.Query();
        postQuery.getTop(maxPosts).withUser().decendingTime();


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


    private void loadTopPosts(int maxPosts)
    {

        final Post.Query postQuery = new Post.Query();
        postQuery.getTop(maxPosts).withUser().decendingTime();
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
