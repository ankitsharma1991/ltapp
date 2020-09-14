package com.accusterltapp.model;

import java.util.ArrayList;

public class QcDataList {
    ArrayList<QcData> list;
    public ArrayList<QcData> getList() {
        return list;
    }
    public void setList(ArrayList<QcData> list) {
        this.list = list;
    }
    public String getLtid() {
        return ltid;
    }
    public void setLtid(String ltid) {
        this.ltid = ltid;
    }
    String ltid;
}
