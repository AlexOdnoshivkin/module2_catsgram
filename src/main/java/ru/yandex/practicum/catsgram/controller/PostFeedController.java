package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.exceptions.IncorrectParameterException;
import ru.yandex.practicum.catsgram.model.FeedParams;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.catsgram.Constants.SORTS;

@RestController()
@RequestMapping("/feed/friends")
public class PostFeedController {

    private final PostService postService;

    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    List<Post> getFriendsFeed(@RequestBody FeedParams feedParams) {
        if (!SORTS.contains(feedParams.getSort())) {
            throw new IncorrectParameterException("sort", "Указан неферный формат сортировки");
        }
        if (feedParams.getFriendsEmails().isEmpty()) {
            throw new IncorrectParameterException("friendsEmails", "Список друзей не может быть пустым");
        }
        if (feedParams.getSize() == null || feedParams.getSize() <= 0) {
            throw new IncorrectParameterException("size", "Параметр size должен быть больше нуля");
        }
        List<Post> result = new ArrayList<>();
        for (String friendEmail : feedParams.getFriendsEmails()) {
            result.addAll(postService.findPostsByUser(friendEmail, feedParams.getSize(), feedParams.getSort()));
        }
        return result;
    }

    public Collection<Post> findPostsByUser(String authorId, Integer size, String sort) {
        return findPostsByUser(authorId)
                .stream()
                .sorted((p0, p1) -> {
                    int comp = p0.getCreationDate().compareTo(p1.getCreationDate()); //прямой порядок сортировки
                    if (sort.equals("desc")) {
                        comp = -1 * comp; //обратный порядок сортировки
                    }
                    return comp;
                })
                .limit(size)
                .collect(Collectors.toList());
    }
}
