package com.sifang.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkerLogin {
    private int id;
    private String num;
    private String pwd;
}
