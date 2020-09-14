package com.accusterltapp.model;

public class ApprovedReportData {
    String id;
    String uid ;
    String  camp;
    String   name;
    String camp_code;

    public String getCamp_code() {
        return camp_code;
    }

    public void setCamp_code(String camp_code) {
        this.camp_code = camp_code;
    }

    public int  test_manual_status;
    public void setTest_manual_status(int test_manual_status) {
        this.test_manual_status = test_manual_status;
    }

    public boolean is_manual_status() {
        return test_manual_status == 1;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCamp() {
        return camp;
    }

    public void setCamp(String camp) {
        this.camp = camp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    String patientCode;
    String contact;
    String  age;
    String  gender;
    String  email;
    String  test;
    String amount;
    String created;
    String report_status;

    public int getTest_manual_status() {
        return test_manual_status;
    }

    public String getReport_status() {
        return report_status;
    }

    public void setReport_status(String report_status) {
        this.report_status = report_status;
    }

    public ApprovedReportData(String id, String uid, String camp, String name, int test_manual_status, String patientCode, String contact, String age, String gender, String email, String test, String amount, String created,String report_status,String camp_code) {
        this.id = id;
        this.uid = uid;
        this.camp = camp;
        this.name = name;
        this.test_manual_status = test_manual_status;
        this.patientCode = patientCode;
        this.contact = contact;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.test = test;
        this.amount = amount;
        this.created = created;
        this.report_status=report_status;
        this.camp_code=camp_code;
    }
}
