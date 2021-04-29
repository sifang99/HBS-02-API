package com.sifang.controller;

import com.sifang.auth.UserPasswordToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class WorkerLoginController {

    final static String LOGIN_TYPE = "Worker";

    @PostMapping("/workerLogin")
    public Map<String, Object> login(@RequestBody Map<String, String>  workerLogin){

        Map<String, Object> result = new HashMap<>();
        //获取传递过来的账号和密码
        String num = workerLogin.get("num");
        String pwd = workerLogin.get("pwd");
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //生成token
        UserPasswordToken token = new UserPasswordToken(num, pwd, LOGIN_TYPE);
        try{
            subject.login(token);
            Serializable tokenId = subject.getSession().getId();
            result.put("tokenId", tokenId);
            result.put("message", "登录成功");
            result.put("isLogin", true);
            return result;
        }catch (UnknownAccountException e){
            result.put("isLogin", false);
            result.put("message", "登陆失败！用户名不存在");
            return result;
        }catch (IncorrectCredentialsException e){
            result.put("isLogin", false);
            result.put("message", "登陆失败！密码错误");
            return result;
        }
    }
}
