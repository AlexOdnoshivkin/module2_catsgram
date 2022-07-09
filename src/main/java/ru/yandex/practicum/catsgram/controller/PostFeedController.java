package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.FriendsPostsParam;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;
@Slf4j
@RestController
public class PostFeedController {
  private final PostService postService;

  @Autowired
  public PostFeedController(PostService postService) {
    this.postService = postService;
  }

  @PostMapping(value = "/feed/friends")
  public List<Post> postFriendsPosts(@RequestBody String body) throws JsonProcessingException {
    log.debug("Получен запрос на просмотр последних постов друзе {}", body);
    ObjectMapper objectMapper = new ObjectMapper();
    FriendsPostsParam friendsPostsParam;
    try {
        String stringFriendsPostParam = objectMapper.readValue(body, String.class);
        friendsPostsParam = objectMapper.readValue(stringFriendsPostParam, FriendsPostsParam.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Ошибка конвертации данных, проверьте корректность json", e);
    }

    return postService.findFriendsPosts(friendsPostsParam);
  }
}
