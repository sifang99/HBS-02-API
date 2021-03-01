package com.sifang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumberMessage {
    private int id;
    private String dept;
    private String doctorNum;
    private int number;
    private Date day;
    private Time startTime;
    private Time endTime;
    private String place;
    private int fee;
    private int total;
    private int status;
}
