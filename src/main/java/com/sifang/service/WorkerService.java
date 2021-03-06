package com.sifang.service;

import com.sifang.pojo.ReturnMessage;
import com.sifang.pojo.WorkerLogin;

public interface WorkerService {
    ReturnMessage addWorker(WorkerLogin workerLogin);
    ReturnMessage updatePwd(String num, String pwd);
    ReturnMessage deleteWorker(String num);
    WorkerLogin getByNum(String num);
}
