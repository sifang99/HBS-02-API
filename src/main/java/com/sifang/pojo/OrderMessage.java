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
    private int dept;
    private String detailTime;
    private int status;
    private int numSequence;
    private Date orderDate;
    private int numberId;
    private int userId;
}
