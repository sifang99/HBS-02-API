package com.sifang.controller;

import com.sifang.pojo.Dept;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/Dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @PostMapping("/addDept")
    public ReturnMessage addDept(@RequestBody Dept dept){
        return deptService.addDept(dept);
    }

    @PostMapping("/updateDept")
    public ReturnMessage updateDept(@RequestBody Dept dept){
        return deptService.updateDept(dept);
    }

    @GetMapping("/getAllDepts")
    public List<Map<String, Object>> getAllDepts(){
        return deptService.getAllDepts();
    }

    @GetMapping("/deleteDept")
    public ReturnMessage deletDept(int id){
        return deptService.deleteDept(id);
    }
    @GetMapping("/getDeptById")
    public Dept getDeptById(int id){
        return this.deptService.getDeptById(id);
    }

}
