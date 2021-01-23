package com.example.client.controller;

import com.example.client.constant.CommonConst;
import com.example.client.dto.UserDTO;
import com.example.client.service.UserService;
import com.example.client.util.Md5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户控制器
 *
 * @author 言曌
 * @date 2021/1/22 8:55 下午
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录页面
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 注册页面
     *
     * @return
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 登录提交
     *
     * @return
     */
    @PostMapping("/doLogin")
    public String doLogin(@RequestParam("userName") String userName,
                          @RequestParam("password") String password,
                          Model model) {

        String errorMsg = "";
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                // 登录成功，跳转到首页
                return "redirect:/";
            }
        } catch (UnknownAccountException e) {
            errorMsg = "账号不存在";
        } catch (IncorrectCredentialsException e) {
            errorMsg = "密码错误";
        } catch (Exception e) {
            e.printStackTrace();
            errorMsg = "服务端异常";
        }
        // 登录失败，依然在登录页面
        model.addAttribute("msg", errorMsg);
        return "login";
    }


    /**
     * 注册提交
     *
     * @return
     */
    @PostMapping("/doRegister")
    public String doRegister(@RequestParam("userName") String userName,
                             @RequestParam("realName") String realName,
                             @RequestParam("password") String password,
                             Model model) {
        UserDTO user = userService.findByUserName(userName);
        if (user != null) {
            // 用户名已存在，依然停留在注册页面
            model.addAttribute("msg", "用户名已存在");
            return "register";
        }

        // 添加用户
        user = new UserDTO();
        user.setUserName(userName);
        user.setRealName(realName);
        // 密码采用 MD5加盐加密10次
        user.setPassword(Md5Util.toMd5(password, CommonConst.PASSWORD_SALT, 10));
        userService.addUser(user);

        model.addAttribute("msg", "注册成功");
        // 注册成功，跳转到登录页面
        return "login";
    }
}
