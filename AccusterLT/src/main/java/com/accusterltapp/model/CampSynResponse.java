package com.accusterltapp.model;

/**
 * Created by LoB Android on 17/12/17.
 */

public class CampSynResponse {
    private String message;

    private String[] sync_camp;

    private String[] NonSync_camp;

    private String error;

    private String status;
    public String getMessage() {
        return message;
    }

    public String[] getSync_camp() {
        return sync_camp;
    }

    public String[] getNonSync_camp() {
        return NonSync_camp;
    }

    public String getError() {
        return error;
    }

    public String getStatus() {
        return status;
    }
}
