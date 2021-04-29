package com.sifang.controller;

import com.sifang.pojo.PatientMessage;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.PatientService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class PatientMessageController {
    @Autowired
    private PatientService patientService;

    //添加患者信息
    @PostMapping("/addPatient")
    public ReturnMessage addPatient(@RequestBody PatientMessage patientMessage){
        return patientService.addPatient(patientMessage);
    }

    //查询就诊人基本信息
    @GetMapping("/getPatient")
    public List<PatientMessage> getPatientListByUserId(int userId){
        return this.patientService.getPatientListByUserId(userId);
    }

    //删除患者信息
    @RequestMapping("/deletePatient")
    public ReturnMessage deletePatient(String account){
        return patientService.deletePatientByAccount(account);
    }
}
