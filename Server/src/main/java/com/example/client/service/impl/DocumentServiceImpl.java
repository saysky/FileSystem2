package com.example.client.service.impl;

import com.example.client.dto.DocumentDTO;
import com.example.client.repostory.DocumentRepository;
import com.example.client.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 文档服务实现
 *
 * @author 言曌
 * @date 2021/1/22 8:47 下午
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public DocumentDTO findById(Long id) {
        Optional<DocumentDTO> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()) {
            return optionalDocument.get();
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        documentRepository.deleteById(id);
    }

    @Override
    public void addDocument(DocumentDTO documentDTO) {
        documentRepository.save(documentDTO);
    }

    @Override
    public List<DocumentDTO> findByUserIdAndDocumentKey(Long userId, String documentKey) {
        return documentRepository.findByUserIdAndDocumentKey(userId, documentKey);
    }
}
