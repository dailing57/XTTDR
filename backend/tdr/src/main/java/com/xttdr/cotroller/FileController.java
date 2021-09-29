package com.xttdr.cotroller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xttdr.common.Result;
import com.xttdr.entity.User;
import com.xttdr.mapper.AccountMapper;
import com.xttdr.mapper.UserMapper;
import com.xttdr.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController extends BaseController{
    @Value("${server.port}")
    private String port;

    @Value("${file.ip}")
    private String ip;

    @Resource
    UserMapper userMapper;
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
    @PostMapping("/avatar")
    public Result<?> updateUserAvatar(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String path = "";
        String prefix = "";
        String filename = "";
        for (MultipartFile file : files) {
            prefix = "avatar/"+IdUtil.fastSimpleUUID();
            filename = file.getOriginalFilename();
            path = fileUtils.upload(file,prefix);
        }
        path = "http://"+ip+":"+port+"/files/"+prefix+filename;
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id",getAccountId()).set("avatar",path);
        userMapper.update(null,userUpdateWrapper);
        return Result.success();
    }
    @PostMapping("/delete/{path}")
    public Result<?> deleteFile(@PathVariable String path){
        return Result.success(fileUtils.deleteFile(path));
    }
}
