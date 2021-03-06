package com.sifang.mapper;

import com.sifang.pojo.PatientMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PatientMessageMapper {
    int addPatient(PatientMessage patientMessage);
    int updatePatient(PatientMessage patientMessage);
    int deletePatientByAccount(String account);
    List<PatientMessage> getPatientListByUserId(int userId);
    PatientMessage getPatientByAccount(String account);
}
