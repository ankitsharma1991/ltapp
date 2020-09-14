package com.accusterltapp.model;

import java.io.Serializable;

public class ReportsettingModel implements Serializable {
    public String camp_name;
    public boolean header;
    public boolean footer;

    public String getCamp_name() {
        return camp_name;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public boolean isFooter() {
        return footer;
    }

    public void setFooter(boolean footer) {
        this.footer = footer;
    }

    public void setCamp_name(String camp_name) {
        this.camp_name = camp_name;
    }

    public String getLogo_enable() {
        return logo_enable;
    }

    public void setLogo_enable(String logo_enable) {
        this.logo_enable = logo_enable;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String logo_enable;
    public String address;
}
