package com.kaishengit.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RentDoc {
  private Integer id;
  private String sourceName;
  private String newName;
  private Timestamp createTime;
  private Integer rentId;
}
