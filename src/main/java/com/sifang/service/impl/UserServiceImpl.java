package com.sifang.service.impl;


import com.sifang.mapper.UserLoginMapper;
import com.sifang.pojo.ReturnMessage;
import com.sifang.pojo.UserLogin;
import com.sifang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Override
    public List<UserLogin> getUserList() {
        return userLoginMapper.getAllUser();
    }
    //根据昵称查询用户
    @Override
    public UserLogin getUserByNickname(String nickname) {
        return userLoginMapper.getUserByNickname(nickname);
    }
    //根据电话号码查询用户
    @Override
    public UserLogin getUserByTel(String tel) {
        return userLoginMapper.getUserByTel(tel);
    }

    @Override
    public ReturnMessage userLogin(String account, String pwd) {
        char c = account.charAt(0);
        ReturnMessage returnMessage = new ReturnMessage();
        UserLogin userLogin;

        //判断用户是通过昵称进行登录，还是通过电话号码进行登录
        if (this.isNickname(account) ==1){ //如果用户通过昵称进行登录
            if (this.checkNickname(account)){//检查昵称是否符合规范
                userLogin = this.getUserByNickname(account);
            }else{
                returnMessage.setIsSuccess(1);
                returnMessage.setMessage("账号不存在！");
                return returnMessage;//账号不存在
            }
        }else if(this.isNickname(account) == 2){//如果用户通过电话号码进行登录
            if (this.checkTel(account)){//检查电话号码是否合法
                userLogin = this.getUserByTel(account);
            }else{
                returnMessage.setIsSuccess(1);
                returnMessage.setMessage("账号不存在！");
                return returnMessage;//账号不存在
            }
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("账号不存在！");
            return returnMessage;//账号不存在
        }

        if (userLogin.getPwd().equals(pwd)){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("登陆成功！");
            return returnMessage;//登录成功
        }else {
            returnMessage.setIsSuccess(2);
            returnMessage.setMessage("密码错误！");
            return returnMessage;//账号正确，密码错误
        }
    }

    @Override
    public boolean checkNickname(String nickname) {
        int length = nickname.length();
        if(length == 0 || length > 20) return false;
        for (int i = 0; i < length; i++){
            char ch = nickname.charAt(i);
            if ((ch >= 48 && ch <= 57) || (ch >= 65 && ch <= 90) ||  (ch >= 97 && ch <= 122)|| ('\u4e00' <= ch  && ch <= '\u9fff')){
                if (i==0 && ch >= 48 && ch <= 57){
                    return false;
                }
            }else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkTel(String tel) {
        int length = tel.length();
        if (length != 11) return false;
        for (int i=0; i < length; i++){
            if (tel.charAt(i) < 48 || tel.charAt(i) > 57) return false;
        }
        return true;
    }

    @Override
    public boolean checkPwd(String pwd) {
        int length = pwd.length();
        boolean includeNumber = false;
        boolean includeLetter = false;
        if (length < 12 || length > 20) return false;
        for (int i=0; i < length; i++){
            char ch = pwd.charAt(i);
            if (ch >= 48 && ch <= 57){
                includeNumber = true;
            }else if((ch >= 65 && ch <= 90) ||  (ch >= 97 && ch <= 122)){
                includeLetter = true;
            }else {
                return false;
            }
        }
        if (includeLetter && includeNumber){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean isNicknameExist(String nickname) {
        UserLogin userLogin = userLoginMapper.getUserByNickname(nickname);
        if (userLogin != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean isTelExist(String tel) {
        UserLogin userLogin = userLoginMapper.getUserByTel(tel);
        if (userLogin != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public ReturnMessage register(String nickname, String tel, String pwd) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (this.checkNickname(nickname)){
            if (this.isNicknameExist(nickname)){
                returnMessage.setIsSuccess(1);
                returnMessage.setMessage("昵称已经存在！");
                return returnMessage;
            }
            if(this.checkTel(tel)){
                if (this.isTelExist(tel)){
                    returnMessage.setIsSuccess(2);
                    returnMessage.setMessage("该电话号码已注册！");
                    return returnMessage;
                }
                if (this.checkPwd(pwd)){
                    if (userLoginMapper.addUser(tel, nickname, pwd) >= 1){
                        returnMessage.setIsSuccess(0);
                        returnMessage.setMessage("注册成功！");
                        return returnMessage;
                    }else{
                        returnMessage.setIsSuccess(3);
                        returnMessage.setMessage("注册失败！无法插入数据");
                        return returnMessage;
                    }

                }else {
                    returnMessage.setIsSuccess(3);
                    returnMessage.setMessage("密码不符合规范！");
                    return returnMessage;
                }
            }else{
                returnMessage.setIsSuccess(3);
                returnMessage.setMessage("请填入正确的电话号码！");
                return returnMessage;
            }
        }else{
            returnMessage.setIsSuccess(3);
            returnMessage.setMessage("昵称不符合规范！");
            return returnMessage;
        }
    }

    @Override
    public int isNickname(String account) {
        char c = account.charAt(0);
        //判断用户是通过昵称进行登录，还是通过电话号码进行登录
        if ((c >= 65 && c <= 90) ||  (c >= 97 && c <= 122)|| ('\u4e00' <= c  && c <= '\u9fff')){
            return 1; //账号为昵称
        }else if(c >= 48 && c <= 57){
            return 2; //账号为电话号码
        }else{
            return 0; //账号非法
        }
    }

    @Override
    public ReturnMessage updatePwd(String account, String oldPwd, String newPwd) {
        ReturnMessage returnMessage = new ReturnMessage();
        UserLogin userLogin;
        boolean isTel = false; //编辑账号是否为电话号码
        //判断账号是昵称还是电话号码
        if (this.isNickname(account) == 1){
            userLogin = userLoginMapper.getUserByNickname(account);
        }else if (this.isNickname(account) == 2){
            userLogin = userLoginMapper.getUserByTel(account);
            isTel = true;
        }else{//账号不符合规范返回1
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("账号不存在！");
            return returnMessage;
        }
        //获取账号信息，如果数据库中账号不存在，返回2
        if (userLogin == null){
            returnMessage.setIsSuccess(2);
            returnMessage.setMessage("账号不存在！");
            return returnMessage;
        }
        //判断旧密码是否正确，如果不正确返回3
        if (!userLogin.getPwd().equals(oldPwd)){
            returnMessage.setIsSuccess(3);
            returnMessage.setMessage("旧密码输入错误！");
            return returnMessage;
        }
        //判断新密码是否符合规范，如果不符合返回4
        if (!this.checkPwd(newPwd)){
            returnMessage.setIsSuccess(4);
            returnMessage.setMessage("新密码不符合规范！");
            return returnMessage;
        }
        //更新用户信息，如果更新成功，返回0，如果更新失败，返回5
        if (isTel){
            if (userLoginMapper.updatePwdByTel(account, newPwd) >= 1){
                returnMessage.setIsSuccess(0);
                returnMessage.setMessage("修改成功！");
                return returnMessage;
            }else{
                returnMessage.setIsSuccess(5);
                returnMessage.setMessage("修改失败！数据库操作不成功");
                return returnMessage;
            }
        }else{
            if (userLoginMapper.updatePwdByNickname(account, newPwd) >= 1){
                returnMessage.setIsSuccess(0);
                returnMessage.setMessage("修改成功！");
                return returnMessage;
            }else{
                returnMessage.setIsSuccess(5);
                returnMessage.setMessage("修改失败！数据库操作不成功");
                return returnMessage;
            }
        }
    }
}
