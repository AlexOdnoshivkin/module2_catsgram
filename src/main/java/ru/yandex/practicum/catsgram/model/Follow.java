package ru.yandex.practicum.catsgram.model;

public class Follow {
    private String id_user;
    private String id_follower;

    public Follow(String id_user, String id_follower) {
        this.id_user = id_user;
        this.id_follower = id_follower;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_follower() {
        return id_follower;
    }

    public void setId_follower(String id_follower) {
        this.id_follower = id_follower;
    }
}
