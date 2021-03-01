package com.sifang.mapper;

import com.sifang.pojo.Doctor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DoctorMapper {
    int addDoctor(Doctor doctor);
    Doctor isNumExist(String num);
    int updateDoctor(Doctor doctor);
    int deleteDoctor(int id);
}
