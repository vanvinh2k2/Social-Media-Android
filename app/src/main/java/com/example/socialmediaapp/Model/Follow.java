package com.example.socialmediaapp.Model;

public class Follow {
    private String followBy;
    private long followAt;

    public Follow() {
    }

    public String getFollowBy() {
        return followBy;
    }

    public void setFollowBy(String followBy) {
        this.followBy = followBy;
    }

    public long getFollowAt() {
        return followAt;
    }

    public void setFollowAt(long followAt) {
        this.followAt = followAt;
    }
}
