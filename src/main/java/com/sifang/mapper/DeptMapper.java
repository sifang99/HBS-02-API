package com.sifang.mapper;

import com.sifang.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeptMapper {
    int addDept(Dept dept);
    //修改科室
    int updateDept(Dept dept);
    //查询所有科室
    List<Dept> getDeptList();
    //删除一级科室
    int deleteDept(int id);
    //删除二级科室
    int deleteAffiliation(int affiliate);
    //通过科室id查询科室
    Dept getDeptById(int id);
    //通过affiliate查询科室
    List<Dept> getDeptByAffiliate(int affiliate);
}
