package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.FriendsPostsParam;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;

@RestController
public class PostFeedController {
  private final PostService postService;

  @Autowired
  public PostFeedController(PostService postService) {
    this.postService = postService;
  }

  @PostMapping(value = "/feed/friends")
  public List<Post> postFriendsPosts(@RequestBody String body) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    String stringFriendsPostParam = objectMapper.readValue(body, String.class);
    FriendsPostsParam friendsPostsParam = objectMapper.readValue(stringFriendsPostParam, FriendsPostsParam.class);
    return postService.findFriendsPosts(friendsPostsParam);
  }
}
