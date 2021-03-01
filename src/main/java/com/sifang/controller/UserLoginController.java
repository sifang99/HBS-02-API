package com.sifang.controller;

import com.sifang.pojo.ReturnMessage;
import com.sifang.pojo.UserLogin;
import com.sifang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserLoginController {

    @Autowired
    private UserService userService;
    private ReturnMessage returnMessage;//封装返回信息

    @RequestMapping("/getAllUsers")
    public List<UserLogin> getUserList(){
        return userService.getUserList();
    }

    //用户登录
    @RequestMapping("/userLogin")
    public ReturnMessage login(String account, String pwd){
        return userService.userLogin(account, pwd);
    }

    //用户注册
    @RequestMapping("/userRegister")
    public ReturnMessage register(String nickname, String tel, String pwd){
        return userService.register(nickname, tel, pwd);
    };

    //用户修改密码
    @RequestMapping("/userUpdatePwd")
    public ReturnMessage updatePwd(String account, String oldPwd, String newPwd){
        return userService.updatePwd(account, oldPwd, newPwd);
    }
}
