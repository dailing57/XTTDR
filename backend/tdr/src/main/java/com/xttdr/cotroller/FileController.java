package com.xttdr.cotroller;

import com.xttdr.common.Result;
import com.xttdr.utils.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
public class FileController {
    private static final FileUtils fileUtils = new FileUtils();
    private Path fileStorageLocation; // 文件在本地存储的地址
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file,String prefix) throws IOException {
        return Result.success(fileUtils.upload(file,prefix));
    }

    @GetMapping("/download/{path}")
    public ResponseEntity<InputStreamResource> downloadFiles(@PathVariable String path, HttpServletResponse response) throws IOException {
        fileUtils.getFile(path,response);

        ResponseEntity<InputStreamResource> tmp =fileUtils.downloadFile(path);
//        System.out.println(tmp);
        return tmp;
    }

    @PostMapping("/delete/{path}")
    public Result<?> deleteFile(@PathVariable String path){
        return Result.success(fileUtils.deleteFile(path));
    }

}
