package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Data
public class FriendsPostsParam {
    private String sort;
    private int size;
    private List<String> friends;
}
