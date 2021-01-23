package com.example.client.service;


import com.example.client.dto.UserDTO;

/**
 * 用户服务层接口
 *
 * @author 言曌
 * @date 2021/1/22 8:46 下午
 */
public interface UserService {

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    UserDTO findByUserName(String userName);


    /**
     * 添加用户
     *
     * @param user
     */
    void addUser(UserDTO user);
}
