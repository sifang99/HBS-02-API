package com.sifang.controller;

import com.sifang.pojo.ReturnMessage;
import com.sifang.pojo.UserLogin;
import com.sifang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
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
    public ReturnMessage login(@RequestBody Map<String, String> login){
        return userService.userLogin(login.get("account"), login.get("pwd"));
    }

    //用户注册
    @RequestMapping("/userRegister")
    public ReturnMessage register(@RequestBody UserLogin userLogin){
        System.out.println(userLogin);
        return userService.register(userLogin.getNickname(), userLogin.getTel(), userLogin.getPwd());
    };

    //用户修改密码
    @RequestMapping("/userUpdatePwd")
    public ReturnMessage updatePwd(String account, String oldPwd, String newPwd){
        return userService.updatePwd(account, oldPwd, newPwd);
    }
}
