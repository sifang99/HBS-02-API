package com.sifang.service.impl;

import com.sifang.mapper.WorkerLoginMapper;
import com.sifang.pojo.ReturnMessage;
import com.sifang.pojo.WorkerLogin;
import com.sifang.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    private WorkerLoginMapper workerLoginMapper;


    @Override
    public ReturnMessage addWorker(WorkerLogin workerLogin) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (workerLoginMapper.addWorker(workerLogin) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("添加成功");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("添加失败");
        }
        return returnMessage;
    }

    @Override
    public ReturnMessage updatePwd(String num, String pwd) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (workerLoginMapper.updatePwd(num, pwd) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("修改成功");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("修改失败");
        }
        return returnMessage;
    }
}
