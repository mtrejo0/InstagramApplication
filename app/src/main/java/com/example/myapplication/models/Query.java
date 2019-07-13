package com.example.myapplication.models;

import com.parse.ParseQuery;
import com.parse.ParseUser;

import static com.example.myapplication.models.Post.KEY_CREATED_AT;

public class Query extends ParseQuery<Post> {

    public Query(){
        super(Post.class);
    }

    public Query getTop(int maxPosts) {
        setLimit(maxPosts);
        return this;
    }

    // makes sure the user json is also included in the response
    public Query withUser()
    {
        include("user");
        return this;
    }

    // gets the order of elements in decending order by time
    public Query decendingTime()
    {
        addDescendingOrder(KEY_CREATED_AT);
        return this;
    }

    public Query onlyCurrentUser()
    {
        whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        return this;
    }

    public Query onlyUser(ParseUser user)
    {
        whereEqualTo(Post.KEY_USER,user);
        return this;
    }


}
