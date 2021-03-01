package com.sifang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    private int id;
    private String num;
    private String name;
    private Integer gender;//0：女性，1：男性
    private String tel;
    private String dept;
    private Date birth;
    private String position;
    private String speciality;
    private String introduction;
}
