package com.kaishengit.service;

import com.kaishengit.pojo.Role;
import com.kaishengit.pojo.User;

import java.util.List;

/**
 * Created by sunny on 2017/1/16.
 */
public interface UserService {
    List<User> findAllUser();

    List<Role> findAllRole();

    Integer saveNewUser(User user, Integer[] roleIds);

    void delUserById(Integer userId);

    User findUserById(Integer userId);

    void editUser(User user, Integer[] roleIds);
}