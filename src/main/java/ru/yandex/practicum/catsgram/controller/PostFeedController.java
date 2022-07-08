package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.FriendsPostsParam;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.List;

@RestController
public class PostFeedController {

  @PostMapping(value = "/feed/friends")
  public FriendsPostsParam postFriendsPosts(@RequestBody String body) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    FriendsPostsParam friendsPostsParam = objectMapper.readValue(body, FriendsPostsParam.class);
    /*String sort = friendsPostsParam.getSort();
    System.out.println(sort);
    int size = friendsPostsParam.getSize();
    System.out.println(size);
    List<String> friends = friendsPostsParam.getFriends();
    System.out.println(friends);*/
    return friendsPostsParam;
  }
}
