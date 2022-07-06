package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping({"/posts", "/posts/{id}"})
    public List<Post> findAll(@PathVariable(required = false) String id,
                              @RequestParam(defaultValue = "asc") String sort,
                              @RequestParam(defaultValue = "100") int size,
                              @RequestParam(defaultValue = "1") int page) {
        if (id == null) {
            return postService.findAll(sort, size, page);
        } else {
           return List.of(postService.findPostById(id));
        }
    }

    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }
}