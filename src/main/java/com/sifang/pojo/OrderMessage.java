package com.sifang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMessage {
    private int id;
    private String patientID;
    private String doctorNum;
    private String dept;
    private Date day;
    private String time;
    private String place;
    private int fee;
    private int status;
    private int sequence;
}
