package com.caudelldevelopment.test.tagfirebasetest;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caudelldevelopment.test.tagfirebasetest.data.FirebaseUtil;
import com.caudelldevelopment.test.tags.UI.TagChip;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import Data.BaseItem;
import Data.BaseTag;
import Data.Tag;
import Data.Taggable;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ValueEventListener, View.OnClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ChipsInput mTagInput;
    private List<TagChip> availableTags;
    private List<TagChip> selectedTags;
    
    private RecyclerView mRecyclerView;
    private ItemsAdapter mAdapter;
    private FloatingActionButton mAddItemFab;

    private List<BaseItem> mItems;
    private List<BaseTag> mTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // TODO: Assign FAB and set callback listener

        mAddItemFab = findViewById(R.id.main_add_fab);
        mAddItemFab.setOnClickListener(this);
        
        mRecyclerView = findViewById(R.id.main_item_list);
        mAdapter = new ItemsAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);



        // Tags and Items

        mItems = new LinkedList<>();
        availableTags = new LinkedList<>();
        selectedTags = new LinkedList<>();
        mTagInput = findViewById(R.id.tag_input);
        mTagInput.addChipsListener(new ChipsInput.ChipsListener() {
            @Override
            public void onChipAdded(ChipInterface chip, int i) {
                Log.v(LOG_TAG, "Adding chip. Chip label, loc: " + chip.getLabel() + ", " + i);

                TagChip currTag = (TagChip) chip;

//                mTagInput.addChip(chip);
                selectedTags.add(currTag);
                mAdapter.update();
            }

            @Override
            public void onChipRemoved(ChipInterface chip, int i) {
                Log.v(LOG_TAG, "Removing chip. Chip label, loc: " + chip.getLabel() + ", " + i);

                TagChip currTag = (TagChip) chip;

                mTagInput.removeChip(chip);
                selectedTags.remove(currTag);
                mAdapter.update();
            }

            @Override
            public void onTextChanged(CharSequence charSequence) {
                Log.v(LOG_TAG, "Text changed: " + charSequence);
            }
        });

        mTagInput.setFilterableList(availableTags);

        List<TagChip> tempList = (List<TagChip>) mTagInput.getFilterableList();


        // Get the list entered in the ChipInput. Use that to filter the item list.
        if (tempList != null) {
            if (tempList.isEmpty()) {
                Log.w(LOG_TAG, "List is empty.");
            } else {
                for (int i = 0; i < tempList.size(); i++) {
                    Log.d(LOG_TAG, "Chip " + i + ": " + tempList.get(i).getName());
                }
            }
        } else {
            Log.e(LOG_TAG, "List is null.");
        }

        // ------ Firebase -------
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference root = database.getReference();

        // Pull data from Realtime DB and put it into the ChipInput and RecyclerView.
        root.child("tags").addListenerForSingleValueEvent(this);
        root.child("items").addListenerForSingleValueEvent(this);
    }

    public void updateItems(List<BaseItem> items) {
        mItems = items; // Replace list for now. Add only new ones later.
        mAdapter.update();
    }

    public void updateTags(List<BaseTag> tags) {
        mTags = tags;

        // availableTags
        List<TagChip> temp = new LinkedList<>();
        for (BaseTag curr : tags) temp.add(new TagChip(curr));
        availableTags = temp;

        // ChipInput
        mTagInput.setFilterableList(availableTags);
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
        // Inflate the menu; this adds data to the action bar if it is present.
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

        // Using Util function instead
        switch (rootKey) {
            case "tags":
                List<BaseTag> tags = FirebaseUtil.getTags(dataSnapshot);
                updateTags(tags);
                break;
            case "items":
                List<BaseItem> items = FirebaseUtil.getItems(dataSnapshot);
                updateItems(items);
                break;
            default:
                Log.w(LOG_TAG, "onDataChange - root key not recognized: " + rootKey);
        }

        // Go through the list of data and put them in their respective lists

//        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
//        for (DataSnapshot child : children) {
//            Log.v(LOG_TAG, rootKey + " db child: " + child.getKey() + ", " + child.toString());
//            Log.v(LOG_TAG, "Child: " + child.getKey() + ", " + child.toString());
//            Log.v(LOG_TAG, "Child.class: " + child.getClass().getName() + ", " + child.getClass().toString());
//
//            switch (rootKey) {
//                case "tags":
////                    Log.v(LOG_TAG, "TagChip.class: " + TagChip.class.getName() + ", " + TagChip.class.toString());
//
//                    TagChip currTag = child.getValue(TagChip.class);
////                    Object key = child.getValue();
//
//                    if (currTag != null) {
//                        Log.v(LOG_TAG, "ValueEvent, onDataChange - child label: " + currTag.getName());
//
//                        availableTags.add(currTag);
//                    } else {
//                        Log.w(LOG_TAG, "ValueEvent, onDataChange - TagChip is null!");
//                    }
//
//                    break;
//                case "items":
//                    Log.v(LOG_TAG, "BaseItem.class: " + BaseItem.class.getName() + ", " + BaseItem.class.toString());
//
//                    BaseItem item = child.getValue(BaseItem.class);
//
//                    Log.v(LOG_TAG, "onDataChange - item.getTagLabels() == null: " + (item.getTagLabels() == null));
//
//                    List<String> tags_str = item.getTagLabels();
//                    if (tags_str != null) {
//                        for (int i = 0; i < tags_str.size(); i++) {
//                            Log.v(LOG_TAG, "onDataChange - item tags_str(" + i + "): " + tags_str.get(i));
//                        }
//                    } else {
//                        Log.w(LOG_TAG, "onDataChange - item tags_str is null!!!");
//                    }
//
//                    listItems.add(item);
//
//                    break;
//                default:
//                    Log.w(LOG_TAG, "onDataChange - root key not recognized: " + rootKey);
//            }
//        }
//
//        if (rootKey.equals("items")) {
//            mAdapter.update();
//        } else if (rootKey.equals("tags")) {
//            mTagInput.setFilterableList(availableTags);
//        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w(LOG_TAG, "XXXXXX --- onCancelled --- XXXXXX");
        Log.w(LOG_TAG, databaseError.getMessage());
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.main_add_fab) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            NewItemDialog dialog = NewItemDialog.newInstance(null);
            dialog.show(ft, NewItemDialog.TAG);
        } else {
            Log.w(LOG_TAG, "onClick from unrecognized view - id: " + view.getId() + ", " + view.toString());
        }

    }

    protected class ItemsHolder extends RecyclerView.ViewHolder {

        public TextView desc;
        public LinearLayout itemListLayout;

        public ItemsHolder(View view) {
            super(view);
//            desc = itemView;
            desc = view.findViewById(R.id.item_desc_tv);
//            itemListLayout = view.findViewById(R.id.main_item_chip_list);
        }
    }

    protected class ItemsAdapter extends RecyclerView.Adapter<ItemsHolder> {

        List<String> data;

        public ItemsAdapter() {
            data = new LinkedList<>();
        }

//        public ItemsAdapter(List<String> data) {
//            this.data = data;
//            this.tags = new LinkedList<>();
//        }

        @Override
        public ItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View tv = LayoutInflater.from(getApplicationContext()).inflate(R.layout.main_list_item, parent, false);
            return new ItemsHolder(tv);
        }

        @Override
        public void onBindViewHolder(ItemsHolder holder, int position) {
            String descText = data.get(position);
            Log.v(LOG_TAG, "ItemsAdapter - onBindViewHolder desc: " + descText);

            holder.desc.setText(descText);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void update() {
            Log.v(LOG_TAG, "ItemsAdapter - update called.");

            // Loop through the items, adding to the filtered list if they contain the tags.
            List<BaseItem> filteredList = new LinkedList<>();
            for (BaseItem currItem : mItems) {
                boolean matches = true;

                // All tags need to match. If one doesn't, match is false and don't need to check more.
                // If selectedTags is empty, the for loop shouldn't start and match is still true.
                for (Tag selectedTag : selectedTags) {
                    if (!currItem.isTagged(selectedTag)) {
                        matches = false;
                        break;
                    }
                }

                // Need to check all tags before adding. If they all match, then add to filtered list.
                if (matches) {
                    filteredList.add(currItem);
                }
            }

            // Get the Taggable list descriptions to their own list.
            data = new LinkedList<>();
            for (BaseItem currItem : filteredList) {
                String tempDesc = currItem.toString();

                Log.v(LOG_TAG, "ItemsAdapter.update - item: " + tempDesc);
                Log.v(LOG_TAG, "ItemsAdapter.update - currItem.getTagLabels.size: " + currItem.getTagLabels().size());
                Log.i(LOG_TAG, "ItemAdapter.update - description: " + tempDesc);

                data.add(tempDesc);
            }

            notifyDataSetChanged();
        }
    }
}
