package com.example.client.service;


import com.example.client.dto.DocumentDTO;

import java.util.List;

/**
 * 文档服务层接口
 *
 * @author 言曌
 * @date 2021/1/22 8:46 下午
 */
public interface DocumentService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    DocumentDTO findById(Long id);

    /**
     * 根据id删除
     *
     * @param id
     */
    void deleteById(Long id);


    /**
     * 添加文档
     *
     * @param documentDTO
     */
    void addDocument(DocumentDTO documentDTO);

    /**
     * 根据用户ID和文档号查询
     *
     * @param userId
     * @param documentKey
     * @return
     */
    List<DocumentDTO> findByUserIdAndDocumentKey(Long userId, String documentKey);

}
