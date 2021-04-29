package com.sifang.config;


import com.sifang.auth.*;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * 对shiro的拦截器进行注入
 * <p>
 * securityManager:
 * 所有Subject 实例都必须绑定到一个SecurityManager上,SecurityManager 是 Shiro的核心，
 * 初始化时协调各个模块运行。然而，一旦 SecurityManager协调完毕，
 * SecurityManager 会被单独留下，且我们只需要去操作Subject即可，无需操作SecurityManager 。
 * 但是我们得知道，当我们正与一个 Subject 进行交互时，实质上是
 * SecurityManager在处理 Subject 安全操作
 **/

@Configuration
public class ShiroConfig {

    //ShiroFilterFactoryBean:1
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(securityManager);
        //添加shiro的内置过滤器
        /**
         * anon: 无需认证就可以访问
         * authc: 必须认证了才能访问
         * user: 必须拥有 记住我 功能才能用
         * perms: 拥有对某个资源的权限才能访问
         * role: 拥有某个角色权限才能访问
         */
        //登录拦截
        //auth过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("corsAuthenticationFilter", new CORSAuthenticationFilter());
        bean.setFilters(filters);
        //拦截器，配置访问权限 必须是LinkedHashMap，因为它必须保证有序。滤链定义，从上向下顺序执行，一般将 /**放在最为下边
        Map<String, String> filterMap = new LinkedHashMap<>();
        //用户不无需登录就可以访问的接口
        filterMap.put("/Dept/getAllDepts", "anon");
        filterMap.put("/Dept/getDeptById", "anon");
        filterMap.put("/getAttentionList", "anon");
        filterMap.put("/getAttentionById", "anon");
        filterMap.put("/getCommentsByDoctorNum", "anon");
        filterMap.put("/getDoctorListByDept", "anon");
        filterMap.put("/getDoctorByNum", "anon");
        filterMap.put("/getNumberByDeptDate", "anon");
        filterMap.put("/getExperts", "anon");
        filterMap.put("/getNumberMessage", "anon");
        filterMap.put("/getOrderMessage", "anon");
        filterMap.put("/userLogin", "anon");
        filterMap.put("/userLogout", "anon");
        filterMap.put("/userRegister", "anon");
        filterMap.put("/getUserByTel", "anon");
        filterMap.put("/getUserByNickname", "anon");

        //用户登陆后才可以访问
        filterMap.put("/addComment", "perms[user:comment]");
        filterMap.put("/deleteComment", "perms[user:comment]");
        filterMap.put("/addOrderMessage", "perms[user:order]");
        filterMap.put("/returnOrder", "perms[user:order]");
        filterMap.put("/getOrderRecord", "perms[user:record]");
        filterMap.put("/addPatient", "perms[user:patient]");
        filterMap.put("/getPatient", "perms[user:patient]");
        filterMap.put("/deletePatient", "perms[user:patient]");
        filterMap.put("/userUpdatePwd", "perms[user:updatePwd]");

        //拥有管理员角色才能访问的权限
        filterMap.put("/addAttention", "perms[admin:attention]");
        filterMap.put("/updateAttention", "perms[admin:attention]");
        filterMap.put("/getAttentionByDate", "perms[admin:attention]");
        filterMap.put("/deleteAttention", "perms[admin:attention]");

        filterMap.put("/addDept", "perms[admin:dept]");
        filterMap.put("/updateDept", "perms[admin:dept]");
        filterMap.put("/deleteDept", "perms[admin:dept]");

        filterMap.put("/getDoctorsByDept", "perms[admin:doctor]");
        filterMap.put("/addDoctor", "perms[admin:doctor]");
        filterMap.put("/updateDoctor", "perms[admin:doctor]");
        filterMap.put("/deleteDoctor", "perms[admin:doctor]");

        filterMap.put("/addNumberMessage", "perms[admin:numberMessage]");
        filterMap.put("/getNumberByDoctor", "perms[admin:numberMessage]");
        filterMap.put("/updateNumberById", "perms[admin:numberMessage]");
        filterMap.put("/deleteNumberById", "perms[admin:numberMessage]");
        filterMap.put("/getNumber", "perms[admin:numberMessage]");

        filterMap.put("/stopTreatment", "perms[admin:stopTreatment]");

        filterMap.put("/getCheckList", "perms[admin:retreat]");
        filterMap.put("/agreeRetreat", "perms[admin:retreat]");
        filterMap.put("/disagreeRetreat", "perms[admin:retreat]");


        //剩余的请求shiro都拦截
//        filterMap.put("/**/*", "authc");

        bean.setFilterChainDefinitionMap(filterMap);

        return bean;
    }

    //DefaultWebSecurityManager：2
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //设置realm
        securityManager.setAuthenticator(modularRealmAuthenticator());
        //添加多个realm
        List<Realm> realmList = new ArrayList<>();
        realmList.add(getUserRealm());
        realmList.add(getWorkerRealm());
        securityManager.setRealms(realmList);

        //自定义的shiro session 缓存管理器
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    //1
    /**
     * 系统自带的Realm管理，主要针对多realm
     * */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(){
        //自己重写的ModularRealmAuthenticator
        UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    /**
     * 自定义的 shiro session 缓存管理器，用于跨域等情况下使用 token 进行验证，不依赖于sessionId
     * @return
     */
    @Bean
    public SessionManager sessionManager(){
        //将我们继承后重写的shiro session 注册
        ShiroSession shiroSession = new ShiroSession();
        //如果后续考虑多tomcat部署应用，可以使用shiro-redis开源插件来做session 的控制，或者nginx 的负载均衡
        shiroSession.setSessionDAO(new EnterpriseCacheSessionDAO());
        return shiroSession;
    }

    @Bean
    public UserRealm getUserRealm(){
        UserRealm userRealm = new UserRealm();

        return userRealm;
    }

    @Bean
    public WorkerRealm getWorkerRealm(){
        WorkerRealm workerRealm = new WorkerRealm();

        return workerRealm;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}

