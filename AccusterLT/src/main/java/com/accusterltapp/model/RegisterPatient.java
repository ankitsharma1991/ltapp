package com.accusterltapp.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by appideas-user2 on 19/7/17.
 */

public class RegisterPatient implements Parcelable {
    private int id;
    private String  userregistration_bp;

    public String getUserregistration_bp() {
        return userregistration_bp;
    }

    public void setUserregistration_bp(String userregistration_bp) {
        this.userregistration_bp = userregistration_bp;
    }

    public String getUserregistration_bmi() {
        return userregistration_bmi;
    }

    public void setUserregistration_bmi(String userregistration_bmi) {
        this.userregistration_bmi = userregistration_bmi;
    }

    private  String  userregistration_bmi;
    private String userregistration_code;
    private String userregistration_complete_name;
    private String userregistration_age;
    private String userregistration_gender_id;
    private String userregistration_mobile_number;
    private String userregistration_address_line_1;
    private String userregistration_email_address;
    private String userregistration_Id_type;
    private String userregistration_Id_no;
    private String userregistration_history_type;
    private String userregistration_history_type_detail;
    private String userregistration_diet;
    private String pLabelId;
    private String userregistration_org_id;
    private String userregistration_Lt_id;
    private String camp_name;
    private String userregistration_camp_code;
    public String userregistration_sync_id;
    private String userregistration_created_time;
    private String date;;
    public String getUserregistration_created_time() {
        return userregistration_created_time;
    }
    public void setUserregistration_created_time(String userregistration_created_time) {
        this.userregistration_created_time = userregistration_created_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserregistration_Id_no() {
        return userregistration_Id_no;
    }

    public void setUserregistration_Id_no(String userregistration_Id_no) {
        this.userregistration_Id_no = userregistration_Id_no;
    }

    public String getUserregistration_complete_name() {
        return userregistration_complete_name;
    }

    public void setUserregistration_complete_name(String userregistration_complete_name) {
        this.userregistration_complete_name = userregistration_complete_name;
    }

    public String getUserregistration_mobile_number() {
        return userregistration_mobile_number;
    }

    public void setUserregistration_mobile_number(String userregistration_mobile_number) {
        this.userregistration_mobile_number = userregistration_mobile_number;
    }

    public String getUserregistration_age() {
        return userregistration_age;
    }

    public void setUserregistration_age(String userregistration_age) {
        this.userregistration_age = userregistration_age;
    }

    public String getUserregistration_gender_id() {
        return userregistration_gender_id;
    }

    public void setUserregistration_gender_id(String userregistration_gender_id) {
        this.userregistration_gender_id = userregistration_gender_id;
    }

    public String getUserregistration_Id_type() {
        return userregistration_Id_type;
    }

    public void setUserregistration_Id_type(String userregistration_Id_type) {
        this.userregistration_Id_type = userregistration_Id_type;
    }

    public String getUserregistration_address_line_1() {
        return userregistration_address_line_1;
    }

    public void setUserregistration_address_line_1(String userregistration_address_line_1) {
        this.userregistration_address_line_1 = userregistration_address_line_1;
    }

    public String getUserregistration_diet() {
        return userregistration_diet;
    }

    public void setUserregistration_diet(String userregistration_diet) {
        this.userregistration_diet = userregistration_diet;
    }

    public String getpLabelId() {
        return pLabelId;
    }

    public void setpLabelId(String pLabelId) {
        this.pLabelId = pLabelId;
    }

    public String getUserregistration_email_address() {
        return userregistration_email_address;
    }

    public void setUserregistration_email_address(String userregistration_email_address) {
        this.userregistration_email_address = userregistration_email_address;
    }

    public String getUserregistration_code() {
        return userregistration_code;
    }

    public void setUserregistration_code(String userregistration_code) {
        this.userregistration_code = userregistration_code;
    }

    public String getUserregistration_history_type() {
        return userregistration_history_type;
    }

    public void setUserregistration_history_type(String userregistration_history_type) {
        this.userregistration_history_type = userregistration_history_type;
    }
    public String getUserregistration_history_type_detail() {
        return userregistration_history_type_detail;
    }

    public void setUserregistration_history_type_detail(String userregistration_history_type_detail) {
        this.userregistration_history_type_detail = userregistration_history_type_detail;
    }

    public String getUserregistration_org_id() {
        return userregistration_org_id;
    }

    public void setUserregistration_org_id(String userregistration_org_id) {
        this.userregistration_org_id = userregistration_org_id;
    }

    public String getUserregistration_Lt_id() {
        return userregistration_Lt_id;
    }

    public void setUserregistration_Lt_id(String userregistration_Lt_id) {
        this.userregistration_Lt_id = userregistration_Lt_id;
    }

    public String getCampName() {
        return camp_name;
    }

    public void setCampName(String camp_name) {
        this.camp_name = camp_name;
    }

    public String getUserregistration_camp_code() {
        return userregistration_camp_code;
    }

    public void setUserregistration_camp_code(String userregistration_camp_code) {
        this.userregistration_camp_code = userregistration_camp_code;
    }

    public String getUserregistration_sync_id() {
        return userregistration_sync_id;
    }

    public void setUserregistration_sync_id(String userregistration_sync_id) {
        this.userregistration_sync_id = userregistration_sync_id;
    }

    public RegisterPatient() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this. userregistration_bmi);
        dest.writeString(this.userregistration_bp);
        dest.writeString(this.userregistration_code);
        dest.writeString(this.userregistration_complete_name);
        dest.writeString(this.userregistration_age);
        dest.writeString(this.userregistration_gender_id);
        dest.writeString(this.userregistration_mobile_number);
        dest.writeString(this.userregistration_address_line_1);
        dest.writeString(this.userregistration_email_address);
        dest.writeString(this.userregistration_Id_type);
        dest.writeString(this.userregistration_Id_no);
        dest.writeString(this.userregistration_history_type);
        dest.writeString(this.userregistration_history_type_detail);
        dest.writeString(this.userregistration_diet);
        dest.writeString(this.pLabelId);
        dest.writeString(this.userregistration_org_id);
        dest.writeString(this.userregistration_Lt_id);
        dest.writeString(this.camp_name);
        dest.writeString(this.userregistration_camp_code);
        dest.writeString(this.userregistration_sync_id);
        dest.writeString(this.userregistration_created_time);
        dest.writeString(this.date);
    }

    protected RegisterPatient(Parcel in) {
        this.id = in.readInt();
        this.userregistration_bmi=in.readString();
        this. userregistration_bp=in.readString();
        this.userregistration_code = in.readString();
        this.userregistration_complete_name = in.readString();
        this.userregistration_age = in.readString();
        this.userregistration_gender_id = in.readString();
        this.userregistration_mobile_number = in.readString();
        this.userregistration_address_line_1 = in.readString();
        this.userregistration_email_address = in.readString();
        this.userregistration_Id_type = in.readString();
        this.userregistration_Id_no = in.readString();
        this.userregistration_history_type = in.readString();
        this.userregistration_history_type_detail = in.readString();
        this.userregistration_diet = in.readString();
        this.pLabelId = in.readString();
        this.userregistration_org_id = in.readString();
        this.userregistration_Lt_id = in.readString();
        this.camp_name = in.readString();
        this.userregistration_camp_code = in.readString();
        this.userregistration_sync_id = in.readString();
        this.userregistration_created_time = in.readString();
        this.date = in.readString();
    }

    public static final Creator<RegisterPatient> CREATOR = new Creator<RegisterPatient>() {
        @Override
        public RegisterPatient createFromParcel(Parcel source) {
            return new RegisterPatient(source);
        }

        @Override
        public RegisterPatient[] newArray(int size) {
            return new RegisterPatient[size];
        }
    };
}
