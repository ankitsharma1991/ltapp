package com.accusterltapp.model;

import java.util.ArrayList;

/**
 * Created by pbadmin on 2/11/17.
 *
 */

public class TestDetails {
    private String test_type_name;
    private String type_id;
    private boolean isChecked;
    private String package_code;
    private String package_name;


    public boolean isChecked() {
        return isChecked;
    }

    private ArrayList<SubTestDetails> test_list;


    public String getTest_name() {
        return test_type_name;
    }

    public String getType_id() {
        return type_id;
    }


    public ArrayList<SubTestDetails> getTest_list() {
        return test_list;
    }

    public void toggle() {
        isChecked = !isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public SubTestDetails getChildItem(int index) {
       if (index>=test_list.size())
           return test_list.get(test_list.size()-1);
       else
        return test_list.get(index);
    }

   public int getChildrenCount(){
       return test_list.size();
   }

    public String getTest_type_name() {
        return test_type_name;
    }

    public void setTest_type_name(String test_type_name) {
        this.test_type_name = test_type_name;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public void setTest_list(ArrayList<SubTestDetails> test_list) {
        this.test_list = test_list;
    }

    public String getPackage_code() {
        return package_code;
    }

    public void setPackage_code(String package_code) {
        this.package_code = package_code;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    @Override
    public String toString() {
        return package_name;
    }
}
