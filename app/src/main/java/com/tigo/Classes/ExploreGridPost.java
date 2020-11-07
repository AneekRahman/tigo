package com.tigo.Classes;

public class ExploreGridPost {

    private String userName, postContentUrl;
    private long userId, viewsCount;

    public ExploreGridPost(String userName, long userId, long viewsCount, String postContentUrl){

        this.userName = userName;
        this.userId = userId;
        this.viewsCount = viewsCount;
        this.postContentUrl = postContentUrl;

    }

    public String getUserName() {
        return userName;
    }

    public long getUserId() {
        return userId;
    }

    public long getViewsCount() {
        return viewsCount;
    }

    public String getPostContentUrl() {
        return postContentUrl;
    }
}
