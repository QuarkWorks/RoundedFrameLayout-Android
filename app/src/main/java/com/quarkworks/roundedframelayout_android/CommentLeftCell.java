package com.quarkworks.roundedframelayout_android;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by zekunwang on 4/17/17.
 */

public class CommentLeftCell extends FrameLayout {

    public ImageView profileImageView;
    public TextView commentTextView;
    public @DrawableRes int imageResId;

    public CommentLeftCell(@NonNull Context context) {
        super(context);
        initialize();
    }

    public CommentLeftCell(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public CommentLeftCell(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.comment_cell_left, this);

        this.profileImageView = (ImageView) findViewById(R.id.profile_image_view);
        this.commentTextView = (TextView) findViewById(R.id.comment_text_view);
        final FrameLayout imageViewContainer = (FrameLayout) findViewById(R.id.profile_image_container);



        imageViewContainer.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                imageViewContainer.requestLayout();
            }
        });

        profileImageView.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                Glide.with(getContext()).load(imageResId)
                        // Using placeholder causes the issue
                        .placeholder(R.drawable.placeholder)
                        .into(profileImageView);
            }
        });
    }

    public void setViewData(Comment comment) {
        int imageId = comment.commentId.hashCode() % 5 + 1;
        imageResId = R.drawable.image_1;

        switch (imageId) {
            case 1:
                imageResId = R.drawable.image_1;
                break;
            case 2:
                imageResId = R.drawable.image_2;
                break;
            case 3:
                imageResId = R.drawable.image_3;
                break;
            case 4:
                imageResId = R.drawable.image_4;
                break;
            case 5:
                imageResId = R.drawable.image_5;
                break;
        }

        Glide.with(getContext()).load(imageResId)
                // Using placeholder causes the issue
                .placeholder(R.drawable.placeholder)
                .into(profileImageView);
        commentTextView.setText(comment.textString);
    }
}
