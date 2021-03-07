package com.accusterltapp.model;

import android.text.TextUtils;

/**
 * Created by pbadmin on 2/11/17.
 */

public class SubTestDetails {
    private  String im_p;

    public String getIm_p() {
        return im_p;
    }

    public void setIm_p(String im_p) {
        this.im_p = im_p;
    }
private  boolean img_pri;

    public boolean isImg_pri() {
        return img_pri;
    }

    public void setImg_pri(boolean img_pri) {
        this.img_pri = img_pri;
    }

    private String test_upper_bound_male;
    private String test_upper_bound_female;
    private String test_interpretation;
    private String test_precautions;
    private String test_low_bound_female;
    private String test_low_bound_male;
    private String test_price;
    private String test_code;
    private String report_id;
    private String test_name;
    private String image_permission;


    public String getImage_permission() {
        return image_permission;
    }

    public void setImage_permission(String image_permission) {
        this.image_permission = image_permission;
    }

    public boolean isImagepre() {
        return isImagepre;
    }

    public void setImagepre(boolean imagepre) {
        isImagepre = imagepre;
    }

    private String test_result;
    private int  test_manual_status;
    private String patientId;
    private String test_unit;
    private String test_range;
    private boolean isChecked;
    private boolean isImagepre;

    public String getTest_interpretation() {
        return test_interpretation;
    }

    public void setTest_interpretation(String test_interpretation) {
        this.test_interpretation = test_interpretation;
    }

    public String getTest_precautions() {
        return test_precautions;
    }

    public void setTest_precautions(String test_precautions) {
        this.test_precautions = test_precautions;
    }

    private String imagePath;
    private String packageName;
    public String syn_status;
    public String cam_code;
    public String test_id;
    public String test_type_name;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public double getTest_price() {
        try {
            return TextUtils.isEmpty(test_price) ? 0 : Double.parseDouble(test_price);
        } catch (Exception e) {
            return 0;
        }

    }

    public String getTest_code() {
        return TextUtils.isEmpty(test_code) ? "" : test_code;
    }


    public String getTestReportId() {
        return TextUtils.isEmpty(report_id) ? "" : report_id;
    }
    public String getTest_name() {
        return test_name;
    }

    public String getTest_id() {
        return test_id;
    }

    public String getTest_unit() {
        return test_unit;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void toggle() {
        isChecked = !isChecked;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setTest_price(String test_price) {
        this.test_price = test_price;
    }

    public void setTest_code(String test_code) {
        this.test_code = test_code;
    }

    public void setTestReportId(String reportid) {
        this.report_id = reportid;
    }
    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public void setTest_unit(String test_unit) {
        this.test_unit = test_unit;
    }

    public String getTest_result() {
        return TextUtils.isEmpty(test_result) ? "" : test_result;
    }

    public void setTest_result(String test_result) {
        this.test_result = test_result;
    }

    public int isTest_manual_status() {
        return test_manual_status;
    }

    public void setTest_manual_status(int test_manual_status) {
        this.test_manual_status = test_manual_status;
    }

    public boolean is_manual_status() {
        return test_manual_status == 1;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }



    public String getTest_range() {
        return test_range;
    }

    public void setTest_range(String test_range) {
        this.test_range = test_range;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSyn_status() {
        return syn_status;
    }

    public void setSyn_status(String syn_status) {
        this.syn_status = syn_status;
    }

    public String getTest_upper_bound_male() {
        return test_upper_bound_male;
    }

    public void setTest_upper_bound_male(String test_upper_bound_male) {
        this.test_upper_bound_male = test_upper_bound_male;
    }

    public String getTest_upper_bound_female() {
        return test_upper_bound_female;
    }

    public void setTest_upper_bound_female(String test_upper_bound_female) {
        this.test_upper_bound_female = test_upper_bound_female;
    }

    public String getTest_low_bound_female() {
        return test_low_bound_female;
    }

    public void setTest_low_bound_female(String test_low_bound_female) {
        this.test_low_bound_female = test_low_bound_female;
    }

    public String getTest_low_bound_male() {
        return test_low_bound_male;
    }

    public void setTest_low_bound_male(String test_low_bound_male) {
        this.test_low_bound_male = test_low_bound_male;
    }

    public int getTest_manual_status() {
        return test_manual_status;
    }

    public String getCam_code() {
        return cam_code;
    }

    public void setCam_code(String cam_code) {
        this.cam_code = cam_code;
    }

    public String getTest_type_name() {
        return test_type_name;
    }

    public void setTest_type_name(String test_type_name) {
        this.test_type_name = test_type_name;
    }
}
