package com.sifang.controller;

import com.sifang.pojo.Doctor;
import com.sifang.pojo.ReturnMessage;
import com.sifang.pojo.WorkerLogin;
import com.sifang.service.DoctorService;
import com.sifang.service.WorkerService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private WorkerService workerService;

    //通过dept的id获得医生
    @GetMapping("/getDoctorsByDept")
    public List<Map<String, Object>> getDoctorsByDept(int dept){
        return this.doctorService.getDoctorsByDept(dept);
    }
    @GetMapping("/getDoctorListByDept")
    public List<Doctor> getDoctorListByDept(int dept){
        return this.doctorService.getDoctorListByDept(dept);
    }
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

    //删除医生信息的同时删除登录账号
    @RequestMapping("/deleteDoctor")
    public ReturnMessage deleteDoctor(String num){
        ReturnMessage returnMessage = new ReturnMessage();
        if (doctorService.deleteDoctorByNum(num).getIsSuccess() == 0){
            if (workerService.deleteWorker(num).getIsSuccess() == 0){
                returnMessage.setIsSuccess(0);
                returnMessage.setMessage("删除成功！");
            }else{
                returnMessage.setMessage("删除登录账号失败！");
                returnMessage.setIsSuccess(1);
            }
        }else{
            returnMessage.setMessage("删除医生失败！");
            returnMessage.setIsSuccess(1);
        }
        return returnMessage;
    }

    @GetMapping("/getDoctorByNum")
    public Doctor getDoctorByNum(String doctorNum){
        return this.doctorService.getDoctorByNum(doctorNum);
    }

}
