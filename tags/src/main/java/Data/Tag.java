package Data;

import java.util.List;

/**
 * Created by caude on 9/13/2017.
 */

public interface Tag {

    void setLabel(String label);
    String getLabel();

    void setId(Object id);
    Object getId();

    int getCount();


    // This will store and manage foreign keys
    void addTagId(Object id);
    void removeTagId(Object id);
    List<Object> getTagIds();
    Object getTagId(int pos);
    void setTagIds(List<Object> ids);
    void removeIds(List<Object> ids);
    void removeAllTagIds();


    // May not need these?
    void addTaggedItem(Taggable item);
    void removeTaggedItem(Taggable item);
    List<Taggable> getItems();
    Taggable getItem(int pos);
    void setItems(List<Taggable> items);
    void removeItems(List<Taggable> items);
    void removeAllItems();
}
