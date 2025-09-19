package com.sunny.system.portal.controller;

import com.sunny.framework.core.model.CommonResult;
import com.sunny.framework.file.model.FileResult;
import com.sunny.framework.file.provider.FileProviderFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件API")
@RestController
@RequestMapping(path = "file")
public class FileController {

    @Autowired
    FileProviderFactory fileProviderFactory;

    @PostMapping(path = "upload")
    public CommonResult<FileResult> upload(MultipartFile file) {
        return CommonResult.ok(fileProviderFactory.getProvider("local").upload(file));
    }
}
