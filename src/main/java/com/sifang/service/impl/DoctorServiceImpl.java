package com.sifang.service.impl;

import com.sifang.mapper.DoctorMapper;
import com.sifang.mapper.WorkerLoginMapper;
import com.sifang.pojo.Doctor;
import com.sifang.pojo.ReturnMessage;
import com.sifang.pojo.WorkerLogin;
import com.sifang.service.DoctorService;
import com.sifang.service.NumberMessageService;
import com.sifang.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private NumberMessageService numberMessageService;


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
    public ReturnMessage deleteDoctorByNum(String num) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (doctorMapper.deleteDoctor(num) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("删除成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("删除失败！");
        }
        return returnMessage;
    }

    //根据科室查询医生信息，并判断该医生是否已经排班，然后返回未排班的医生信息
    @Override
    public List<Map<String, Object>> getDoctorsByDept(int dept) {
        LinkedList<Doctor> doctorList = this.doctorMapper.getDoctorsByDept(dept);
        List<Map<String, Object>> resultList = new ArrayList<>();
        int length = doctorList.size();

        //获得现在的日期
        Calendar calendar = Calendar.getInstance();
        String dateStr = String.valueOf(calendar.get(Calendar.YEAR));
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month >= 10){
            dateStr = dateStr + "-" + String.valueOf(month);
        }else {
            dateStr = dateStr + "-0" + String.valueOf(month);
        }

        int day = calendar.get(Calendar.DATE);
        if (day >= 10){
            dateStr = dateStr + "-" + String.valueOf(day);
        }else{
            dateStr = dateStr + "-0" + String.valueOf(day);
        }
        Date date = Date.valueOf(dateStr);

        //封装返回数据
        for (int i = 0; i < length; i++){
            Map<String, Object> doctor = new HashMap<>();
            doctor.put("num", doctorList.get(i).getNum());
            doctor.put("name", doctorList.get(i).getName());
            doctor.put("position", doctorList.get(i).getPosition());
            doctor.put("state",this.isArranged(doctorList.get(i).getNum(), date));
            resultList.add(doctor);
        }
        System.out.println(resultList);
        return resultList;
    }

    @Override
    public List<Doctor> getDoctorListByDept(int dept) {
        return this.doctorMapper.getDoctorsByDept(dept);
    }

    @Override
    public Doctor getDoctorByNum(String doctorNum) {
        return this.doctorMapper.getDoctorByNum(doctorNum);
    }

    //辅助函数
    //判断医生是否已经排班
    boolean isArranged(String doctorNum, Date date){
        if (this.numberMessageService.getNumberListByDocDate(doctorNum, date).size() != 0){
            return  true;
        }else{
            return false;
        }
    }

}
