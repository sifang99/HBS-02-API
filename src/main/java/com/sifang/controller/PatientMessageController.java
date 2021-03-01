package com.sifang.controller;

import com.sifang.pojo.PatientMessage;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientMessageController {
    @Autowired
    private PatientService patientService;

    //添加患者信息
    @PostMapping("/addPatient")
    public ReturnMessage addPatient(@RequestBody PatientMessage patientMessage){
        return patientService.addPatient(patientMessage);
    }

    //修改患者信息
    @RequestMapping("/updatePatient")
    public ReturnMessage updatePatient(@RequestBody PatientMessage patientMessage){
        return patientService.updatePatient(patientMessage);
    }

    //删除患者信息
    @RequestMapping("/deletePatient")
    public ReturnMessage deletePatient(String account){
        return patientService.deletePatientByAccount(account);
    }
}
