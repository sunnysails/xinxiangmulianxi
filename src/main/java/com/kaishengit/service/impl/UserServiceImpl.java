package com.kaishengit.service.impl;

import com.kaishengit.mapper.RoleMapper;
import com.kaishengit.mapper.UserMapper;
import com.kaishengit.pojo.Role;
import com.kaishengit.pojo.User;
import com.kaishengit.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sunny on 2017/1/16.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Value("${password.salt}")
    private String salt;

    @Override
    public List<User> findAllUser() {
        return userMapper.findAll();
    }

    @Override
    public List<Role> findAllRole() {
        return roleMapper.findAll();
    }

    /**
     * 存储user 与 role 之间的外键约束关系
     *
     * @param user    当前User对象
     * @param roleIds 已选择的roleIds
     */
    private void addUserRole(User user, Integer[] roleIds) {
        if (roleIds != null) {
            for (Integer roleId :
                    roleIds) {
                Role role = roleMapper.findByRoleId(roleId);
                if (role != null) {
                    roleMapper.addNewUserRole(user.getId(), roleId);
                }
            }
        }
    }

    /**
     * 给密码加盐
     *
     * @param user 需要加盐的User对象
     */
    private void addSalt(User user) {
        if (user.getPassword() != null) {
            user.setPassword(DigestUtils.md5Hex(user.getPassword() + salt));
        }
    }

    @Override
    @Transactional
    public Integer saveNewUser(User user, Integer[] roleIds) {
        Integer newAccount = userMapper.findMaxAccount() + 1;
        user.setUserAccount(newAccount);
        addSalt(user);
        userMapper.save(user);
        addUserRole(user, roleIds);
        //TODO 概率性的账号重复，待添加检测,邮箱，电话唯一性未添加。
        return newAccount;
    }

    @Override
    @Transactional
    public void delUserById(Integer userId) {
        roleMapper.delUserRoleByUserId(userId);
        userMapper.delById(userId);
    }

    @Override
    public User findUserById(Integer userId) {
        return userMapper.findById(userId);
    }

    @Override
    @Transactional
    public void editUser(User user, Integer[] roleIds) {
        roleMapper.delUserRoleByUserId(user.getId());
        addUserRole(user, roleIds);
        addSalt(user);
        userMapper.update(user);
    }
}
