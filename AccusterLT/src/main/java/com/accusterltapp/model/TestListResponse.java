package com.accusterltapp.model;

import java.util.ArrayList;

/**
 * Created by pbadmin on 2/11/17.
 */

public class TestListResponse {
    private String message;

    private String status;

    private ArrayList<TestDetails> test_list;
    private ArrayList<TestDetails> package_list;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<TestDetails> getTest_list() {
        return test_list;
    }

    public ArrayList<TestDetails> getPackage_list() {
        return package_list;
    }
}
