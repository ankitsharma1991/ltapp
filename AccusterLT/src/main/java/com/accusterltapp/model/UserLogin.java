package com.accusterltapp.model;

import com.base.model.CampDetails;

import java.util.ArrayList;

/**
 * Created by Jyoti on 11/1/2017.
 */

public class UserLogin {
    private String message;
    private ArrayList<PathlogistDetails> pathlogist_list;
    private ArrayList<TestDetails> package_list;
    private String error;
    private UserDetails user_detial;
    private String status;
    private ArrayList<TestDetails> test_list;
    private ArrayList<CampDetails> camp_list;
    public String getMessage() {
        return message;
    }

    public ArrayList<PathlogistDetails> getPathlogist_list() {
        return pathlogist_list;
    }

    public String getError() {
        return error;
    }
    public UserDetails getUser_detial() {
        return user_detial;
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

    public ArrayList<CampDetails> getCamp_list() {
        return camp_list;
    }
}
