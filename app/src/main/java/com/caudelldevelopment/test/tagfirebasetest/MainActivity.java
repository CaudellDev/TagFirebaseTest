package com.caudelldevelopment.test.tagfirebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.caudelldevelopment.test.tags.UI.TagChip;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import Data.BaseItem;
import Data.Tag;
import Data.Taggable;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ValueEventListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ChipsInput mTagInput;
    private List<TagChip> tags;
    private List<String> tagIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Tags

        tags = new LinkedList<>();
        mTagInput = findViewById(R.id.tag_input);
        mTagInput.addChipsListener(new ChipsInput.ChipsListener() {
            @Override
            public void onChipAdded(ChipInterface chip, int i) {
                Log.v(LOG_TAG, "Adding chip. Chip label, loc: " + chip.getLabel() + ", " + i);

                mTagInput.addChip(chip);
            }

            @Override
            public void onChipRemoved(ChipInterface chip, int i) {
                Log.v(LOG_TAG, "Removing chip. Chip label, loc: " + chip.getLabel() + ", " + i);
            }

            @Override
            public void onTextChanged(CharSequence charSequence) {
                Log.v(LOG_TAG, "Text changed: " + charSequence);
            }
        });

        mTagInput.setFilterableList(tags);

        List<TagChip> tempList = (List<TagChip>) mTagInput.getFilterableList();


        if (tempList != null) {
            if (tempList.isEmpty()) {
                Log.w(LOG_TAG, "List is empty.");
            } else {
                for (int i = 0; i < tempList.size(); i++) {
                    Log.d(LOG_TAG, "Chip " + i + ": " + tempList.get(i).getLabel());
                }
            }
        } else {
            Log.e(LOG_TAG, "List is null.");
        }

        // ------ Firebase -------

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference root = database.getReference();



        root.child("tags").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.v(LOG_TAG,
                                "ValueEventListener - onDataChange: " +
                                dataSnapshot.getKey() + ", " +
                                dataSnapshot.toString());

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                        List<Tag> tags = new LinkedList<>();

                        int i = 0;
                        for (DataSnapshot child : children) {
                            Log.v(LOG_TAG, "Child " + i + ": " + child.getKey() + ", " + child.toString());
                            Log.v(LOG_TAG, "Child.class: " + child.getClass().getName() + ", " + child.getClass().toString());
                            Log.v(LOG_TAG, "TagChip.class: " + TagChip.class.getName() + ", " + TagChip.class.toString());

//                            TagChip temp = child.getValue(TagChip.class);
                            Object key = child.getValue();

                            Log.v(LOG_TAG, "ValueEvent, onDataChange - child value: " + key);

                            i++;
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(LOG_TAG + " : ValueEvent", "onCancelled: " + databaseError.getMessage());
                    }
        });

        final List<Taggable> itemsList = new LinkedList<>();
        root.child("items").addListenerForSingleValueEvent(this);


        // Read from the database
//        test.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(LOG_TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(LOG_TAG, "Failed to read value.", error.toException());
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addtag) {
            Intent intent = new Intent(this, AddTagActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // ########## --- ValueEventListener --- ##########

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        String rootKey = dataSnapshot.getKey();
        Log.v(LOG_TAG, "onDataChange - rootKey: " + rootKey);

        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

        int i = 0;

        for (DataSnapshot child : children) {
            Log.v(LOG_TAG, rootKey + " db child: " + child.getKey() + ", " + child.toString());

            switch (rootKey) {
                case "Tag":
                    break;
                case "Items":

                    BaseItem item = child.getValue(BaseItem.class);
                    // Add to list

                    break;
                default:
                    Log.w(LOG_TAG, "onDataChange - root key not recognized: " + rootKey);
            }
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w(LOG_TAG, "XXXXXX --- onCancelled --- XXXXXX");
        Log.w(LOG_TAG, databaseError.getMessage());
    }

    protected class ItemsHolder extends RecyclerView.ViewHolder {



        public ItemsHolder(View itemView) {
            super(itemView);
        }
    }

    protected class ItemsAdapter extends RecyclerView.Adapter<ItemsHolder> {

        List<String> items;

        public ItemsAdapter() {
            items = new LinkedList<>();
        }

        public ItemsAdapter(List<String> data) {
            items = data;
        }

        @Override
        public ItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(ItemsHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
