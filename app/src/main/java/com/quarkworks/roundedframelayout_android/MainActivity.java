package com.quarkworks.roundedframelayout_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.quarkworks.roundedframelayout.RoundedFrameLayout;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText inputEditText;
    Button leftButton;
    View refreshButton;
    RoundedFrameLayout refreshButtonContainer;
    RecyclerView recyclerView;

    MyAdapter adapter;
    ArrayList<Comment> comments;

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View composeContainer = findViewById(R.id.compost_container);
        inputEditText = (EditText) composeContainer.findViewById(R.id.input_edit_text);
        leftButton = (Button) composeContainer.findViewById(R.id.left_button);
        refreshButton = composeContainer.findViewById(R.id.refresh_button);
        refreshButtonContainer = (RoundedFrameLayout) findViewById(R.id.refresh_button_container);

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comments.add(new Comment(UUID.randomUUID().toString(),
                        inputEditText.getText().toString().trim()));
                adapter.notifyDataSetChanged();

                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();

                refreshButtonContainer.setCornerRadiusTopLeft(random.nextInt(31));
                refreshButtonContainer.setCornerRadiusTopRight(random.nextInt(31));
                refreshButtonContainer.setCornerRadiusBottomRight(random.nextInt(31));
                refreshButtonContainer.setCornerRadiusBottomLeft(random.nextInt(31));
                refreshButtonContainer.requestLayout();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        comments = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            String id = UUID.randomUUID().toString();
            String message = id;
            for (int j = 0; j < rand.nextInt(8); j++) {
                message += id;
            }

            comments.add(new Comment(id, message));
        }

        adapter = new MyAdapter(comments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }
}
