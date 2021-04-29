package com.sifang.auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//重写ModularRealmAuthenticator的方法doAuthenticate以实现多个realm的使用
/**
 * 当配置了多个Realm时，我们通常使用的认证器是shiro自带的org.apache.shiro.authc.pam.ModularRealmAuthenticator，
 * 其中决定使用的Realm的是doAuthenticate()方法
 *
 * 自定义Authenticator
 * 注意，当需要分别定义处理用户和管理员验证的Realm时，对应Realm的全类名应该包含字符串“User”“Worker”。
 * 并且，他们不能相互包含，例如，处理用户验证的Realm的全类名中不应该包含字符串"Worker"。
 */
public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        //判断getRealms()是否返回为空
        assertRealmsConfigured();
        //强制转换成自定义的UserPasswordToken
        UserPasswordToken token = (UserPasswordToken) authenticationToken;
        //获取登录类型
        String loginType = token.getLoginType();
        //所有realm
        Collection<Realm> realms = getRealms();
        //登陆类型对应的所有realm
        List<Realm> typeRealms = new ArrayList<>();
        for(Realm realm : realms){
            if (realm.getName().contains(loginType)){
                typeRealms.add(realm);
            }
        }

        //判断是单realm还是多个realm
        if (typeRealms.size() == 1){
            return doSingleRealmAuthentication(typeRealms.get(0), token);
        }else {
            return doMultiRealmAuthentication(typeRealms, token);
        }
    }
}
