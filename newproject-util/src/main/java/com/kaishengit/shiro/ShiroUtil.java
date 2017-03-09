package com.kaishengit.shiro;

import com.kaishengit.pojo.User;
import org.apache.shiro.SecurityUtils;

/**
 * Created by sunny on 2017/2/18.
 */
public class ShiroUtil {
    public static User getCurrentUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    public static String getCurrentUserName() {
        return getCurrentUser().getUserName();
    }

    public static Integer getCurrentUserId() {
        return getCurrentUser().getId();
    }

    /**
     * 判断当前登录对象是否为管理员
     *
     * @return
     */
    public static boolean isSales() {
        return SecurityUtils.getSubject().hasRole("role_administrator");
    }
}
