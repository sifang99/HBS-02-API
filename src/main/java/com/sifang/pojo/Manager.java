package com.sifang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager {
    private int id;
    private String num;
    private String name;
    private String tel;
    private int gender;
    private Date birth;
}
