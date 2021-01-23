package com.example.client.dto;

import javax.persistence.*;

/**
 * 文件信息表
 *
 * @author 言曌
 * @date 2021/1/22 9:45 下午
 */
@Entity
@Table(name = "t_file")
public class FileDTO {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 文件名称
     */
    @Column(nullable = false, name = "file_full_name")
    private String fileFullName;

    /**
     * 文件名称
     */
    @Column(nullable = false, name = "file_name")
    private String fileName;

    /**
     * 文件后缀
     */
    @Column(nullable = false, name = "file_suffix")
    private String fileSuffix;

    /**
     * 文件存储路径
     */
    @Column(nullable = false, name = "file_absolute_path")
    private String fileAbsolutePath;


    /**
     * 用户ID
     */
    @Column(nullable = false, name = "user_id")
    private Long userId;


    /**
     * 文件大小
     */
    @Column(nullable = false, name = "file_size")
    private Long fileSize;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileFullName() {
        return fileFullName;
    }

    public void setFileFullName(String fileFullName) {
        this.fileFullName = fileFullName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }

    public void setFileAbsolutePath(String fileAbsolutePath) {
        this.fileAbsolutePath = fileAbsolutePath;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
