package com.sifang.mapper;

import com.sifang.pojo.WorkerLogin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WorkerLoginMapper {
    int addWorker(WorkerLogin workerLogin);
    int updatePwd(String num, String pwd);
}
