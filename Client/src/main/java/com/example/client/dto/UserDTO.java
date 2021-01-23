package com.example.client.dto;


import javax.persistence.*;

/**
 * @author 言曌
 * @date 2021/1/22 8:40 下午
 */
@Entity
@Table(name = "t_user")
public class UserDTO {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @Column(nullable = false, name = "user_name")
    private String userName;

    /**
     * 姓名
     */
    @Column(nullable = false, name = "real_name")
    private String realName;

    /**
     * 密码
     */
    @Column(nullable = false, name = "password")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
