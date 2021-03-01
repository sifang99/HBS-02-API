package com.sifang.service.impl;

import com.sifang.mapper.DoctorMapper;
import com.sifang.mapper.WorkerLoginMapper;
import com.sifang.pojo.Doctor;
import com.sifang.pojo.ReturnMessage;
import com.sifang.pojo.WorkerLogin;
import com.sifang.service.DoctorService;
import com.sifang.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public ReturnMessage addDoctor(Doctor doctor) {
        ReturnMessage returnMessage = new ReturnMessage();
        //添加医生
        //判断医生编号是否唯一
        if (this.isNumExist(doctor.getNum())){
            returnMessage.setMessage("改编号已注册！");
            returnMessage.setIsSuccess(1);
            return returnMessage;
        }
        //如果改编号尚未注册，添加医生
        if (doctorMapper.addDoctor(doctor) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("添加医生成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("添加医生失败！");
        }
        return returnMessage;
    }

    @Override
    public boolean isNumExist(String num) {
        if (doctorMapper.isNumExist(num) != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public ReturnMessage updateDoctor(Doctor doctor) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (doctorMapper.updateDoctor(doctor) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("修改成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("修改失败！");
        }
        return returnMessage;
    }

    @Override
    public ReturnMessage deleteDoctorById(int id) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (doctorMapper.deleteDoctor(id) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("删除成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("删除失败！");
        }
        return returnMessage;
    }

}
