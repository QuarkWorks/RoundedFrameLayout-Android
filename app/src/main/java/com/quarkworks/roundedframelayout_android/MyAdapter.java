package com.quarkworks.roundedframelayout_android;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by zekunwang on 4/17/17.
 */

public class MyAdapter extends RecyclerView.Adapter {

    ArrayList<Comment> comments;

    public MyAdapter(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(new CommentLeftCell(parent.getContext())) {};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CommentLeftCell leftCell = (CommentLeftCell) holder.itemView;
        leftCell.setViewData(comments.get(position));

        leftCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("item click", "item click: " + position);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
