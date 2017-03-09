package com.kaishengit.mapper;

import com.kaishengit.pojo.User;

import java.util.List;

/**
 * Created by sunny on 2017/1/16.
 */
public interface UserMapper {
    List<User> findAll();

    int findMaxAccount();

    void save(User user);

    void delById(Integer userId);

    User findById(Integer userId);

    void update(User user);

    User findByUserName(String userName);
}
