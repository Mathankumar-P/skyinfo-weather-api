package com.skyinfo.weatherforecast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErrorDTO {
    private Date timeStamp;
    private int status;
    private String path;
    private List<String> errors = new ArrayList<>();

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void addErrors(String message){
        this.errors.add(message);
    }
}
