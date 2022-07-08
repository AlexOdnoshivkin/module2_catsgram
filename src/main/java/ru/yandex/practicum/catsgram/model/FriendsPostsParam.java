package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;


public class FriendsPostsParam {
    private final String sort;
    private final int size;

    public String getSort() {
        return sort;
    }

    public int getSize() {
        return size;
    }

    public List<String> getFriends() {
        return friends;
    }

    private final List<String> friends;

    public FriendsPostsParam(String sort, int size, List<String> friends) {
        this.sort = sort;
        this.size = size;
        this.friends = friends;
    }
}
