package com.base.model;

import java.util.ArrayList;

/**
 * Created by pbadmin on 27/8/17.
 */

public class CampList {
    private String status;

    private String message;
    private ArrayList<CampDetails> camp_list;

    public String getStatus() {
        return status;
    }

    public ArrayList<CampDetails> getCamplist() {
        return camp_list;
    }

    public String getMessage() {
        return message;
    }
}
