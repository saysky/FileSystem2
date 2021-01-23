package com.example.client.util;

import com.example.client.constant.CommonConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

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
    public static File inputStreamToFile(InputStream in, File file) {
        try {

            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            in.close();

            String tmpFilePath = file.getAbsolutePath() + "_tmp";
            AESUtil.encryptFile(CommonConst.AES_KEY, file.getAbsolutePath(), tmpFilePath);

            file.delete();
            return new File(tmpFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new File(file.getAbsolutePath());
    }

    /**
     * 下载文件
     *
     * @param response
     * @param fileName
     */
    public static void downloadFile(HttpServletResponse response, String fileName, byte[] data) {
        try {
            //首先设置响应的内容格式是force-download，那么你一旦点击下载按钮就会自动下载文件了
            response.setContentType("application/force-download");
            //通过设置头信息给文件命名，也即是，在前端，文件流被接受完还原成原文件的时候会以你传递的文件名来命名
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setCharacterEncoding("UTF-8");

            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            OutputStream out = response.getOutputStream();

            // 3.响应
            out.write(data);
            out.flush();

        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
