package com.whl.hessian;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.whl.hessian.api.FileService;

public class TestFileService {

    @Test
    public void testUpload() throws IOException {
        String url = "http://127.0.0.1:8080/hessian/file";

        HessianProxyFactory factory = new HessianProxyFactory();
        FileService fileService = (FileService) factory.create(FileService.class, url);

        String fileName = fileService.upload("15-火车票2.JPG", Files.newInputStream(Paths.get("C:\\Users\\Administrator\\Desktop\\发票\\15-火车票2.JPG")));
        assertNotNull(fileName);
    }

    @Test
    public void testDownload() throws IOException {
        String url = "http://127.0.0.1:8080/hessian/file";

        HessianProxyFactory factory = new HessianProxyFactory();
        FileService fileService = (FileService) factory.create(FileService.class, url);

        try (InputStream is = fileService.download("0924dfc2-c055-4597-8692-db0dd7b254db.JPG")) {
            assertNotNull(is);
        }
    }

}
