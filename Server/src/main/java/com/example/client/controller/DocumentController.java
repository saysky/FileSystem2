package com.example.client.controller;

import com.example.client.dto.DocumentDTO;
import com.example.client.dto.FileDTO;
import com.example.client.service.DocumentService;
import com.example.client.service.FileService;
import com.example.client.util.FileUtil;
import com.example.client.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author 言曌
 * @date 2021/1/22 9:54 下午
 */
@RestController
public class DocumentController {


    @Autowired
    private FileService fileService;

    @Autowired
    private DocumentService documentService;

    /**
     * 首页，根据文档号查询
     *
     * @param documentKey
     * @return
     */
    @PostMapping("/document/search")
    public JsonResult search(@RequestParam("documentKey") String documentKey,
                             @RequestParam("userId") Long userId) {
        List<DocumentDTO> documentDTOList = new ArrayList<>();
        if (documentKey != null && !"".equals(documentKey)) {
            documentDTOList = documentService.findByUserIdAndDocumentKey(userId, documentKey);
        }
        return JsonResult.success("查询成功", documentDTOList);
    }

    /**
     * 上传
     *
     * @param documentKeys
     * @param documentTitle
     * @param file
     * @return
     */
    @PostMapping("/document/upload")
    public JsonResult uploadFile(@RequestParam("documentKeys") String documentKeys,
                                 @RequestParam("documentTitle") String documentTitle,
                                 @RequestParam("userId") Long userId,
                                 MultipartFile file) {
        // 上传文件并存储文件信息
        FileDTO fileDTO;
        try {
            fileDTO = FileUtil.upload(file);
            fileDTO.setUserId(userId);
            fileService.addFile(fileDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("上传失败");
        }

        // 存储文档信息
        String[] documentKeyArr = documentKeys.split(",");
        for (String documentKey : documentKeyArr) {
            DocumentDTO document = new DocumentDTO();
            document.setUserId(userId);
            document.setDocumentTitle(documentTitle);
            document.setDocumentKey(documentKey);
            document.setFileId(fileDTO.getId());
            documentService.addDocument(document);
        }
        return JsonResult.success("上传成功");
    }

    /**
     * 删除
     *
     * @param documentId
     * @return
     */
    @PostMapping("/document/delete")
    public JsonResult doDelete(@RequestParam("id") Long documentId,
                               @RequestParam("userId") Long userId) {
        DocumentDTO documentDTO = documentService.findById(documentId);
        if (!Objects.equals(documentDTO.getUserId(), userId)) {
            return JsonResult.error("没有权限删除");
        }

        documentService.deleteById(documentId);

//        fileService.deleteFile(documentDTO.getFileId());
        return JsonResult.success("删除成功");
    }

    /**
     * 文件下载
     *
     * @param fileId
     */
    @PostMapping("/document/download")
    public JsonResult download(@RequestParam("fileId") Long fileId,
                               @RequestParam("userId") Long userId) throws Exception {

        // 查询文件
        FileDTO fileDTO = fileService.findById(fileId);
        if (fileDTO == null || !Objects.equals(fileDTO.getUserId(), userId)) {
            return JsonResult.error("文件不存在");
        }
        String baseString = FileUtil.getFileBase64String(fileDTO.getFileAbsolutePath());
        return JsonResult.success("下载成功", baseString);
    }

    /**
     * 文件下载
     *
     * @param fileId
     */
    @PostMapping("/file/detail")
    public JsonResult fileDetail(@RequestParam("fileId") Long fileId) {
        // 查询文件
        FileDTO fileDTO = fileService.findById(fileId);
        if (fileDTO == null) {
            return JsonResult.error("文件不存在");
        }
        return JsonResult.success("查询成功", fileDTO);
    }


}
