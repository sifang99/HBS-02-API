package com.sifang.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SysToken {
    private int userId;
    private LocalDateTime expireTime;
    private LocalDateTime updateTime;
    private String token;
}
