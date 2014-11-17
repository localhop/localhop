package com.localhop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class Adapter_FriendList extends ArrayAdapter<Item_Friend> {

    private final Context context;
    private final ArrayList<Item_Friend> itemsArrayList;
    private String sEventNameSpacing = "    ";

    public Adapter_FriendList(Context context, ArrayList<Item_Friend> itemsArrayList) {

        super(context, R.layout.item_friend, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Get rowView from inflater
        View rowView = inflater.inflate(R.layout.item_friend, parent, false);

        // Get the UI components
        TextView tvFriendName = (TextView) rowView.findViewById(R.id.friend_list_item_friend_name);

        // Set the UI components
        tvFriendName.setText(itemsArrayList.get(position).getsName());

        // Return the item
        return rowView;
    }
}