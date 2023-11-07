package com.whl.hessian.api.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.caucho.hessian.server.HessianServlet;
import com.whl.hessian.api.FileService;

@WebServlet("/file")
public class FileServiceImpl extends HessianServlet implements FileService {
    private static final long serialVersionUID = -4906682224189344273L;

    @Override
    public String upload(String name, InputStream is) throws IOException {
        final String PATH = this.getFileStoreDirectory();
        String suffix = "";

        if (StringUtils.contains(name, ".")) {
            suffix = name.substring(name.lastIndexOf("."));
        }
        String storeFileName = UUID.randomUUID() + suffix;
        File destination = new File(PATH + File.separator + storeFileName);
        FileUtils.copyInputStreamToFile(is, destination);
        return storeFileName;
    }

    @Override
    public InputStream download(String name) throws IOException {
        final String PATH = this.getFileStoreDirectory();
        File file = new File(PATH + File.separator + name);

        if (file.exists()) {
            return Files.newInputStream(file.toPath());
        }
        return null;
    }

    private String getFileStoreDirectory() {
        return System.getProperty("user.dir") + File.separator + "upload";
    }

}
