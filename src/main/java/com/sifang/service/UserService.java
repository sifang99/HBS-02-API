package com.sifang.service;

import com.sifang.pojo.ReturnMessage;
import com.sifang.pojo.UserLogin;

import java.util.List;

public interface UserService {
    //获得所有用户
    List<UserLogin> getUserList();
    //根据昵称查询用户
    UserLogin getUserByNickname(String nickname);
    //根据电话号码查询用户
    UserLogin getUserByTel(String tel);
    //用户登录
    ReturnMessage userLogin(String account, String pwd);
    //检查nickname是否符合规范
    boolean checkNickname(String nickname);
    //检查tel是否符合规范
    boolean checkTel(String tel);
    //检查pwd是否符合规范
    boolean checkPwd(String pwd);
    //检查nickname是否已经存在
    boolean isNicknameExist(String nickname);
    //检查电话号码是否已经存在
    boolean isTelExist(String tel);
    //用户注册账号
    ReturnMessage register(String nickname, String tel, String pwd);
    //判断账号是否为昵称
    int isNickname(String account);
    //用户修改密码
    ReturnMessage updatePwd(String account, String oldPwd, String newPwd);
}
