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
        String imageUrl= post.getImage().getUrl();
        Glide.with(context)
                .load(imageUrl)
                .into(viewHolder.ivImage);


        // open a new details activity if item is long clicked
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final Intent intent = new Intent(context, DetailsActivity.class);

                //pass in post that was selected
                intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                context.startActivity(intent);

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

        public ViewHolder(View itemView)
        {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvUser = itemView.findViewById(R.id.tvUser);
            tvUserTop = itemView.findViewById(R.id.tvUserTop);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

    }
}
