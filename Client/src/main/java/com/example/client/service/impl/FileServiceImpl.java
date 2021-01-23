package com.example.client.service.impl;

import com.example.client.dto.FileDTO;
import com.example.client.service.FileService;
import com.example.client.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 文件服务实现
 *
 * @author 言曌
 * @date 2021/1/22 8:47 下午
 */
@Service
public class FileServiceImpl implements FileService {


    @Override
    public FileDTO findById(Long id) {
        return null;
    }

    @Override
    public void addFile(FileDTO file) {
    }

    @Override
    public void deleteFile(Long id) {
    }
}
