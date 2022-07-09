package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.FriendsPostsParam;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostService {
    private final UserService userService;
    private final Map<Integer,Post> posts = new HashMap<>();
    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll(String sort, int size, int page) {
        if (sort.equals("asc")) {
            return posts.values().stream()
                    .sorted((Post p1, Post p2) -> (int) (p1.getCreationDate().toEpochMilli() - p2.getCreationDate().toEpochMilli()))
                    .skip((long) size * (page - 1))
                    .limit(size)
                    .collect(Collectors.toList());

        } else {
            return posts.values().stream()
                    .sorted((Post p1, Post p2) -> (int) (p2.getCreationDate().toEpochMilli() - p1.getCreationDate().toEpochMilli()))
                    .skip((long) size * (page - 1))
                    .limit(size)
                    .collect(Collectors.toList());
        }
    }

    public List<Post> findFriendsPosts(FriendsPostsParam param) {
        if (param.getSort().equals("asc")) {
            return posts.values().stream()
                    .filter((Post p) -> {
                        boolean existOnList = false;
                        for(String friend: param.getFriends()) {
                            if(friend.equals(p.getAuthor())){
                                existOnList = true;
                            }
                        }
                        return existOnList;
                    })
                    .sorted((p1, p2) -> p1.getCreationDate().compareTo(p2.getCreationDate()))
                    .limit(param.getSize())
                    .collect(Collectors.toList());
        } else {
            return posts.values().stream()
                    .filter((Post p) -> {
                        boolean existOnList = false;
                        for(String friend: param.getFriends()) {
                            if(friend.equals(p.getAuthor())){
                                existOnList = true;
                            }
                        }
                        return existOnList;
                    })
                    .sorted((p1, p2) -> p2.getCreationDate().compareTo(p1.getCreationDate()))
                    .limit(param.getSize())
                    .collect(Collectors.toList());
        }
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