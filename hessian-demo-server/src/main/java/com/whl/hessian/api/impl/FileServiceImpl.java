package com.whl.hessian.api.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;

import org.apache.commons.lang3.StringUtils;

import com.caucho.hessian.server.HessianServlet;
import com.whl.hessian.api.FileService;

@WebServlet("/file")
public class FileServiceImpl extends HessianServlet implements FileService {
    private static final long serialVersionUID = -4906682224189344273L;

    @Override
    public String upload(String name, InputStream is) throws IOException {
        String suffix = "";

        if (StringUtils.contains(name, ".")) {
            suffix = name.substring(name.lastIndexOf("."));
        }
        String storeFileName = UUID.randomUUID() + suffix;
        Path path = this.getFileStorePath();
        Path storePath = path.resolve(storeFileName);

        if (!Files.exists(storePath.getParent())) {
            Files.createDirectories(storePath.getParent());
        }
        Files.copy(is, storePath, StandardCopyOption.REPLACE_EXISTING);
        return storeFileName;
    }

    @Override
    public InputStream download(String name) throws IOException {
        Path path = this.getFileStorePath();
        Path file = path.resolve(name);

        if (Files.exists(file)) {
            return Files.newInputStream(file);
        }
        return null;
    }

    private Path getFileStorePath() {
        return Paths.get(System.getProperty("user.dir"), "upload");
    }

}
