package com.example.client.dto;


import javax.persistence.*;

/**
 * 文档
 *
 * @author 言曌
 * @date 2021/1/22 8:40 下午
 */
@Entity
@Table(name = "t_document")
public class DocumentDTO {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 文档号
     */
    @Column(nullable = false, name = "document_key")
    private String documentKey;

    /**
     * 文档标题
     */
    @Column(nullable = false, name = "document_title")
    private String documentTitle;

    /**
     * 文件ID
     */
    @Column(nullable = false, name = "file_id")
    private Long fileId;

    /**
     * 用户ID
     */
    @Column(nullable = false, name = "user_id")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
