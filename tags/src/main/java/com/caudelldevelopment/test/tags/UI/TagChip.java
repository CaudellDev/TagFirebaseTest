package com.caudelldevelopment.test.tags.UI;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pchmn.materialchips.model.ChipInterface;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import Data.Tag;
import Data.Taggable;

/**
 * Created by caude on 8/19/2017.
 */

public class TagChip implements ChipInterface, Tag {

    private static final String LOG_TAG = TagChip.class.getSimpleName();

    private String label;
    private Object id;
    private List<Taggable> items;
    private List<Object> tagIds;

    // -------------------------------------------

    // Required for the DataSnapshot.getValue(TagChip.class)
    public TagChip() {
        label = "TagChip(" + this.hashCode() + ")";
        items = new LinkedList<>();
        tagIds = new LinkedList<>();
    }

    public TagChip(String label) {
        this.label = label;
        items = new LinkedList<>();
        tagIds = new LinkedList<>();
    }

    @Override
    public String getLabel() {
        return label;
    }
    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void setId(Object id) {
        Log.v(LOG_TAG, "setId - id.toString(): " + id.toString());
        this.id = id;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public void addTaggedItem(Taggable item) {
        items.add(item);
    }

    @Override
    public void removeTaggedItem(Taggable item) {
        items.remove(item);
    }

    @Override
    public Taggable getItem(int pos) {
        return items.get(pos);
    }

    @Override
    public List<Taggable> getItems() {
        return items;
    }

    @Override
    public void setItems(List<Taggable> items) {
        this.items = items;
    }

    @Override
    public void removeAllItems() {

    }

    @Override
    public void removeItems(List<Taggable> items) {

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public void addTagId(Object id) {
        tagIds.add(id);
    }

    @Override
    public void removeTagId(Object id) {
        tagIds.remove(id);
    }

    @Override
    public List<Object> getTagIds() {
        return tagIds;
    }

    @Override
    public Object getTagId(int pos) {
        return tagIds.get(pos);
    }

    @Override
    public void setTagIds(List<Object> ids) {
        tagIds.addAll(ids);
    }

    @Override
    public void removeIds(List<Object> ids) {
        tagIds.removeAll(ids);
    }

    @Override
    public void removeAllTagIds() {
        tagIds = new LinkedList<>();
    }

    // ChipInterface only

    @Override
    public Uri getAvatarUri() {
        return null;
    }

    @Override
    public Drawable getAvatarDrawable() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }

}
