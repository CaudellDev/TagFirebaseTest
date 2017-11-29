package com.caudelldevelopment.test.tagfirebasetest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.caudelldevelopment.test.tags.UI.TagChip;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.Chip;

import Data.BaseItem;

/**
 * Created by caude on 11/13/2017.
 */

public class NewItemDialog extends DialogFragment {

    public static final String TAG = "NEW_ITEM_DIALOG";

    private EditText itemName;
    private ChipsInput itemTags;

    public static NewItemDialog newInstance(BaseItem item) {

        NewItemDialog fragment = new NewItemDialog();

        Bundle args = new Bundle();

        if (item != null) {
            args.putString("item_desc", item.getDesc());
            String[] array = new String[item.getTagLabels().size()];
            args.putStringArray("item_tags", item.getTagLabels().toArray(array));
        }

        fragment.setArguments(args);

        return fragment;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.new_item_dialog, container, false);
//
//        itemName = root.findViewById(R.id.dialog_item_name_edit);
//        itemTags = root.findViewById(R.id.dialog_item_tag_input);
//
//        if (!getArguments().isEmpty()) {
//            Bundle args = getArguments();
//            String desc = args.getString("item_desc");
//            String[] tags = args.getStringArray("item_tags");
//
//            itemName.setText(desc);
//            for (int i = 0; i < tags.length; i++) {
//                itemTags.addChip(new TagChip(tags[i]));
//            }
//        }
//
//        return root;
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext())
                        .setTitle("Add Item")
                        .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Save Item and return it.
                            }
                        })
                        .setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.new_item_dialog, null);

        itemName = root.findViewById(R.id.dialog_item_name_edit);
        itemTags = root.findViewById(R.id.dialog_item_tag_input);

        if (!getArguments().isEmpty()) {
            Bundle args = getArguments();
            String desc = args.getString("item_desc");
            String[] tags = args.getStringArray("item_tags");

            itemName.setText(desc);
            for (int i = 0; i < tags.length; i++) {
                itemTags.addChip(new TagChip(tags[i]));
            }
        }

        return builder.create();
    }
}
