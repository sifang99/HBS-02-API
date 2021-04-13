package com.sifang.controller;

import com.sifang.pojo.Doctor;
import com.sifang.pojo.NumberMessage;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.NumberMessageService;
import com.sifang.service.OrderMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class NumberMessageController {
    @Autowired
    private NumberMessageService numberMessageService;
    @Autowired
    OrderMessageService orderMessageService;

    @PostMapping("/addNumberMessage")
    public ReturnMessage addNumberMessage(@RequestBody Map<String, Object> schedules){
        return this.numberMessageService.addNumberMessage(schedules);
    }

    @GetMapping("/getNumberMessageByDoctorNum")
    public NumberMessage getNumberMessage(String doctorNum){
        //获得今天的日期
        Calendar calendar = Calendar.getInstance();
        String dateStr = String.valueOf(calendar.get(Calendar.YEAR));
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month >= 10){
            dateStr = dateStr + "-" + String.valueOf(month);
        }else {
            dateStr = dateStr + "-0" + String.valueOf(month);
        }

        int date = calendar.get(Calendar.DATE);
        if (date >= 10){
            dateStr = dateStr + "-" + String.valueOf(date);
        }else{
            dateStr = dateStr + "-0" + String.valueOf(date);
        }

        Date day = Date.valueOf(dateStr);
        return this.numberMessageService.searchNumber(doctorNum, day);
    }

    @GetMapping("/getNumberByDoctor")
    public List<NumberMessage> getNumberByDoctor(String doctorNum){
        return this.numberMessageService.getUsefulNumberByDoctor(doctorNum);
    }

    @PostMapping("/updateNumberById")
    public ReturnMessage updateNumberById(@RequestBody NumberMessage numberMessage){
        return this.numberMessageService.updateNumberById(numberMessage);
    }

    @GetMapping("/getNumberByDeptDate")
    public List<NumberMessage> getNumberByDeptDate(int dept, Date date){
        return this.numberMessageService.getNumberByDeptDate(dept, date);
    }

    @GetMapping("/deleteNumberById")
    public ReturnMessage deleteNumberById(int id[]){
        return this.numberMessageService.deleteNumberById(id);
    }

    @GetMapping("/getFutureDate")
    public Map<String, Integer>[] getFutureDate(){
        return  this.numberMessageService.getFutureDate();
    }

    @GetMapping("/getExperts")
    public List<Doctor> getFutureNumber(int dept, Date date){
        return this.numberMessageService.getDoctorsByDeptDate(dept, date);
    }

    @GetMapping("/getNumberMessage")
    public NumberMessage getNumberMessage(String doctorNum, Date date){
        return this.numberMessageService.searchNumber(doctorNum, date);
    }

    //返回某位医生的可预约、已约满、停诊状态的号源（停诊服务）
    @GetMapping("/getNumber")
    public List<NumberMessage> getNumber(String doctorNum){
        return this.numberMessageService.getNumberListByDoctor(doctorNum);
    }

    @GetMapping("/stopTreatment")
    public ReturnMessage stopTreatment(int numberId){
        ReturnMessage returnMessage = new ReturnMessage();
        if (this.numberMessageService.updateStatus(2, numberId) >= 1){
            this.orderMessageService.setStatus(3, numberId);
            returnMessage.setMessage("已停诊");
            returnMessage.setIsSuccess(0);
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("停诊失败！");
        }
        return returnMessage;
    }
}
