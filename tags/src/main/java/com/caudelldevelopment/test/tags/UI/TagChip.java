package com.caudelldevelopment.test.tags.UI;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.pchmn.materialchips.model.ChipInterface;

import java.util.LinkedList;
import java.util.List;

import Data.BaseTag;
import Data.Tag;
import Data.Taggable;

/**
 * Created by caude on 8/19/2017.
 */

public class TagChip extends BaseTag implements ChipInterface {

    private static final String LOG_TAG = TagChip.class.getSimpleName();


    // -------------------------------------------

    // Required for the DataSnapshot.getValue(TagChip.class)
    public TagChip() {
    }

    public TagChip(String name) {
        setName(name);
    }

    public TagChip(BaseTag baseTag) {
        setName(baseTag.getName());
        setId(baseTag.getId());
        setTagIds(baseTag.getTagIds());
    }

    @Override
    public Object getId() {
        return super.getId();
    }



    // ChipInterface only

    @Override
    public String getLabel() {
        return getName();
    }

    @Override public String getInfo() {
        return null;
    }
    @Override public Uri getAvatarUri() {
        return null;
    }
    @Override public Drawable getAvatarDrawable() {
        return null;
    }
}
