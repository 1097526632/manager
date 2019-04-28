package com.manage.system.core.store;

import com.manage.system.common.utils.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件存储
 */
@Component
public class FileStore implements IStore {
    @Override
    public void save(String filePath, InputStream inputStream) {
        try {
            FileUtils.copyInputStreamToFile(inputStream,new File(filePath) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load(String key, OutputStream outputStream) throws IOException {
        FileCopyUtils.copy(new FileInputStream(key), outputStream);
    }

    @Override
    public long getFileSize(String key) {
        File file=new File(key);
        return file.length();
    }

    @Override
    public void downloadFile(String key, HttpServletRequest request, HttpServletResponse response) {
        File file=new File(key);
        FileUtils.downFile(file,request,response);
    }

    @Override
    public void delete(String filePath) {
        FileUtils.deleteFile(filePath);
    }

    @Override
    public void copy(String sourcePath, String destPath) {
        FileUtils.copyFile(sourcePath,destPath);
    }

    @Override
    public boolean move(String sourcePath, String destPath) {
        boolean result=FileUtils.backCopyFile(sourcePath,destPath);
        FileUtils.deleteFile(sourcePath);
        return result;
    }
}
