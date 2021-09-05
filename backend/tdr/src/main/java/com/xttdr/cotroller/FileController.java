package com.xttdr.cotroller;

import com.xttdr.common.Result;
import com.xttdr.utils.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {
    private static final FileUtils fileUtils = new FileUtils();
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        return Result.success(fileUtils.upload(file));
    }

    @GetMapping("/download/{path}")
    public void downloadFiles(@PathVariable String path, HttpServletResponse response) {
        fileUtils.getFile(path,response);
    }

    @PostMapping("/delete/{path}")
    public Result<?> deleteFile(@PathVariable String path){
        return Result.success(fileUtils.deleteFile(path));
    }
}
