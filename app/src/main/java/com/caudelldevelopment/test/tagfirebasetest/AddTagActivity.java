package com.caudelldevelopment.test.tagfirebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.caudelldevelopment.test.tags.UI.TagChip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class AddTagActivity extends AppCompatActivity implements ValueEventListener {

    private static final String LOG_TAG = AddTagActivity.class.getCanonicalName();

    private RecyclerView mTagList;
    private Button mAddBtn;
    private EditText mAddEdit;

    private TagAdapter mTagAdapter;

    private DatabaseReference mTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);

        // ---- Firebase ----

        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        mTags = firebase.getReference("tags");

        mTags.orderByKey().addListenerForSingleValueEvent(this);

        // ---- TagList & Adapter ----

        mTagList = findViewById(R.id.tag_list);
        mTagList.setLayoutManager(new LinearLayoutManager(this));

        mTagAdapter = new TagAdapter();
        mTagList.setAdapter(mTagAdapter);

        // ---- Other ----

        mAddBtn = findViewById(R.id.addtag_btn);
        mAddEdit = findViewById(R.id.addtag_edittext);

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(LOG_TAG, "Add Button Clicked.");

                String text = mAddEdit.getText().toString().trim();

                if (!text.isEmpty()) {
                    // Add new tag to RecyclerView
                    TagChip newTag = new TagChip(text);

                    mTags.setValue(newTag);


//                    mTagAdapter.tags.add(newTag);
//                    mTagAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.v(LOG_TAG, "onDataChange: " + dataSnapshot.getKey() + ", " + dataSnapshot.toString());

        TagChip newTag = dataSnapshot.getValue(TagChip.class);

        if (newTag != null) {
            Log.v(LOG_TAG, "Tag label: " + newTag.getName());
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w(LOG_TAG, "Single Value Event - onCanceled: " + databaseError.getMessage());
    }

    protected class TagAdapter extends RecyclerView.Adapter<TagViewHolder> {

        private List<TagChip> tags;

        TagAdapter() {
            tags = new LinkedList<>();
        }

        public TagAdapter(List<TagChip> data) {
            tags = data;
        }

        @Override
        public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.addtag_list_item, parent, false);

            return new TagViewHolder(v);
        }

        @Override
        public void onBindViewHolder(TagViewHolder holder, int position) {
            TagChip currTag = tags.get(position);
            holder.tagLabel.setText(currTag.getName());
        }

        @Override
        public int getItemCount() {
            return tags.size();
        }
    }

    protected class TagViewHolder extends RecyclerView.ViewHolder {

        public TextView tagLabel;

        public TagViewHolder(View itemView) {
            super(itemView);
            tagLabel = itemView.findViewById(R.id.tag_label_item);
        }
    }
}
