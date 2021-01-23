package com.example.client.service.impl;

import com.example.client.dto.UserDTO;
import com.example.client.repostory.UserRepository;
import com.example.client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 *
 * @author 言曌
 * @date 2021/1/22 8:47 下午
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void addUser(UserDTO user) {
        userRepository.save(user);
    }
}
