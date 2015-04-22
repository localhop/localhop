package com.localhop.swipe.createevent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.localhop.R;
import com.localhop.objects.Friend;
import com.localhop.objects.Group;

import java.util.ArrayList;

/**
 * Adapter for displaying custom group/friend list items in the Create Event Invite tab
 */
public class AdapterExpandFriendGroupList extends BaseExpandableListAdapter {

    private final Context mContext;
    private final ArrayList<Group> mGroupItems;
    private final ArrayList<Friend> mFriendItems;

    public AdapterExpandFriendGroupList(Context context, ArrayList<Group> groups, ArrayList<Friend> friends) {

        this.mContext = context;
        this.mGroupItems = groups;
        this.mFriendItems = friends;
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if (groupPosition == 0 && mGroupItems != null)
        {
            return mGroupItems.size();
        }
        else if (groupPosition == 1 && mFriendItems != null)
        {
            return mFriendItems.size();
        }
        else return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        if (groupPosition == 0 && mGroupItems != null)
        {
            return mGroupItems.get(childPosition);
        }
        else if (groupPosition == 1 && mFriendItems != null)
        {
            return mFriendItems.get(childPosition);
        }
        else return 0;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // Create inflater
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Get rowView from inflater
        View rowView = inflater.inflate(R.layout.item_expand_header_create_event_invite, parent, false);

        // Set group header text
        TextView tvHeader = (TextView) rowView.findViewById(R.id.tv_item_expand_header_create_event_invite);

        if(groupPosition == 0)
        {
            tvHeader.setText(R.string.groups);
        }
        else
        {
            tvHeader.setText(R.string.friends);
        }

        return rowView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        // Create inflater
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = null;

        if( groupPosition == 0 && mGroupItems != null) {

            // Get rowView from inflater
            rowView = inflater.inflate(R.layout.item_group, parent, false);

            // Get the UI components
            TextView tvGroupName = (TextView) rowView.findViewById(R.id.group_list_item_group_name);
            TextView tvGroupList = (TextView) rowView.findViewById(R.id.group_list_item_group_members);

            // Set the UI components
            tvGroupName.setText(mGroupItems.get(childPosition).getsName());
            tvGroupList.setText(mGroupItems.get(childPosition).getsMembers());
        }
        else if( groupPosition == 1 && mFriendItems != null)
        {
            // Get rowView from inflater
            rowView = inflater.inflate(R.layout.item_friend, parent, false);

            // Get the UI components
            TextView tvFriendName = (TextView) rowView.findViewById(R.id.friend_list_item_friend_name);

            // Set the UI components
            tvFriendName.setText(mFriendItems.get(childPosition).getFullName());
        }

        // Return the item
        return rowView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}  // end of class AdapterGroupList.java