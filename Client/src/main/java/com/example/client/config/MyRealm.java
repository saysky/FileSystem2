package com.example.client.config;

import com.example.client.constant.CommonConst;
import com.example.client.dto.UserDTO;
import com.example.client.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * Shiro的用户认证授权配置
 */
public class MyRealm extends AuthorizingRealm {

    Logger log = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    @Lazy
    private UserService userService;


    /**
     * 认证信息(身份验证) Authentication 是用来验证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("认证-->MyShiroRealm.doGetAuthenticationInfo()");
        //1.验证用户名
        String userName = (String) token.getPrincipal();
        UserDTO user = userService.findByUserName(userName);
        if (user == null) {
            //用户不存在
            log.info("用户不存在! 登录名:{}, 密码:{}", userName, token.getCredentials());
            return null;
        }

        //4.封装authenticationInfo，准备验证密码
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, // 用户名
                user.getPassword(), // 密码
                ByteSource.Util.bytes(CommonConst.PASSWORD_SALT), // 盐
                getName() // realm name
        );
        System.out.println("realName:" + getName());
        return authenticationInfo;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        return authorizationInfo;
    }
}
