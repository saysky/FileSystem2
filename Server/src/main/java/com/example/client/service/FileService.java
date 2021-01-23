package com.example.client.service;


import com.example.client.dto.FileDTO;

/**
 * 文件信息服务层接口
 *
 * @author 言曌
 * @date 2021/1/22 8:46 下午
 */
public interface FileService {

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    FileDTO findById(Long id);


    /**
     * 存储文件信息
     *
     * @param file
     */
    void addFile(FileDTO file);

    /**
     * 根据id删除
     * @param id
     */
    void deleteFile(Long id);


}
