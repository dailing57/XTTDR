package com.xttdr.cotroller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.xttdr.common.Result;
import com.xttdr.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {
    @Value("${server.port}")
    private String port;

    @Value("${file.ip}")
    private String ip;

    private static final FileUtils fileUtils = new FileUtils();
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file,String prefix) throws IOException {
        return Result.success(fileUtils.upload(file,prefix));
    }

    @GetMapping("/download/{path}")
    public ResponseEntity<InputStreamResource> downloadFiles(@PathVariable String path, HttpServletResponse response) throws IOException {
        return fileUtils.downloadFile(path);
    }
    @PostMapping("/editor/upload")
    public JSON editorUpload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 获取源文件的名称
        // 定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/files/" + flag + "_" + originalFilename;  // 获取上传的路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);  // 把文件写入到上传的路径
        String url = "http://" + ip + ":" + port +  "/files/"  + flag + "_" + originalFilename;
        JSONObject json = new JSONObject();
        json.set("errno", 0);
        JSONArray arr = new JSONArray();
        JSONObject data = new JSONObject();
        arr.add(data);
        data.set("url", url);
        json.set("data", arr);
        return json;  // 返回结果 url
    }
    @PostMapping("/delete/{path}")
    public Result<?> deleteFile(@PathVariable String path){
        return Result.success(fileUtils.deleteFile(path));
    }
}
