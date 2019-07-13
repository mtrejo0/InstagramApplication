package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.Post;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mPosts;
    Context context;



    public PostAdapter(List<Post> posts)
    {
        mPosts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // create new view with item post xml
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate((R.layout.item_post),viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        // gets the post that corresponds to that position in the recycler view
        final Post post = mPosts.get(i);

        // fill in text views
        viewHolder.tvDescription.setText(post.getDescription());
        viewHolder.tvUser.setText(post.getUser().getUsername());
        viewHolder.tvUserTop.setText(post.getUser().getUsername());
        viewHolder.tvDate.setText(post.getCreatedAt().toString().substring(0,10));

        // gets image url from the parse object
        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(viewHolder.ivImage);

        setPostLongClickable(viewHolder,post);

        setProfileImageClickable(viewHolder,post);

        // load in profile image to holder
        Glide.with(context)
                .load(post.getUser().getParseFile("profileImage").getUrl())
                .into(viewHolder.ivProfileImage);

    }

    public void setProfileImageClickable(ViewHolder viewHolder, final Post post)
    {
        // set profile image as clickable
        viewHolder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser user = post.getUser();
                final Intent profileDetailsIntent = new Intent(context, ProfileDetailsActivity.class);
                //pass in user that was selected
                profileDetailsIntent.putExtra("user", user.getUsername());
                profileDetailsIntent.putExtra(ParseUser.class.getSimpleName(), Parcels.wrap(user));
                context.startActivity(profileDetailsIntent);

            }
        });
    }

    public void setPostLongClickable(ViewHolder viewHolder, final Post post)
    {
        // open a new details activity if item is long clicked
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final Intent postDetailIntent = new Intent(context, PostDetailsActivity.class);

                //pass in post that was selected
                postDetailIntent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                context.startActivity(postDetailIntent);

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivImage;
        public TextView tvDescription;
        public TextView tvUser;
        public TextView tvUserTop;
        public TextView tvDate;
        public ImageView ivProfileImage;


        public ViewHolder(View itemView)
        {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvUser = itemView.findViewById(R.id.tvUser);
            tvUserTop = itemView.findViewById(R.id.tvUserTop);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
        }

    }
}
