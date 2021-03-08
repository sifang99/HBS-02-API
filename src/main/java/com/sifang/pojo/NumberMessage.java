package com.sifang.pojo;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NumberMessage {
    private int id;
    private int dept;
    private String doctorNum;
    private int remain;
    public Date numberDate;
    private Time startTime;
    private Time endTime;
    private String place;
    private int fee;
    private int total;
    private int status;
    private int timeInterval;
}
