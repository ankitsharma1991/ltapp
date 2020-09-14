package com.accusterltapp.model;

/**
 * Created by LoB Android on 14/12/17.
 */

public class PathlogistDetails {
    private String userregistration_id;

    private String userregistration_complete_name;

    public String getUserregistration_id() {
        return userregistration_id;
    }

    public String getUserregistration_complete_name() {
        return userregistration_complete_name;
    }

    @Override
    public String toString() {
        return userregistration_complete_name;
    }
}
