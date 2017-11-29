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
        Log.v(LOG_TAG, "isTagged: " + tag.getName());
        return tags.contains(tag.getName());
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
    public void setTagLabels(List<String> tagLabels) {
        this.tags = tagLabels;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(getDesc());

        builder.append(" (");
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i) != null) {
                builder.append(tags.get(i));
                if (i != tags.size() - 1) builder.append(", ");
            }
        }
        builder.append(")");

        return builder.toString();
    }
}
