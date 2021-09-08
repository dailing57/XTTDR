package com.xttdr.cotroller;

import com.xttdr.common.Result;
import com.xttdr.utils.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {
    private static final FileUtils fileUtils = new FileUtils();
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file,String prefix) throws IOException {
        return Result.success(fileUtils.upload(file,prefix));
    }

    @GetMapping("/download/{path}")
    public ResponseEntity<InputStreamResource> downloadFiles(@PathVariable String path, HttpServletResponse response) throws IOException {
        //fileUtils.getFile(path,response);
        return fileUtils.downloadFile(path);
    }

    @PostMapping("/delete/{path}")
    public Result<?> deleteFile(@PathVariable String path){
        return Result.success(fileUtils.deleteFile(path));
    }
}
