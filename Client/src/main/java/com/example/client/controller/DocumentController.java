package com.example.client.controller;

import com.example.client.dto.DocumentDTO;
import com.example.client.dto.FileDTO;
import com.example.client.dto.UserDTO;
import com.example.client.service.DocumentService;
import com.example.client.service.FileService;
import com.example.client.util.FileUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author 言曌
 * @date 2021/1/22 9:54 下午
 */
@Controller
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
    @GetMapping("/")
    public String index(@RequestParam(value = "documentKey", required = false) String documentKey,
                        Model model) {
        // 获取登录用户信息
        Subject subject = SecurityUtils.getSubject();
        UserDTO user = (UserDTO) subject.getPrincipal();

        List<DocumentDTO> documentDTOList = new ArrayList<>();
        if (documentKey != null && !"".equals(documentKey)) {
            documentDTOList = documentService.findByUserIdAndDocumentKey(user.getId(), documentKey);
        }
        model.addAttribute("documentDTOList", documentDTOList);
        return "index";
    }

    /**
     * 上传
     *
     * @param documentKeys
     * @param documentTitle
     * @param file
     * @return
     */
    @PostMapping("/doUpload")
    public String uploadFile(@RequestParam("documentKeys") String documentKeys,
                             @RequestParam("documentTitle") String documentTitle,
                             MultipartFile file) throws IOException {
        // 获取登录用户信息
        Subject subject = SecurityUtils.getSubject();
        UserDTO user = (UserDTO) subject.getPrincipal();

        documentService.uploadDocument(documentKeys, documentTitle, user.getId(), file);
        return "redirect:/";
    }

    /**
     * 删除
     *
     * @param documentId
     * @param model
     * @return
     */
    @GetMapping("/doDelete")
    public String doDelete(@RequestParam("id") Long documentId, Model model) {
        // 获取登录用户信息
        Subject subject = SecurityUtils.getSubject();
        UserDTO user = (UserDTO) subject.getPrincipal();

        documentService.deleteByIdAndUserId(documentId, user.getId());
        return "redirect:/";
    }

    /**
     * 文件下载
     *
     * @param fileId
     * @param response
     */
    @GetMapping("/doDownload")
    public void download(@RequestParam("fileId") Long fileId,
                         HttpServletResponse response) throws Exception {
        // 获取登录用户信息
        Subject subject = SecurityUtils.getSubject();
        UserDTO user = (UserDTO) subject.getPrincipal();
        documentService.downloadDocument(fileId, user.getId(), response);
    }
}
