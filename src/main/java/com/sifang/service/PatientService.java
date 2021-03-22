package com.sifang.service;

import com.sifang.pojo.PatientMessage;
import com.sifang.pojo.ReturnMessage;

import java.util.List;

public interface PatientService {
    ReturnMessage addPatient(PatientMessage patientMessage);
    //修改患者信息
    ReturnMessage updatePatient(PatientMessage patientMessage);
    //通过身份证号删除患者信息
    ReturnMessage deletePatientByAccount(String account);
    List<PatientMessage> getPatientListByUserId(int userId);
    PatientMessage getPatientByAccount(String account);
}
