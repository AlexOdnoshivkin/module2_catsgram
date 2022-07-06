package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {
    @Autowired
    private final UserService userService;
    public PostService(UserService userService) {
        this.userService = userService;
    }
    private final Map<Integer,Post> posts = new HashMap<>();

    public List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }

    public Post findPostById(String id) {
        Integer postId = Integer.parseInt(id);
        if (!posts.containsKey(postId)) {
            throw new PostNotFoundException("Пост с id " + postId + " не найден");
        }
        return posts.get(postId);
    }

    public Post create(Post post) {
        if (userService.findUserByEmail(post.getAuthor()) == null) {
            throw  new UserNotFoundException("Пользователь " + post.getAuthor() + " не найден");
        }
        posts.put(post.getId(), post);
        return post;
    }
}