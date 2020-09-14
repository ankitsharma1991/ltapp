package com.accusterltapp.model;

import java.util.ArrayList;

public class AppointMentDatalist {
      int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<AppointMentData> getAppointmentData() {
        return appointmentData;
    }

    public void setAppointmentData(ArrayList<AppointMentData> appointmentData) {
        this.appointmentData = appointmentData;
    }

    ArrayList<AppointMentData> appointmentData;

}
