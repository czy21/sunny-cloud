package com.sunny.framework.file.repository;

import com.sunny.framework.file.model.FileEntity;
import com.sunny.framework.file.model.FileResult;

public interface FileRepository {
    int insert(FileEntity entity);
    int update(FileEntity entity);
    int deleteById(String id);
    FileEntity selectById(String id);
    FileResult selectByIdForResult(String id);
}
