package Data;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by caude on 10/19/2017.
 */

public class BaseTag implements Tag {

    private String name;
    private Object id;
    List<Object> itemIds;

    public BaseTag() {

    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
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
    public int getCount() {
        return itemIds.size();
    }

    @Override
    public void addTagId(Object id) {
        itemIds.add(id);
    }

    @Override
    public void removeTagId(Object id) {
        itemIds.remove(id);
    }

    @Override
    public List<Object> getTagIds() {
        return itemIds;
    }

    @Override
    public Object getTagId(int pos) {
        return itemIds.get(pos);
    }

    @Override
    public void setTagIds(List<Object> ids) {
        itemIds = ids;
    }

    @Override
    public void removeIds(List<Object> ids) {
        itemIds.removeAll(ids);
    }

    @Override
    public void removeAllTagIds() {
        itemIds.clear();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(name);

        builder.append(" (");
        for (int i = 0; i < itemIds.size(); i++) {

            if (itemIds.get(i) != null) {
                builder.append(itemIds.get(i));
                if (i != itemIds.size() - 1) builder.append(", ");
            }
        }
        builder.append(")");

        return builder.toString();
    }
}
