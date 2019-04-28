package com.manage.system.core.store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IStore {
    void save(String filePath, InputStream inputStream);
    void load(String key, OutputStream outputStream)  throws IOException;
    long getFileSize(String key);
    void downloadFile(String key, HttpServletRequest request, HttpServletResponse response);
    void delete(String filePath);
    void copy(String sourcePath,String destPath);
    boolean move(String sourcePath,String destPath);
}
