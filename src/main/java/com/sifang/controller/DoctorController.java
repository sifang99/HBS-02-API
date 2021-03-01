package com.sifang.controller;

import com.sifang.pojo.Doctor;
import com.sifang.pojo.ReturnMessage;
import com.sifang.pojo.WorkerLogin;
import com.sifang.service.DoctorService;
import com.sifang.service.WorkerService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private WorkerService workerService;

    //添加医生
    @PostMapping("/addDoctor")
    public ReturnMessage addDoctor(@RequestBody Doctor doctor){
        ReturnMessage returnMessage ;
        //添加医生
        returnMessage = doctorService.addDoctor(doctor);
        if (returnMessage.getIsSuccess() == 0){
            //医生添加成功后，为医生注册登陆账号
            WorkerLogin workerLogin = new WorkerLogin();
            workerLogin.setNum(doctor.getNum());
            workerLogin.setPwd(doctor.getNum());
            if (workerService.addWorker(workerLogin).getIsSuccess() == 0){
                returnMessage.setMessage("添加成功！");
            }else{
                returnMessage.setIsSuccess(1);
                returnMessage.setMessage("添加医生登录账号失败！");
            }
        }
        return returnMessage;
    }

    //医生修改密码
    @RequestMapping("/doctorUpdatePwd")
    public ReturnMessage updatePwd(String num, String pwd){
        return workerService.updatePwd(num, pwd);
    }

    //修改医生信息
    @RequestMapping("/updateDoctor")
    public ReturnMessage updateDoctor(@RequestBody Doctor doctor){
        return doctorService.updateDoctor(doctor);
    }

    //删除医生
    @RequestMapping("/deleteDoctor")
    public ReturnMessage deleteDoctor(int id){
        return doctorService.deleteDoctorById(id);
    }

}
