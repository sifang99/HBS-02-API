package com.sifang.controller;

import com.sifang.pojo.ReturnMessage;
import com.sifang.pojo.UserLogin;
import com.sifang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Map<String, Object> login(@RequestBody Map<String, String> login){
        return userService.userLogin(login.get("account"), login.get("pwd"));
    }

    //用户注册
    @RequestMapping("/userRegister")
    public ReturnMessage register(@RequestBody UserLogin userLogin){
        System.out.println(userLogin);
        return userService.register(userLogin.getNickname(), userLogin.getTel(), userLogin.getPwd());
    };

    //通过电话号码查询用户
    @GetMapping("/getUserByTel")
    public ReturnMessage getUserByTel(String tel){
        ReturnMessage returnMessage = new ReturnMessage();
        if (this.userService.getUserByTel(tel) != null){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("该电话号码已注册！");
        }else{
            returnMessage.setMessage("该电话号码并未注册");
            returnMessage.setIsSuccess(1);
        }
        return  returnMessage;
    }

    //通过昵称查询用户
    @GetMapping("/getUserByNickname")
    public ReturnMessage getUserByNickname(String nickname){
        ReturnMessage returnMessage = new ReturnMessage();
        if (this.userService.getUserByNickname(nickname) != null){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("该昵称已注册");
        }else {
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("该昵称尚未注册");
        }
        return  returnMessage;
    }

    //用户修改密码
    @RequestMapping("/userUpdatePwd")
    public ReturnMessage updatePwd(String account, String oldPwd, String newPwd){
        return userService.updatePwd(account, oldPwd, newPwd);
    }
}
