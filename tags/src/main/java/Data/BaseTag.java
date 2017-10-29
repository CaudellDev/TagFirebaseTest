package Data;

import java.util.List;

/**
 * Created by caude on 10/19/2017.
 */

public class BaseTag implements Tag {



    @Override
    public void setLabel(String label) {

    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public void setId(Object id) {

    }

    @Override
    public Object getId() {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public void addTagId(Object id) {

    }

    @Override
    public void removeTagId(Object id) {

    }

    @Override
    public List<Object> getTagIds() {
        return null;
    }

    @Override
    public Object getTagId(int pos) {
        return null;
    }

    @Override
    public void setTagIds(List<Object> ids) {

    }

    @Override
    public void removeIds(List<Object> ids) {

    }

    @Override
    public void removeAllTagIds() {

    }

    @Override
    public void addTaggedItem(Taggable item) {

    }

    @Override
    public void removeTaggedItem(Taggable item) {

    }

    @Override
    public List<Taggable> getItems() {
        return null;
    }

    @Override
    public Taggable getItem(int pos) {
        return null;
    }

    @Override
    public void setItems(List<Taggable> items) {

    }

    @Override
    public void removeItems(List<Taggable> items) {

    }

    @Override
    public void removeAllItems() {

    }
}
