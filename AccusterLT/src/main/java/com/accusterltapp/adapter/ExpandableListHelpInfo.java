package com.accusterltapp.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by appideas-user2 on 6/7/17.
 */

public class ExpandableListHelpInfo {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> internaldata = new ArrayList<String>();
        internaldata.add("Lorem ipsum dolor");
        internaldata.add("Lorem ipsum dolor");
        internaldata.add("Lorem ipsum dolor");
        internaldata.add("Lorem ipsum dolor");
        internaldata.add("Lorem ipsum dolor");
        internaldata.add("Lorem ipsum dolor");

        expandableListDetail.put("Term & Condition", internaldata);
        expandableListDetail.put("FAQ", internaldata);
        expandableListDetail.put("How its works", internaldata);


        return expandableListDetail;
    }
}
