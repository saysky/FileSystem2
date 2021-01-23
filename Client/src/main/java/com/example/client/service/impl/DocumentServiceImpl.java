package com.example.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.client.constant.CommonConst;
import com.example.client.dto.DocumentDTO;
import com.example.client.service.DocumentService;
import com.example.client.util.AESUtil;
import com.example.client.util.FileUtil;
import com.example.client.util.JsonResult;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

/**
 * 文档服务实现
 *
 * @author 言曌
 * @date 2021/1/22 8:47 下午
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentService documentService;


    /**
     * 服务器主机
     */
    public static String SERVER_DOMAIN = "http://localhost:8888";

    /**
     * 声明 restTemplate
     *
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    /**
     * 注入 restTemplate
     */
    @Autowired
    private RestTemplate restTemplate;


    /**
     * 构建 header，因为这段代码下面几个方法都需要调用，所以单独提取出来
     *
     * @return
     * @throws Exception
     */
    private HttpHeaders builderHeader() {
        // 请求头设置属性
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentType(MediaType.parseMediaType("multipart/form-data; charset=UTF-8"));
        return headers;
    }


    @Override
    public void deleteByIdAndUserId(Long id, Long userId) {

        // 1.设置header
        HttpHeaders headers = builderHeader();
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(form, headers);
        try {
            // 2.向Server端请求获得列表
            ResponseEntity<String> result = restTemplate.exchange(SERVER_DOMAIN + "/document/delete?id=" + id + "&userId=" + userId, HttpMethod.POST, httpEntity, String.class);
            if (result.getStatusCode() == HttpStatus.OK) {
                JsonResult jsonResult = JSON.parseObject(result.getBody(), JsonResult.class);
                if (jsonResult.getCode() == 1) {
                    System.out.println("删除成功");
                } else {
                    System.out.println(jsonResult.getMsg());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用Server接口失败");
        }

    }

    @Override
    public void uploadDocument(String documentKeys, String documentTitle, Long userId, MultipartFile multipartFile) throws IOException {
        // 客户端也可以保留一份document信息
        // 具体

        String fileName = multipartFile.getOriginalFilename();
        // 1.设置header
        HttpHeaders headers = builderHeader();
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        // 将 MultipartFile 转成 File
        InputStream ins = multipartFile.getInputStream();
        File file = new File(fileName);
        File tmpFile = FileUtil.inputStreamToFile(ins, file);
        // 将 file 作为参数装到 form 中
        FileSystemResource resource = new FileSystemResource(tmpFile);
        form.add("file", resource);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(form, headers);
        try {
            // 2.向Server端请求获得列表
            ResponseEntity<String> result = restTemplate.exchange(SERVER_DOMAIN + "/document/upload?userId=" + userId + "&documentKeys=" + documentKeys + "&documentTitle=" + documentTitle, HttpMethod.POST, httpEntity, String.class);
            if (result.getStatusCode() == HttpStatus.OK) {
                JsonResult jsonResult = JSON.parseObject(result.getBody(), JsonResult.class);
                if (jsonResult.getCode() == 1) {
                    System.out.println("上传成功");
                } else {
                    System.out.println(jsonResult.getMsg());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用Server接口失败");
        }
        tmpFile.delete();
    }

    @Override
    public void downloadDocument(Long fileId, Long userId, HttpServletResponse response) throws Exception {
        // 设置header
        HttpHeaders headers = builderHeader();
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(form, headers);

        // 1. 向Server端请求：根据ID获取文件信息
        String fileFullName = "";
        try {
            ResponseEntity<String> result = restTemplate.exchange(SERVER_DOMAIN + "/file/detail?fileId=" + fileId, HttpMethod.POST, httpEntity, String.class);
            if (result.getStatusCode() == HttpStatus.OK) {
                JsonResult jsonResult = JSON.parseObject(result.getBody(), JsonResult.class);
                if (jsonResult.getCode() == 1) {
                    JSONObject jsonObject = (JSONObject) jsonResult.getResult();
                    fileFullName = jsonObject.getString("fileFullName");
                } else {
                    System.out.println(jsonResult.getMsg());
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用Server接口失败");
        }

        // 2.向Server端请求下载
        ResponseEntity<String> result = restTemplate.exchange(SERVER_DOMAIN + "/document/download?fileId=" + fileId + "&userId=" + userId, HttpMethod.POST, httpEntity, String.class);
        if (result.getStatusCode() == HttpStatus.OK) {
            JsonResult jsonResult = JSON.parseObject(result.getBody(), JsonResult.class);
            if (jsonResult.getCode() == 1) {
                String base64File = (String) jsonResult.getResult();
                byte[] data = new Base64().decode(base64File.getBytes());
                byte[] data2 = AESUtil.decrypt(data, CommonConst.AES_KEY);

                FileUtil.downloadFile(response, fileFullName, data2);
            } else {
                System.out.println(jsonResult.getMsg());
                return;
            }
        }
    }

    @Override
    public List<DocumentDTO> findByUserIdAndDocumentKey(Long userId, String documentKey) {
        // 1.设置header
        HttpHeaders headers = builderHeader();
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(form, headers);
        List<DocumentDTO> fileDTOList = new ArrayList<>();
        try {
            // 2.向Server端请求获得列表
            ResponseEntity<String> result = restTemplate.exchange(SERVER_DOMAIN + "/document/search?userId=" + userId + "&documentKey=" + documentKey, HttpMethod.POST, httpEntity, String.class);
            if (result.getStatusCode() == HttpStatus.OK) {
                JsonResult jsonResult = JSON.parseObject(result.getBody(), JsonResult.class);
                if (jsonResult.getCode() == 1) {
                    fileDTOList = (List<DocumentDTO>) jsonResult.getResult();
                } else {
                    System.out.println(jsonResult.getMsg());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用Server接口失败");
        }
        return fileDTOList;
    }
}
