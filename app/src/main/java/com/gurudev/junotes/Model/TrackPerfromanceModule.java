package com.gurudev.junotes.Model;

public class TrackPerfromanceModule {

    private String title,description,ct1,percetageText,commentText,statusText;

    public TrackPerfromanceModule(String title, String description, String ct1, String percetageText, String commentText, String statusText) {
        this.title = title;
        this.description = description;
        this.ct1 = ct1;
        this.percetageText = percetageText;
        this.commentText = commentText;
        this.statusText = statusText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCt1() {
        return ct1;
    }

    public void setCt1(String ct1) {
        this.ct1 = ct1;
    }


    public String getPercetageText() {
        return percetageText;
    }

    public void setPercetageText(String percetageText) {
        this.percetageText = percetageText;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
