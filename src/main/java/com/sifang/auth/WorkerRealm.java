package com.sifang.auth;

import com.sifang.pojo.WorkerLogin;
import com.sifang.service.WorkerService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkerRealm extends AuthorizingRealm {
    @Autowired
    WorkerService workerService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了管理员的授权");
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了管理人员的认证");
        //获取token
        UserPasswordToken token = (UserPasswordToken) authenticationToken;
        String num = token.getUsername();
        WorkerLogin workerLogin;
        //判断管理员是否存在
        workerLogin = workerService.getByNum(num);
        if (workerLogin == null){//管理员不存在
            return null;
        }
        return new SimpleAuthenticationInfo(workerLogin, workerLogin.getPwd(),this.getName());
    }
}
