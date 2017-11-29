package Data;

import java.util.List;

/**
 * Created by caude on 9/13/2017.
 */

public interface Tag {

    void setName(String name);
    String getName();

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
}
