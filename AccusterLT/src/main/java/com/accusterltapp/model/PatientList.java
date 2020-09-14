package com.accusterltapp.model;

/**
 * Created by Jyoti on 11/2/2017.
 */

public class PatientList {
    private String userregistration_id;

    private String userregistration_date_of_birth;

    private String age;

    private String userregistration_complete_name;

    private String gender;

    private String userregistration_gender_id;

    public String getUserregistration_id() {
        return userregistration_id;
    }

    public void setUserregistration_id(String userregistration_id) {
        this.userregistration_id = userregistration_id;
    }

    public String getUserregistration_date_of_birth() {
        return userregistration_date_of_birth;
    }

    public void setUserregistration_date_of_birth(String userregistration_date_of_birth) {
        this.userregistration_date_of_birth = userregistration_date_of_birth;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUserregistration_complete_name() {
        return userregistration_complete_name;
    }

    public void setUserregistration_complete_name(String userregistration_complete_name) {
        this.userregistration_complete_name = userregistration_complete_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserregistration_gender_id() {
        return userregistration_gender_id;
    }

    public void setUserregistration_gender_id(String userregistration_gender_id) {
        this.userregistration_gender_id = userregistration_gender_id;
    }

    public PatientList(String userregistration_id, String userregistration_date_of_birth, String age, String userregistration_complete_name, String gender, String userregistration_gender_id) {
        this.userregistration_id = userregistration_id;
        this.userregistration_date_of_birth = userregistration_date_of_birth;
        this.age = age;
        this.userregistration_complete_name = userregistration_complete_name;
        this.gender = gender;
        this.userregistration_gender_id = userregistration_gender_id;
    }
}
