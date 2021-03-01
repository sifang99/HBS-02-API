package com.sifang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientMessage {
    private int id;
    private String account;
    private String name;
    private String tel;
    private int gender;
    private Date birth;
    private String address;
    private int userId;
}
