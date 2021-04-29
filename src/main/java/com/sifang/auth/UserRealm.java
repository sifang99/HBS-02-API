package com.sifang.auth;

import com.sifang.pojo.RolePermission;
import com.sifang.pojo.SysToken;
import com.sifang.pojo.UserLogin;
import com.sifang.pojo.UserRole;
import com.sifang.service.*;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Autowired
    SysTokenService sysTokenService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleService roleService;
    @Autowired
    RolePermissionService rolePermissionService;
    @Autowired
    PermissionService permissionService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了用户的授权");
        //1.获取用户的登录信息
        UserLogin userLogin = (UserLogin) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        List<UserRole> userRoleList = userRoleService.getRoleListByUser(userLogin.getId());
        for (UserRole userRole : userRoleList){
            //添加角色
            String roleName = roleService.getRoleById(userRole.getUserId()).getName();
            simpleAuthorizationInfo.addRole(roleName);

            //添加权限
            List<RolePermission> rolePermissionList = rolePermissionService.getByRole(userRole.getRoleId());
            for (RolePermission rolePermission : rolePermissionList){
                String permissionName = permissionService.getById(rolePermission.getPermissionId()).getName();
                simpleAuthorizationInfo.addStringPermission(permissionName);
            }
        }
        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了用户的认证");
        UserPasswordToken userToken = (UserPasswordToken) token;
        String account = userToken.getUsername();
        UserLogin userLogin;
        //判断用户是通过昵称进行登录，还是通过电话号码进行登录
        if (userService.isNickname(account) == 1){ //如果用户通过昵称进行登录
            userLogin = userService.getUserByNickname(account);
        }else{//如果用户通过电话号码进行登录
            userLogin = userService.getUserByTel(account);
        }
        if (userLogin == null){//用户不存在
            return null;
        }
        //密码认证，shiro做
        return new SimpleAuthenticationInfo(userLogin, userLogin.getPwd(), this.getName());
    }
}
