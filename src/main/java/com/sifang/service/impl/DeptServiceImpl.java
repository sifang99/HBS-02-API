package com.sifang.service.impl;

import com.sifang.mapper.DeptMapper;
import com.sifang.pojo.Dept;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;


    @Override
    public ReturnMessage addDept(Dept dept) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (deptMapper.addDept(dept) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("添加科室成功！");
        }else {
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("添加科室失败！");
        }
        return returnMessage;
    }

    @Override
    public ReturnMessage updateDept(Dept dept) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (deptMapper.updateDept(dept) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("修改成功！");
        }else {
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("修改失败！");
        }
        return returnMessage;
    }

    @Override
    public List<Dept> getAllDepts() {
        return deptMapper.getDeptList();
    }

    @Override
    public ReturnMessage deleteDept(int id) {
        ReturnMessage returnMessage = new ReturnMessage();
        Dept dept = deptMapper.getDeptById(id);
        boolean isAffilation = false;
        //判断科室是否为一级科室
        if (dept.getAffiliate() != 0) isAffilation =true;
        //如果是一级科室，则先删除其附属科室，再删除该科室
        if (!isAffilation){
            List<Dept> affiliation= deptMapper.getDeptByAffiliate(id);
            //判断该一级科室是否含有附属科室
            if (affiliation != null){
                //如果删除附属科室失败，则直接返回
                if (deptMapper.deleteAffiliation(id) < 1){
                    returnMessage.setIsSuccess(1);
                    returnMessage.setMessage("删除"+dept.getName()+"的附属科室失败！");
                    return returnMessage;
                }
            }
        }
        //删除一级科室
        if (deptMapper.deleteDept(id) >= 1){
            returnMessage.setIsSuccess(0);
            if (!isAffilation){
                returnMessage.setMessage("成功删除"+dept.getName()+"及其附属科室！");
            }else{
                returnMessage.setMessage("成功删除"+dept.getName()+"！");
            }
        }else {
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("删除"+dept.getName()+"失败！");
        }
        return returnMessage;
    }
}
