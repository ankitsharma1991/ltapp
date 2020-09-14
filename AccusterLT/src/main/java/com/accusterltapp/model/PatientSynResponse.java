package com.accusterltapp.model;

/**
 * Created by LoB Android on 17/12/17.
 */

public class PatientSynResponse {
    private String message;

    private String[] NoneSync_patient;

    private String error;

    private String status;

    private String[] sync_patient;

    public String getMessage() {
        return message;
    }

    public String[] getNoneSync_patient() {
        return NoneSync_patient;
    }

    public String getError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public String[] getSync_patient() {
        return sync_patient;
    }
}
