package com.accusterltapp.adapter;

/*
 * Created by jdevani on 10-Jan-16.
 *
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accusterltapp.activity.EditTestActivity;
import com.accusterltapp.fragment.AddPackageFragemnt;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.model.SubTestDetails;
import com.accusterltapp.model.TestDetails;
import com.accusterltapp.R;
import com.base.utility.StringUtils;

import java.util.ArrayList;

import static com.base.utility.StringUtils.finalTotal;


public class EditTestAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnChildClickListener {
    private final Context context;
    private final ArrayList<TestDetails> groups;
    //private double finalTotal = 0;
    private boolean isFragment;
    private AddPackageFragemnt mFragment;
    private ArrayList<String> testid;

    public EditTestAdapter(Context context, ArrayList<TestDetails> groups) {
        this.context = context;
        this.groups = groups;
        Log.e("method ", "yes");
        //this.testid=testid;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getTest_list().get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {

        return groups.get(groupPosition).getTest_list().size();
    }

    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    public int getGroupCount() {
        return groups.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 設定 Group 資料
     */
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TestDetails mGroup = (TestDetails) getGroup(groupPosition);

        if (convertView == null) {

            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_layout, null);
        }

        TextView tv = convertView.findViewById(R.id.tvGroup);
        tv.setText(mGroup.getTest_name());

        CheckBox checkBox1 = convertView.findViewById(R.id.chbGroup);
        checkBox1.setChecked(mGroup.isChecked());
        checkBox1.setOnClickListener(new Group_CheckBox_Click(groupPosition));

        return convertView;
    }

    /**
     * 勾選 Group CheckBox 時，存 Group CheckBox 的狀態，以及改變 Child CheckBox 的狀態
     */
    public class Group_CheckBox_Click implements OnClickListener {
        private final int groupPosition;
        ArrayList<String> str = new ArrayList<>();

        Group_CheckBox_Click(int groupPosition) {
            this.groupPosition = groupPosition;
        }

        public void onClick(View v) {
            groups.get(groupPosition).toggle();
            boolean groupIsChecked = groups.get(groupPosition).isChecked();
            for (SubTestDetails details : groups.get(groupPosition).getTest_list()) {
                details.setChecked(groupIsChecked);
                if (groupIsChecked) {
                    finalTotal = finalTotal + details.getTest_price();
                    details.setPackageName(groups.get(groupPosition).getTest_name());
                    details.setTest_type_name(groups.get(groupPosition).getTest_type_name());
                    if (isFragment) {
                        mFragment.addTest(details);
                    } else {
                        ((EditTestActivity) context).addTest(details);
                    }
                } else {
                    finalTotal = finalTotal - details.getTest_price();
                    if (isFragment) {
                        mFragment.removeTest(details);
                    } else {
                        ((EditTestActivity) context).removeTest(details);
                    }
                }
            }
            if (!isFragment)
                ((EditTestActivity) context).setTotalAmount(finalTotal);
            notifyDataSetChanged();
        }
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SubTestDetails child = groups.get(groupPosition).getChildItem(childPosition);

        Log.e("method called", "yes");
//Log.e("the all test id", Heleprec.testId_list.toString());
        Log.e("Current id ", child.getImage_permission() + " image");
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_layout, null);
        }

        TextView tv = convertView.findViewById(R.id.tvChild);
        LinearLayout childItemContainer = convertView.findViewById(R.id.childItemContainer);
        tv.setText(child.getTest_name());
        Log.e("the ", child.getTest_type_name() + " sachin");
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        ArrayList t;


        String mess = context.getResources().getString(R.string.Rs);
        tvPrice.setText(mess + " " + com.base.utility.StringUtils.doubleToIndianFormat(child.getTest_price()));

        CheckBox checkBox = convertView.findViewById(R.id.chbChild);
        // Log.e("size is ", Heleprec.testId_list.size()+"");
        // Log.e("true or not ", Heleprec.testId_list.contains(child.getTest_code().toString())+"");
        if (context instanceof EditTestActivity)
            if (Heleprec.testId_list.contains(child.getTest_code())) {
                child.setChecked(true);
                //checkBox.setEnabled(false);
            }
        checkBox.setChecked(child.isChecked());


        checkBox.setOnClickListener(new Child_CheckBox_Click(groupPosition, childPosition));
        tv.setOnClickListener(new Child_CheckBox_Click(groupPosition, childPosition));

        return convertView;
    }

    /**
     * 勾選 Child CheckBox 時，存 Child CheckBox 的狀態
     */
    public class Child_CheckBox_Click implements OnClickListener {
        private final int groupPosition;
        private final int childPosition;

        Child_CheckBox_Click(int groupPosition, int childPosition) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        public void onClick(View v) {
            handleClick(childPosition, groupPosition);

        }
    }

    public void handleClick(int childPosition, int groupPosition) {
        SubTestDetails details = groups.get(groupPosition).getChildItem(childPosition);
        details.toggle();
        groups.get(groupPosition).setChecked(false);
        if (details.isChecked()) {
            finalTotal = finalTotal + details.getTest_price();
            details.setPackageName(groups.get(groupPosition).getTest_name());
            details.setTest_type_name(groups.get(groupPosition).getTest_type_name());
            if (isFragment) {
                mFragment.addTest(groups.get(groupPosition).getChildItem(childPosition));
            } else {
                ((EditTestActivity) context).addTest(details);
            }
        } else {
            finalTotal = finalTotal - details.getTest_price();
            if (isFragment) {
                mFragment.removeTest(details);
            } else {
                ((EditTestActivity) context).removeTest(details);
            }
        }
        if (!isFragment)
            ((EditTestActivity) context).setTotalAmount(finalTotal);
        notifyDataSetChanged();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        //handleClick(childPosition, groupPosition);
        return false;
    }

    public void setTypeValue(AddPackageFragemnt baseFragment) {
        isFragment = true;
        mFragment = baseFragment;
    }
}
