package com.xttdr.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

public class FileUtils {
    @Value("${server.port}")
    private String port;

    @Value("${file.ip}")
    private String ip;

    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 获取源文件的名称
        String flag = IdUtil.fastSimpleUUID();// 定义文件的唯一标识（前缀）
        String rootFilePath = System.getProperty("user.dir") + "/files/" + flag + "_" + originalFilename;  // 获取上传的路径
        cn.hutool.core.io.FileUtil.writeBytes(file.getBytes(), rootFilePath);  // 把文件写入到上传的路径
        return rootFilePath;  // 返回结果 url
    }

    public void getFile(String flag, HttpServletResponse response) {
        OutputStream os;  // 新建一个输出流对象
        String basePath = System.getProperty("user.dir") + "/files/";  // 定于文件上传的根路径
        List<String> fileNames = cn.hutool.core.io.FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = cn.hutool.core.io.FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

    public Boolean deleteFile(String path){
        return FileSystemUtils.deleteRecursively(new File(path));
    }

    @PostMapping("/editor/upload")
    public JSON editorUpload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 获取源文件的名称
        String flag = IdUtil.fastSimpleUUID();// 定义文件的唯一标识（前缀）
        String rootFilePath = System.getProperty("user.dir") + "/files/" + flag + "_" + originalFilename;  // 获取上传的路径
        cn.hutool.core.io.FileUtil.writeBytes(file.getBytes(), rootFilePath);  // 把文件写入到上传的路径
        String url = ip + ":" + port + "/files/" + flag;
        JSONObject json = new JSONObject();
        json.set("errno", 0);
        JSONArray arr = new JSONArray();
        JSONObject data = new JSONObject();
        arr.add(data);
        data.set("url", url);
        json.set("data", arr);
        return json;  // 返回结果 url
    }
}
