package Data;

import java.util.List;

/**
 * Created by caude on 9/13/2017.
 */

public interface Taggable {

    void setDesc(String desc);
    String getDesc();

    void setId(Object id);
    Object getId();

    boolean isTagged(Tag tag);
    boolean isTagged(Object id);

    void addTagLabel(String tag);
    void removeTagLabel(String tag);
    String getTagLabel(int pos);
    List<String> getTagLabels();
//    void setTagLabels(List<String> tagLabels);

    void setTagLabels(List<String> tags);
}
