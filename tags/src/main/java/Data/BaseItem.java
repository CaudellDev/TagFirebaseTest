package Data;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by caude on 10/12/2017.
 */

public class BaseItem implements Taggable {

    private static final String LOG_TAG = BaseItem.class.getSimpleName();

    private String desc;
    private Object id;

    private List<String> tags;


    public BaseItem() {
        tags = new LinkedList<>();
        desc = "";
    }

    public BaseItem(String desc) {
        tags = new LinkedList<>();
        this.desc = desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void setId(Object id) {
        this.id = id;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public boolean isTagged(Tag tag) {
        Log.v(LOG_TAG, "isTagged: " + tag.getLabel());
        return tags.contains(tag.getLabel());
    }

    @Override
    public boolean isTagged(Object id) {
        Log.v(LOG_TAG, "isTagged: " + id.toString());
        return tags.contains(id.toString());
    }

    @Override
    public void addTagLabel(String tag) {
        tags.add(tag);
    }

    @Override
    public void removeTagLabel(String tag) {
        tags.remove(tag);
    }

    @Override
    public String getTagLabel(int pos) {
        return tags.get(pos);
    }

    @Override
    public List<String> getTagLabels() {
        return tags;
    }

    @Override
    public void addTagId(Object id) {
        tags.add(id.toString());
    }

    @Override
    public void removeTagId(Object id) {
        tags.remove(id.toString());
    }

    @Override
    public Object getTagId(int pos) {
        return tags.get(pos);
    }

    @Override
    public List<Object> getTagIds() {
        return null;
    }

    @Override
    public void addTag(Tag tag) {

    }

    @Override
    public void removeTag(Tag tag) {

    }

    @Override
    public Tag getTag(int pos) {
        return null;
    }

    @Override
    public List<Tag> getTags() {
        return null;
    }
}
