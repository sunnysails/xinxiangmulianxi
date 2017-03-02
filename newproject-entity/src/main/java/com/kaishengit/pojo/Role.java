package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by sunny on 2017/1/16.
 */
@Data
public class Role implements Serializable {
    private Integer id;
    private String roleName;
    private String viewName;
}
