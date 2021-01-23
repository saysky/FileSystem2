package com.example.client.service;


import com.example.client.dto.DocumentDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 文档服务层接口
 *
 * @author 言曌
 * @date 2021/1/22 8:46 下午
 */
public interface DocumentService {

    /**
     * 根据id删除
     *
     * @param id
     */
    void deleteByIdAndUserId(Long id, Long userId);


    /**
     * 添加文档
     *
     */
    void uploadDocument(String documentKeys,
                        String documentTitle,
                        Long userId,
                        MultipartFile file) throws IOException;

    /**
     * 下载文档
     */
    void downloadDocument(Long fileId,  Long userId, HttpServletResponse response) throws Exception;
    /**
     * 根据文档号查询
     *
     * @param documentKey
     * @return
     */
    List<DocumentDTO> findByUserIdAndDocumentKey(Long userId, String documentKey);

}
