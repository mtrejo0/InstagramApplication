package com.example.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.PostAdapter;
import com.example.myapplication.R;
import com.example.myapplication.importedFiles.EndlessRecyclerViewScrollListener;
import com.example.myapplication.models.Post;
import com.example.myapplication.models.Query;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    PostAdapter postAdapter;
    ArrayList<Post> posts;
    RecyclerView rvPosts;
    TextView tvUsername;
    ImageView ivProfileImage;
    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;
    int maxPosts = 20;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate((R.layout.fragment_profile),container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rvPosts = view.findViewById(R.id.rvPosts);
        posts = new ArrayList<>();
        postAdapter = new PostAdapter(posts);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPosts.setAdapter(postAdapter);

        // set username under photo
        tvUsername = view.findViewById(R.id.tvUsername);
        tvUsername.setText(ParseUser.getCurrentUser().getUsername());

        // load profile picture into holder
        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        Glide.with(this)
                .load(ParseUser.getCurrentUser().getParseFile("profileImage").getUrl())
                .into(ivProfileImage);

        // add dividers on posts
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvPosts.getContext(),
                new LinearLayoutManager(getContext()).getOrientation());
        rvPosts.addItemDecoration(dividerItemDecoration);

        // set up swipe container on recycler view
        setUpSwipeContainer(getView());

        // populate timeline with top 20 posts
        loadTopPosts(maxPosts,false);

        // set up scroll listener to know when to reload posts
        setUpInfiniteScroll();

    }

    public void setUpInfiniteScroll()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                // reload 20 more items and increase maXPosts
                infiniteReload(maxPosts+=20);
                // scroll to the same position you were before the reload
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
                // if swiped reload more posts
                loadTopPosts(20,true);
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


        final Query postQuery = new Query();
        // get the top maxPost items
        postQuery.getTop(maxPosts)
                // include the user object
                .withUser()
                // in chronological order
                .decendingTime()
                .onlyCurrentUser();;

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e==null)
                {
                    // only add the new posts at the end of the current post adapter
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


    private void loadTopPosts(int maxPosts, boolean refresh)
    {

        final Query postQuery = new Query();
        // get the top maxPost items
        postQuery.getTop(maxPosts)
                // include the user object
                .withUser()
                // in chronological order
                .decendingTime()
                .onlyCurrentUser();

        // if its a refresh call then clear the posts beforehand
        if (refresh) {
            posts.clear();
            postAdapter.notifyDataSetChanged();
        }

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e==null)
                {
                    for(int i = 0; i < objects.size();i++)
                    {
                        // add all posts to recycler view
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



}
