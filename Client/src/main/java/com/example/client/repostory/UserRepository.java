package com.example.client.repostory;

import com.example.client.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户数据访问层，DAO层
 * 方法命名规则参考：https://www.cnblogs.com/oxygenG/p/10057525.html
 *
 * @author 言曌
 * @date 2021/1/22 8:48 下午
 */
@Repository
public interface UserRepository extends JpaRepository<UserDTO, Long> {

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    UserDTO findByUserName(String userName);
}
