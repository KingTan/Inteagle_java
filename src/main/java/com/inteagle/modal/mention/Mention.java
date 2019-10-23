package com.inteagle.modal.mention;

import java.util.Date;

public class Mention {
    private String mentionId;

    private String deviceId;

    private String deviceType;

    private String mentionText;

    private String mentionStatus;

    private String solvedId;

    private Date mentionTime;

    public String getMentionId() {
        return mentionId;
    }

    public void setMentionId(String mentionId) {
        this.mentionId = mentionId == null ? null : mentionId.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getMentionText() {
        return mentionText;
    }

    public void setMentionText(String mentionText) {
        this.mentionText = mentionText == null ? null : mentionText.trim();
    }

    public String getMentionStatus() {
        return mentionStatus;
    }

    public void setMentionStatus(String mentionStatus) {
        this.mentionStatus = mentionStatus == null ? null : mentionStatus.trim();
    }

    public String getSolvedId() {
        return solvedId;
    }

    public void setSolvedId(String solvedId) {
        this.solvedId = solvedId == null ? null : solvedId.trim();
    }

    public Date getMentionTime() {
        return mentionTime;
    }

    public void setMentionTime(Date mentionTime) {
        this.mentionTime = mentionTime;
    }
}