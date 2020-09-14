package com.accusterltapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RepidTestReslt {
    @SerializedName("imageName")
    @Expose
  private   String imageName;
    @SerializedName("status")
    @Expose
     private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
