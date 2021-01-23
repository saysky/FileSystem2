package com.example.client.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Shiro 配置类
 */
@Configuration
public class ShiroConfig {


    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();


        // 需要登录访问的URL配置 filterChainDefinitionMap.put("/xxx", "authc");
//        filterChainDefinitionMap.put("/admin", "authc");


        // 可以匿名访问的URL配置  filterChainDefinitionMap.put("/xxx", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/doLogin", "anon");
        filterChainDefinitionMap.put("/doRegister", "anon");


        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);


        // 如果不设置默认会自动寻找Web工程根目录下的"/login"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        return shiroFilterFactoryBean;

    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        return securityManager;
    }


    @Bean
    public MyRealm myRealm() {
        MyRealm normalRealm = new MyRealm();
        normalRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return normalRealm;
    }

    /**
     * MD5加盐加密十次
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，md5("")
        hashedCredentialsMatcher.setHashIterations(10);
        return hashedCredentialsMatcher;
    }

    @Bean
    public AllowAllCredentialsMatcher allowAllCredentialsMatcher() {
        return new AllowAllCredentialsMatcher();
    }

}