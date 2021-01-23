package com.example.client.service.impl;

import com.example.client.dto.FileDTO;
import com.example.client.repostory.FileRepository;
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

    @Autowired
    private FileRepository fileRepository;

    @Override
    public FileDTO findById(Long id) {
        Optional<FileDTO> fileOptional = fileRepository.findById(id);
        if (fileOptional.isPresent()) {
            return fileOptional.get();
        }
        return null;
    }

    @Override
    public void addFile(FileDTO file) {
        fileRepository.save(file);
    }

    @Override
    public void deleteFile(Long id) {
        FileDTO fileDTO = this.findById(id);
        if (fileDTO != null) {
            FileUtil.deleteFile(fileDTO.getFileAbsolutePath());

            fileRepository.deleteById(id);
        }
    }
}
