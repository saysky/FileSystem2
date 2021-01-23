package com.example.client.dto;


/**
 * 文件信息表
 *
 * @author 言曌
 * @date 2021/1/22 9:45 下午
 */
public class FileDTO {

    /**
     * ID
     */
    private Long id;

    /**
     * 文件名称
     */
    private String fileFullName;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件存储路径
     */
    private String fileAbsolutePath;


    /**
     * 用户ID
     */
    private Long userId;


    /**
     * 文件大小
     */
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
