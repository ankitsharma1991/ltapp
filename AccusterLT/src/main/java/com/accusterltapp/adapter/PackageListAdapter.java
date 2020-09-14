package com.accusterltapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.accusterltapp.model.SubTestDetails;
import com.accusterltapp.model.TestDetails;
import com.accusterltapp.R;

import java.util.ArrayList;

/**
 * Created by LoB Android on 16/12/17.
 */

public class PackageListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<TestDetails> mPackageList;

    public PackageListAdapter(Context context, ArrayList<TestDetails> packageList) {
        this.context = context;
        mPackageList = packageList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mPackageList.get(groupPosition).getTest_list().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SubTestDetails child = mPackageList.get(groupPosition).getChildItem(childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = convertView.findViewById(R.id.expandedListItem);
        expandedListTextView.setText(child.getTest_name());

        TextView tvPrice = convertView.findViewById(R.id.test_charges);
        String mess = context.getResources().getString(R.string.Rs);
        tvPrice.setText(mess + " " + com.base.utility.StringUtils.doubleToIndianFormat(child.getTest_price()));

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mPackageList.get(groupPosition).getTest_list().size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return mPackageList.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return mPackageList.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TestDetails mGroup = (TestDetails) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(mGroup.getPackage_name());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
