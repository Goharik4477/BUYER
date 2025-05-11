package com.example.buyer.BUYER.b.Chat.model;

public class CallList {
    private String userId;
    private String userName;
    private String callType;
    private String date;
    private String urlProfile;

    public CallList() {
    }

    public CallList(String userId, String userName, String callType, String date, String urlProfile) {
        this.userId = userId;
        this.userName = userName;
        this.callType = callType;
        this.date = date;
        this.urlProfile = urlProfile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrlProfile() {
        return urlProfile;
    }

    public void setUrlProfile(String urlProfile) {
        this.urlProfile = urlProfile;
    }
}
