package com.sifang.service;

import com.sifang.pojo.Dept;
import com.sifang.pojo.ReturnMessage;

import java.util.List;

public interface DeptService {
    ReturnMessage addDept(Dept dept);
    //修改科室
    ReturnMessage updateDept(Dept dept);
    //获得所有科室
    List<Dept> getAllDepts();
    //删除科室，如果被删除科室是一级科室，那么也要删除其包含的二级科室
    ReturnMessage deleteDept(int id);
}
