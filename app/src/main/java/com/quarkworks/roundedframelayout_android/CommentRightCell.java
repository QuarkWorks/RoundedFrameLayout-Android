package com.quarkworks.roundedframelayout_android;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zekunwang on 4/17/17.
 */

public class CommentRightCell extends FrameLayout {

    public ImageView profileImageView;
    public TextView commentTextView;

    public CommentRightCell(@NonNull Context context) {
        super(context);
        initialize();
    }

    public CommentRightCell(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public CommentRightCell(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.comment_cell_right, this);

        this.profileImageView = (ImageView) findViewById(R.id.profile_image_view);
        this.commentTextView = (TextView) findViewById(R.id.comment_text_view);
    }

    public void setViewData(Comment comment) {
        int imageId = comment.commentId.hashCode() % 5;
        commentTextView.setText(comment.textString);
    }
}
