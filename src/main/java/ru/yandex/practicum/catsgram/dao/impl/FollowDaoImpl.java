package ru.yandex.practicum.catsgram.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dao.FollowDao;
import ru.yandex.practicum.catsgram.dao.PostDao;
import ru.yandex.practicum.catsgram.dao.UserDao;
import ru.yandex.practicum.catsgram.model.Follow;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FollowDaoImpl implements FollowDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;
    private final PostDao postDao;

    public FollowDaoImpl(JdbcTemplate jdbcTemplate, UserDao userDao, PostDao postDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
        this.postDao = postDao;
    }

    @Override
    public List<Post> getFollowFeed(String userId, int max) {
        // получаем все подписки пользователя
        String sql = "select * from cat_follow where id_follower = ?"; // напишите подходящий SQL-запрос
        List<Follow> follows = jdbcTemplate.query(sql, (rs, rowNum) -> makeFollow(rs), userId);

        // выгружаем авторов, на которых подписан пользователь
        Set<User> authors = follows.stream()
                .map(Follow::getId_user)
                .map(id -> userDao.findUserById(id).get())
                .collect(Collectors.toSet());


        // выгрузите и отсортируйте посты полученных выше авторов
        // не забудьте предусмотреть ограничение выдачи
        return authors.stream()
                .map(postDao::findPostsByUser)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Post::getCreationDate).reversed())
                .limit(max)
                .collect(Collectors.toList());
    }

    private Follow makeFollow(ResultSet rs) throws SQLException {
        // реализуйте маппинг результата запроса в объект класса Follow
        String id_user = rs.getString("id_user");
        String id_follower = rs.getString("id_follower");
        return new Follow(id_user, id_follower);
    }
}