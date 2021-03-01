package com.sifang.service;

import com.sifang.pojo.Doctor;
import com.sifang.pojo.ReturnMessage;

public interface DoctorService {
    //添加医生
    ReturnMessage addDoctor(Doctor doctor);
    //判断医生编号是否唯一
    boolean isNumExist(String num);
    //修改医生
    ReturnMessage updateDoctor(Doctor doctor);
    //通过id删除某个医生
    ReturnMessage deleteDoctorById(int id);
}
