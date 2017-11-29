package com.caudelldevelopment.test.tagfirebasetest.data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import Data.BaseItem;
import Data.BaseTag;
import Data.Tag;
import Data.Taggable;

/**
 * Created by caude on 11/21/2017.
 */

public class FirebaseUtil {

    public static final String LOG_TAG = FirebaseUtil.class.getSimpleName();

    public static List<BaseTag> getTags(DataSnapshot data) {
        List<BaseTag> tags = new LinkedList<>();

        // Loop through to get a child
        Iterable<DataSnapshot> children = data.getChildren();
        for (DataSnapshot child : children) {
            Log.v(LOG_TAG, "Tag db child: " + child.getKey() + ", " + child.toString());
            Log.v(LOG_TAG, "Child: " + child.getKey() + ", " + child.toString());
            Log.v(LOG_TAG, "Child.class: " + child.getClass().getName() + ", " + child.getClass().toString());

            BaseTag tag = child.getValue(BaseTag.class);

            Log.v(LOG_TAG, "BaseTag from child: " + tag.toString());

            tags.add(tag);
        }

        return tags;
    }

    public static List<BaseItem> getItems(DataSnapshot data) {
        List<BaseItem> items = new LinkedList<>();

        Iterable<DataSnapshot> children = data.getChildren();
        for (DataSnapshot child : children) {
            Log.v(LOG_TAG, "Item db child: " + child.getKey() + ", " + child.toString());
            Log.v(LOG_TAG, "Child: " + child.getKey() + ", " + child.toString());
            Log.v(LOG_TAG, "Child.class: " + child.getClass().getName() + ", " + child.getClass().toString());

            BaseItem item = child.getValue(BaseItem.class);
            Log.v(LOG_TAG, "BaseItem from child: " + item.toString());
            items.add(item);
        }

        return items;
    }

    public static BaseTag getTag(DataSnapshot data, String tagLabel) {

        data = data.child(tagLabel);
        BaseTag tag = data.getValue(BaseTag.class);

        Log.v(LOG_TAG, "getTag - child " + tagLabel + ": " + tag);

        return tag;
    }
}
