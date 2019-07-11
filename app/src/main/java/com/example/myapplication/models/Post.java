package com.example.myapplication.models;

import android.os.Parcel;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";

    public String getDescription() {

        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description)
    {
        put(KEY_DESCRIPTION,description) ;
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image)
    {
        put(KEY_IMAGE,image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user)
    {
        put(KEY_USER,user);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }


    public static class Query extends ParseQuery<Post>{

        public Query(){
            super(Post.class);
        }

        public Query getTop()
        {
            setLimit(20);
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


    }


}

