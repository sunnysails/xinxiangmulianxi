package com.kaishengit.pojo;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sunny on 2017/1/16.
 */
@Data
public class User implements Serializable {
    //用户状态:禁用
    public static final Integer USERSTATE_DISABLED = 0;
    //用户状态:正常
    public static final Integer USERSTATE_ACTIVE = 1;

    private Integer id;
    private Integer userAccount;
    private String userName;
    private String email;
    private String phone;
    private String password;
    private Integer state;
    private Timestamp createTime;
    private List<Role> roleList;

    public String getRoleNames() {
        List<String> viewNames = Lists.newArrayList(Collections2.transform(getRoleList(), new Function<Role, String>() {
            @Override
            public String apply(Role role) {
                return role.getViewName();
            }
        }));
        StringBuilder stringBuilder = new StringBuilder();
        for (String viewName :
                viewNames) {
            stringBuilder.append(viewName).append(" ");
        }
        return stringBuilder.toString();
    }
}