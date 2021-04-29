package com.sifang.controller;

import com.sifang.auth.UserPasswordToken;
import com.sifang.pojo.ReturnMessage;
import com.sifang.pojo.UserLogin;
import com.sifang.pojo.UserRole;
import com.sifang.service.UserRoleService;
import com.sifang.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class UserLoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    final static String LOGIN_TYPE = "User";
    //用户登录
    @RequestMapping("/userLogin")
    public Map<String, Object> login(@RequestBody Map<String, String> login){
        String account = login.get("account");
        String pwd = login.get("pwd");
        boolean isNickname = false;
        Map<String, Object> result = new HashMap<>();

        //判断用户是通过昵称进行登录，还是通过电话号码进行登录
        if (userService.isNickname(account) == 1){ //如果用户通过昵称进行登录
            isNickname = true;
            if (!userService.checkNickname(account)){//检查昵称是否符合规范
                result.put("message", "账号错误！");
                result.put("isLogin", false);
                return result;
            }
        }else if(userService.isNickname(account) == 2){//如果用户通过电话号码进行登录
            if (!userService.checkTel(account)){//检查电话号码是否合法
                result.put("message", "账号错误！");
                result.put("isLogin", false);
                return result;
            }
        }else{
            result.put("message", "账号错误！");
            result.put("isLogin", false);
            return result;
        }

        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登陆数据
        UserPasswordToken token = new UserPasswordToken(account, pwd, LOGIN_TYPE);
        try{
            subject.login(token);
            Serializable tokenId = subject.getSession().getId();
            result.put("isLogin", true);
            result.put("message", "登录成功！");
            Map<String, Object> user = new HashMap<>();
            UserLogin userLogin;
            if (isNickname){
                userLogin = userService.getUserByNickname(account);
            }else{
                userLogin = userService.getUserByTel(account);
            }
            user.put("id", userLogin.getId());
            user.put("tel", userLogin.getTel());
            user.put("nickname", userLogin.getNickname());

            result.put("user", user);
            result.put("tokenId", tokenId);
            System.out.println(tokenId);
            return result;//登录成功

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

    //用户退出登录
    @GetMapping("/userLogout")
    public void logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    //用户注册
    @RequestMapping("/userRegister")
    public ReturnMessage register(@RequestBody UserLogin userLogin){
        //注册成功后，自动为用户授权
        ReturnMessage registerResult = userService.register(userLogin.getNickname(), userLogin.getTel(), userLogin.getPwd());
        if (registerResult.getIsSuccess() == 0){
            UserLogin userLogin1 = userService.getUserByTel(userLogin.getTel());
            UserRole userRole = new UserRole();
            userRole.setUserId(userLogin1.getId());
            userRole.setRoleId(1);

            if (userRoleService.add(userRole) >= 1){
                return registerResult;
            }else {
                registerResult.setMessage("为用户授权失败！");
                registerResult.setIsSuccess(1);
                return registerResult;
            }
        }else {
            return registerResult;
        }
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
    public ReturnMessage updatePwd(@RequestBody Map<String, String> data){
        String account = data.get("account");
        String oldPwd = data.get("oldPwd");
        String newPwd = data.get("newPwd");
        return userService.updatePwd(account, oldPwd, newPwd);
    }
}
