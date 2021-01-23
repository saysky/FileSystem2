package com.example.client.util;

import com.example.client.dto.FileDTO;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @author 言曌
 */
public class FileUtil {

    /**
     * 输入流转File
     *
     * @param in
     * @param file
     */
    public static void inputStreamToFile(InputStream in, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件返回fileDTO
     *
     * @param file
     * @return
     */
    public static FileDTO upload(MultipartFile file) throws Exception {
        FileDTO fileDTO = new FileDTO();
        try {
            //用户目录
            final StringBuilder uploadPath = new StringBuilder(System.getProperties().getProperty("user.home"));
            uploadPath.append("/sens/uploads/");
            final File mediaPath = new File(uploadPath.toString());
            if (!mediaPath.exists()) {
                if (!mediaPath.mkdirs()) {
                    throw new Exception("上传失败，请重试");
                }
            }
            String fullName = file.getOriginalFilename().replaceAll("_tmp", "");

            //不带后缀
            String nameWithOutSuffix = fullName.substring(0, fullName.lastIndexOf('.')).replaceAll(" ", "_").replaceAll(",", "");

            //文件后缀
            final String fileSuffix = fullName.substring(file.getOriginalFilename().lastIndexOf('.') + 1);

            String fileName = UUID.randomUUID().toString().replaceAll("-", "");


            //判断文件名是否已存在
            File descFile = new File(mediaPath.getAbsoluteFile(), fileName);

            file.transferTo(descFile);

            //文件原路径
            final StringBuilder fullPath = new StringBuilder(mediaPath.getAbsolutePath());
            fullPath.append("/");
            fullPath.append(fileName);


            fileDTO.setFileAbsolutePath(fullPath.toString());
            fileDTO.setFileSuffix(fileSuffix);
            fileDTO.setFileFullName(fullName);
            fileDTO.setFileName(nameWithOutSuffix);
            fileDTO.setFileSize(descFile.length());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileDTO;
    }

    /**
     * 下载文件
     *
     * @param request
     * @param response
     * @param filePath
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                String fileName = file.getName().toString();
                // firefox浏览器
                if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
                } // IE浏览器
                else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                    fileName = URLEncoder.encode(fileName, "UTF-8");
                }// 谷歌
                else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
                }
                //首先设置响应的内容格式是force-download，那么你一旦点击下载按钮就会自动下载文件了
                response.setContentType("application/force-download");
                //通过设置头信息给文件命名，也即是，在前端，文件流被接受完还原成原文件的时候会以你传递的文件名来命名
                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.setCharacterEncoding("UTF-8");

                FileInputStream in = new FileInputStream(file);
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                OutputStream out = response.getOutputStream();

                // 3.响应
                byte[] buff = new byte[1024];
                int read = in.read(buff);

                //通过while循环写入到指定了的文件夹中
                while (read != -1) {
                    out.write(buff, 0, buff.length);
                    out.flush();
                    read = in.read(buff);
                }

            } else {
                System.out.println("文件不存在");
            }
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    /**
     * 获取文件base64String
     *
     * @param filePath
     * @return
     */
    public static String getFileBase64String(String filePath) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(filePath);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        return new Base64().encodeToString(data);
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        file.delete();
    }
}
