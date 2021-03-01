package com.sifang.service.impl;

import com.sifang.mapper.PatientMessageMapper;
import com.sifang.pojo.PatientMessage;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientMessageMapper patientMessageMapper;

    @Override
    public ReturnMessage addPatient(PatientMessage patientMessage) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (patientMessageMapper.addPatient(patientMessage) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("添加患者信息成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("添加患者信息失败！");
        }
        return returnMessage;
    }

    @Override
    public ReturnMessage updatePatient(PatientMessage patientMessage) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (patientMessageMapper.updatePatient(patientMessage) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("修改成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("修改失败！");
        }
        return returnMessage;
    }

    @Override
    public ReturnMessage deletePatientByAccount(String account) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (patientMessageMapper.deletePatientByAccount(account) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("删除成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("删除失败！");
        }
        return returnMessage;
    }
}
